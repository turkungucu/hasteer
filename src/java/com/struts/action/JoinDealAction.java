/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.struts.action;

import com.api.JoinDealSessionObject;
import com.api.Jsp;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.ValidationError;
import com.constants.HasteerConstants;
import com.dao.BraintreeError;
import com.dao.CreditCardDetail;
import com.dao.Deal;
import com.dao.DealPricingOption;
import com.dao.DealParticipant;
import com.dao.DealParticipantsLog;
import com.dao.DealParticipantsLog.LogAction;
import com.dao.User;
import com.dao.Product;
import com.dao.ProductImage;
import com.dao.RewardPointsBalance;
import com.dao.OrderSummary;
import com.dao.ShippingAddress;
import com.struts.form.JoinDealForm;
import com.util.CreditCardUtil;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 *
 * @author ecolak
 */
public class JoinDealAction extends Action implements IWorkflowAction {

    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
        ActionForward result = null;
        JoinDealForm jForm = (JoinDealForm) form;
        ActionErrors errors = new ActionErrors();
        
        User user = User.getUserFromSession(request);
        if (user == null) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("userDoesNotExist"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        JoinDealSessionObject jdso = JoinDealSessionObject.getInstanceFromSession(request);
        Deal deal = Deal.getById(jForm.getDealId());
        if(deal == null) {
            DealParticipant dp = jdso.getDealParticipant();
            if(dp != null) {
                deal = Deal.getById(dp.getDealId());
                jForm.setDealId(dp.getDealId());
                jForm.setPrOptionId(dp.getPricingOptionId());
            }
        }
        if(deal == null) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("JoinDealAction.errors.dealNotFound", Jsp.getUrlBase()));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        if ("join".equalsIgnoreCase(jForm.getCmd())) {
            result = next(mapping, form, request, response);
        } else if("quickJoin".equalsIgnoreCase(jForm.getCmd())) {
            result = quickJoin(mapping, form, request, response);
        } else if("leave".equalsIgnoreCase(jForm.getCmd())) {
            result = leaveDeal(mapping, form, request, user);
        } else {
            result = setForm(mapping, form, user);
        }

