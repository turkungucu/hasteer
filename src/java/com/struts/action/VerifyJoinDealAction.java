/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.action;

import com.api.JoinDealSessionObject;
import com.api.billing.braintree.BTGatewaySingleton;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.dao.CreditCardDetail;
import com.dao.DealParticipant;
import com.dao.DealParticipantsLog;
import com.dao.Deal;
import com.dao.ShippingAddress;
import com.dao.User;
import com.dao.Product;
import com.dao.OrderSummary;
import com.api.shipping.usps.USPSWebTools;
import com.api.Pair;
import com.struts.form.VerifyJoinDealForm;
import com.util.StringUtil;
import com.api.Jsp;
import com.api.notification.JoinDealConfirmation;
import com.constants.HasteerConstants;
import com.dao.BraintreeError;
import com.util.CreditCardUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author ecolak
 */
public class VerifyJoinDealAction extends Action implements IWorkflowAction {

    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
    {
        ActionForward result = null;
        VerifyJoinDealForm jForm = (VerifyJoinDealForm) form;
        ActionErrors errors = new ActionErrors();

        User user = User.getUserFromSession(request);
        if (user == null) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("userDoesNotExist"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        JoinDealSessionObject jdso = JoinDealSessionObject.getInstanceFromSession(request);
        if(jdso == null || jdso.getDealParticipant() == null) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("Workflow.errors.invalidDeal"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        Deal deal = Deal.getById(jdso.getDealParticipant().getDealId());
        if("next".equalsIgnoreCase(jForm.getCmd()) || "previous".equalsIgnoreCase(jForm.getCmd()))
        {
            if(!deal.isRunning()) {
                errors.add(Globals.ERROR_KEY, new ActionMessage("JoinDealAction.errors.dealClosed", Jsp.getUrlBase()));
                saveErrors(request, errors);
                initForm(form, jdso, user);
                return (mapping.findForward("failure"));
            }
        }

        if ("next".equalsIgnoreCase(jForm.getCmd())) {
            result = next(mapping, form, request, response);
        } else if("previous".equalsIgnoreCase(jForm.getCmd())) {
            result = previous(mapping);
        } else {
            initForm(form, jdso, user);
            result = mapping.findForward("success");
        }

        return result;
    }

    // finalize join deal
    @Override
    public ActionForward next(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
        ActionErrors errors = new ActionErrors();

        JoinDealSessionObject jdso = JoinDealSessionObject.getInstanceFromSession(request);
        User user = User.getUserFromSession(request);

        DealParticipant dp = jdso.getDealParticipant();
        CreditCardDetail ccd = jdso.getCreditCardDetail();
        ShippingAddress addr = jdso.getShippingAddress();
        OrderSummary os = jdso.getOrderSummary();
        
        if(dp != null && ccd != null && addr != null && os != null) {
            Result<Transaction> trResult = null;

            try {
                if(StringUtils.isNotBlank(dp.getTransactionId())) {
                    // this means that the user is editing pricing option selection
                    // or payment method or shipping address or shipping method
                    // void the current transaction before submitting a new one
                    Result<Transaction> voidResult = CreditCardUtil.voidTransaction(dp.getTransactionId());
                    if(voidResult != null && !voidResult.isSuccess()) {
                        throw new Exception("Error while voiding current transaction " + dp.getTransactionId());
                    }
                }

                if(StringUtils.isBlank(ccd.getVaultToken()))
                    trResult = submitTransaction(dp, ccd, os, !ccd.isDoNotSaveToDb());
                else
                    trResult = submitTransactionFromVault(dp, ccd, os);
            } catch(Exception e) {
                errors.add(Globals.ERROR_KEY, new ActionMessage("billing.errors.generic.error", HasteerConstants.DEFAULT_SUPPORT_PAGE));
                saveErrors(request, errors);
                BraintreeError.insertError(dp.getDealId(), dp.getBuyerId(), null, e.getMessage(), -1);
            }

            // Do not save anything until the transaction is successful
            if(trResult != null) {
                if(trResult.isSuccess()) {
                    if(!ccd.isDoNotSaveToDb()) {
                        if(StringUtils.isBlank(ccd.getVaultToken())) {
                            ccd.setVaultToken(trResult.getTarget().getCreditCard().getToken());
                        }
                    }

                    // TODO double negation is confusing.. Flag should be saveToDB()
                    if(!ccd.isDoNotSaveToDb()) {
                        CreditCardDetail mccd = CreditCardDetail.getMostRecentByUserId(user.getUserId());
                        if(mccd != null) {
                            mccd.setIsMostRecent(false);
                            mccd.save();
                        }
                        ccd.setIsMostRecent(true);
                    }

                    if(!addr.isDoNotSaveToDb()) {
                        ShippingAddress maddr = ShippingAddress.getMostRecentByUserId(user.getUserId());
                        if(maddr != null) {
                            maddr.setIsMostRecent(false);
                            maddr.save();
                        }
                        addr.setIsMostRecent(true);
                    }

                    ccd.save();
                    addr.save();
                    os.save();
                    dp.setCreditCardId(ccd.getCreditCardDetailsId());
                    dp.setShippingAddressId(addr.getShippingAddressId());
                    dp.setOrderSummaryId(os.getOrderSummaryId());
                    dp.setTransactionId(trResult.getTarget().getId());
                    dp.save();

                    DealParticipantsLog dpLog = new DealParticipantsLog();
                    dpLog.setBuyerId(dp.getBuyerId());
                    dpLog.setDealId(dp.getDealId());
                    dpLog.setOptionId(dp.getPricingOptionId());
                    dpLog.setLogDate(new Date());
                    dpLog.setAction(DealParticipantsLog.LogAction.JOIN.getActionType());
                    dpLog.setIp(dp.getIp());
                    dpLog.save();

                    try {
                        Deal d = Deal.getById(dp.getDealId());
                        if(d != null) {
                            Product p = Product.getById(d.getProductId());
                            if(p != null) {
                                JoinDealConfirmation email = new JoinDealConfirmation(os, ccd, p.getProductName(), addr.getFullAddress());
                                email.dispatchEmail();
                            }
                        }                        
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                } else if (trResult.getTransaction() != null) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("billing.errors.generic.error", HasteerConstants.DEFAULT_SUPPORT_PAGE));
                    saveErrors(request, errors);
                    BraintreeError.insertError(dp.getDealId(), dp.getBuyerId(), trResult.getTransaction().getProcessorResponseCode(),
                            trResult.getMessage(), trResult.getTransaction().getStatus().ordinal());
                } else {
                    int i = 0;
                    for (ValidationError error : trResult.getErrors().getAllDeepValidationErrors()) {
                        if(i == 0)
                            errors.add(Globals.ERROR_KEY, new ActionMessage("billing.errors.generic.error", HasteerConstants.DEFAULT_SUPPORT_PAGE));

                        BraintreeError.insertError(dp.getDealId(), dp.getBuyerId(), error.getCode().code, error.getMessage(), -1);
                        i++;
                    }
                    saveErrors(request, errors);
                }
            }
        }

        if (errors.isEmpty()) {
            return mapping.findForward("next");
        } else {
            initForm(form, jdso, user);
            return (mapping.findForward("failure"));
        }
    }

    @Override
    public ActionForward previous(ActionMapping mapping) {
        return mapping.findForward("previous");
    }

    private void initForm(ActionForm form, JoinDealSessionObject jdso, User user) {
        VerifyJoinDealForm jForm = (VerifyJoinDealForm) form;

        DealParticipant dp = jdso.getDealParticipant();
        ShippingAddress addr = jdso.getShippingAddress();
        OrderSummary os = jdso.getOrderSummary();

        //Get product
        Product product = Product.getById(Deal.getById(dp.getDealId()).getProductId());

        //Get map of all offered shipping methods and corresponding cost
        LinkedList<Pair<String, Double>> shippingMethodsAndCost =
                USPSWebTools.getShippingMethodsAndCost("94404", addr.getZipCode(), product, os.getQuantity(), false);

        if (shippingMethodsAndCost.isEmpty()) {
            //TODO Find a better way to handle this
            shippingMethodsAndCost.add(new Pair<String, Double>("Ground Shipping", 6.95));
            os.setShippingMethod("Ground Shipping");
            os.setShippingCost(6.95);
        } else if (StringUtils.isBlank(os.getShippingMethod())) {
            Pair<String, Double> defaultPair = shippingMethodsAndCost.get(0);
            os.setShippingMethod(defaultPair.getFirst());
            os.setShippingCost(defaultPair.getSecond());
        }

        os.setShippingMethodsAndCost(shippingMethodsAndCost);
        os.setCourier(OrderSummary.COURIER_USPS);
        os.setTax(os.getSubtotal() * HasteerConstants.DEFAULT_CA_SALES_TAX);

        jForm.setOrderSummary(os);
        jForm.setCcDetails(jdso.getCreditCardDetail());
        jForm.setShippingDetails(addr.getFullAddress());
        jForm.setContactPhone(addr.getPhoneNumber());
        jForm.setContactEmail(user.getEmail());
        jForm.setProduct(Product.getById(Deal.getById(dp.getDealId()).getProductId()));
    }

    private Result<Transaction> submitTransactionFromVault(DealParticipant dp,
            CreditCardDetail ccd,
            OrderSummary os) throws Exception
    {
        TransactionRequest trRequest = new TransactionRequest().
            amount(new BigDecimal(StringUtil.getBTFormattedPrice(os.getTotal()))).
            customerId(String.valueOf(dp.getBuyerId())).
            paymentMethodToken(ccd.getVaultToken());

        BraintreeGateway gateway = BTGatewaySingleton.getInstance();
        return gateway.transaction().sale(trRequest);
    }

    /**
     * Creates and submits an authorization transaction to the gateway
     * for a new credit card. The card will be created and added to the customer if
     * the customer already exists. Else a new customer will be created and the
     * card will be added to it.
     */
    private Result<Transaction> submitTransaction(DealParticipant dp,
            CreditCardDetail ccd,
            OrderSummary os,boolean storeCcInVault) throws Exception
    {
        BraintreeGateway gateway = BTGatewaySingleton.getInstance();
        Customer cust = null;
        try {
            cust = gateway.customer().find(String.valueOf(dp.getBuyerId()));
        } catch(Exception e) {
            // ignore
        }

        TransactionRequest trRequest = new TransactionRequest().
            amount(new BigDecimal(StringUtil.getBTFormattedPrice(os.getTotal()))). // Braintree expectes max 2 decimal points
            //orderId("order id").
            //merchantAccountId("a_merchant_account_id").
            creditCard().
                number(ccd.getCreditCardNum()).
                expirationDate(ccd.getFormattedExpiryDate()).
                cardholderName(ccd.getFirstName() + " " + ccd.getLastName()).
                cvv(ccd.getCvv()).
                done();

        if(cust != null)
            trRequest.customerId(cust.getId());
        else
            trRequest.
                customer().
                id(String.valueOf(dp.getBuyerId())). // use user_id as customer_id
                firstName(ccd.getFirstName()).
                lastName(ccd.getLastName()).
                done();

        trRequest.
            billingAddress().
                firstName(ccd.getFirstName()).
                lastName(ccd.getLastName()).
                streetAddress(ccd.getAddress1()).
                extendedAddress(ccd.getAddress2()).
                locality(ccd.getCity()).
                region(ccd.getState()).
                postalCode(ccd.getZipCode()).
                countryCodeAlpha2(ccd.getCountry()).
                done().
            options().
                storeInVault(storeCcInVault).
                done();

        return gateway.transaction().sale(trRequest);
    }

}