        return result;
    }

    private ActionForward setForm(ActionMapping mapping, ActionForm form, User user) {
        JoinDealForm jForm = (JoinDealForm) form;
        Deal deal = Deal.getById(jForm.getDealId());
        RewardPointsBalance rewardPointsBalance = RewardPointsBalance.getByUserId(user.getUserId());
        int rewardPointsExchangeRate = RewardPointsBalance.getRewardPointsExchangeRate();

        jForm.setDeal(deal);
        jForm.setDealId(deal.getDealId());
        List<DealPricingOption> prOptions = DealPricingOption.getByDealId(deal.getDealId());
        jForm.setPricingOptions(prOptions);
        jForm.setProduct(Product.getById(deal.getProductId()));
        ProductImage image = ProductImage.getPrimaryImageForProduct(deal.getProductId());
        image.resizeImage(HasteerConstants.MAX_WIDTH_FOR_HOT_DEALS, HasteerConstants.MAX_HEIGHT_FOR_HOT_DEALS);
        jForm.setProductImage(image);
        jForm.setRewardPointsBalance(rewardPointsBalance);

        DealParticipant dp = DealParticipant.getByDealIdBuyerId(deal.getDealId(), user.getUserId());
        if(dp != null)
            jForm.setUserAlreadyInDeal(true);

        if(jForm.getPrOptionId() <= 0) {            
            if(dp != null) {
                jForm.setPrOptionId(dp.getPricingOptionId());
                OrderSummary os = OrderSummary.getById(dp.getOrderSummaryId());
                if (os != null) {
                    jForm.setQuantity(os.getQuantity());
                    jForm.setRedeemedAmount(rewardPointsExchangeRate > 0 ?
                        (os.getRedeemedPoints() / rewardPointsExchangeRate) : 0);
                }
            } else {
                jForm.setPrOptionId(0);
            }
        }

        jForm.setQuickJoinEnabled(CreditCardDetail.getMostRecentByUserId(user.getUserId()) != null &&
                ShippingAddress.getMostRecentByUserId(user.getUserId()) != null && !jForm.isUserAlreadyInDeal());

        return mapping.findForward("success");
    }

    private ActionForward leaveDeal(ActionMapping mapping, ActionForm form, HttpServletRequest request, User user) {
        JoinDealForm jForm = (JoinDealForm) form;
        Deal deal = Deal.getById(jForm.getDealId());
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();

        if(!deal.isRunning()) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("JoinDealAction.errors.leaveNotAllowed", Jsp.getUrlBase()));
            saveErrors(request, errors);
            return setForm(mapping, form, user);
        }

        DealParticipant dp = DealParticipant.getByDealIdBuyerId(deal.getDealId(), user.getUserId());
        OrderSummary os = OrderSummary.getById(dp.getOrderSummaryId());
        if(dp == null) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("JoinDealAction.errors.userNotInDeal"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        Result<Transaction> trResult = null;
        try {
            trResult = CreditCardUtil.voidTransaction(dp.getTransactionId());
        } catch(Exception e) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("billing.errors.generic.error", HasteerConstants.DEFAULT_SUPPORT_PAGE));
            saveErrors(request, errors);
            BraintreeError.insertError(dp.getDealId(), dp.getBuyerId(), null, e.getMessage(), -1);
        }

        if(trResult != null && !trResult.isSuccess()) {
            if (trResult.getTransaction() != null) {
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

        if(!errors.isEmpty()) {
            return setForm(mapping, form, user);
        }

        dp.delete();
        os.delete();
        DealParticipantsLog log = new DealParticipantsLog();
        log.setDealId(deal.getDealId());
        log.setOptionId(0);
        log.setBuyerId(user.getUserId());
        log.setLogDate(new Date());
        log.setAction(LogAction.LEAVE.getActionType());
        log.setIp(request.getRemoteAddr());
        log.save();

        messages.add(Globals.MESSAGE_KEY, 
                new ActionMessage("JoinDealAction.messages.leftDeal", Jsp.getUrlBase()));
        saveMessages(request, messages);
        return (mapping.findForward("home"));
    }

    @Override
    public ActionForward previous(ActionMapping mapping) {
        return (mapping.findForward("success"));
    }

    @Override
    public ActionForward next(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {

        JoinDealForm jForm = (JoinDealForm) form;
        Deal deal = Deal.getById(jForm.getDealId());
        ActionErrors errors = new ActionErrors();

        JoinDealSessionObject jdso = JoinDealSessionObject.getInstanceFromSession(request);
        User user = User.getUserFromSession(request);

        if(!deal.isRunning()) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("JoinDealAction.errors.dealClosed", Jsp.getUrlBase()));
            saveErrors(request, errors);
            return setForm(mapping, form, user);
        }

        if(jForm.getPrOptionId() <= 0) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("errors.required", "Selecting a pricing option"));
            saveErrors(request, errors);
            return setForm(mapping, form, user);
        }
        DealPricingOption prOption = DealPricingOption.getById(jForm.getPrOptionId());
        if (prOption != null) {
            int numParticipants = DealParticipant.getNumParticipantsInDeal(deal.getDealId());
            int maxAllowedParticipants = Deal.getMaxAllowedParticipants(deal.getDealId());

            if((numParticipants + jForm.getQuantity()) > maxAllowedParticipants)
            {
                errors.add(Globals.ERROR_KEY, new ActionMessage("JoinDealAction.errors.notEnoughQuantity"));
                saveErrors(request, errors);
                return setForm(mapping, form, user);
            }

            RewardPointsBalance rewardPointsBalance = RewardPointsBalance.getByUserId(user.getUserId());
            int rewardPointsExchangeRate = RewardPointsBalance.getRewardPointsExchangeRate();
            if(rewardPointsBalance != null && rewardPointsBalance.getPointsCashValue() < jForm.getRedeemedAmount()) {
                errors.add(Globals.ERROR_KEY, new ActionMessage("JoinDealAction.errors.notEnoughRedeemablePoints"));
                saveErrors(request, errors);
                return setForm(mapping, form, user);
            }

            DealParticipant dp = DealParticipant.getByDealIdBuyerId(deal.getDealId(), user.getUserId());
            if (dp == null) {
                dp = new DealParticipant();
            }

            OrderSummary os = OrderSummary.getById(dp.getOrderSummaryId());
            if (os == null) {
                os = new OrderSummary();
            }

            dp.setDealId(prOption.getDealId());
            dp.setPricingOptionId(prOption.getOptionId());
            dp.setBuyerId(user.getUserId());
            dp.setJoinDate(new Date());
            dp.setIp(request.getRemoteAddr());
            os.setQuantity(jForm.getQuantity());
            os.setRedeemedPoints(rewardPointsExchangeRate * jForm.getRedeemedAmount());
            os.setUnitPrice(prOption.getPrice());

            HttpSession session = request.getSession(false);
            if(session.getAttribute("referred_deal_id") != null) {
                long rDealId = 0;
                try {
                    rDealId = Long.parseLong((String)session.getAttribute("referred_deal_id"));
                } catch(Exception e) {}

                if(rDealId == prOption.getDealId()) {
                    if(session.getAttribute("referrer_id") != null) {
                        try {
                            long referrerId = Long.parseLong((String)session.getAttribute("referrer_id"));
                            if(referrerId != user.getUserId())
                                dp.setReferrerId(referrerId);
                        } catch(Exception e) {}
                    }

                    dp.setReferralSource((String)session.getAttribute("referrer_source"));
                }              
            }

            if(jdso == null) {
                jdso =  new JoinDealSessionObject();
                jdso.setDealParticipant(dp);
                jdso.setOrderSummary(os);
                session.setAttribute("jdso", jdso);
            } else {
                jdso.setDealParticipant(dp);
                jdso.setOrderSummary(os);
            }
        }

        return mapping.findForward("next");
    }

    public ActionForward quickJoin(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
        ActionForward result = next(mapping, form, request, response);
        if(!"next".equals(result.getName()))
            return result;

        User user = User.getUserFromSession(request);
        JoinDealSessionObject jdso = JoinDealSessionObject.getInstanceFromSession(request);
        jdso.setCreditCardDetail(CreditCardDetail.getMostRecentByUserId(user.getUserId()));
        jdso.setShippingAddress(ShippingAddress.getMostRecentByUserId(user.getUserId()));
        
        return mapping.findForward("verify");
    }
}
