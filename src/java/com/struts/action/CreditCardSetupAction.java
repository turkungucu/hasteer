/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.action;

import com.api.JoinDealSessionObject;
import com.dao.CreditCardDetail;
import com.dao.ShippingAddress;
import com.dao.User;
import com.dao.Deal;
import com.api.Jsp;
import com.struts.form.CreditCardSetupForm;
import com.util.CreditCardUtil;
import com.util.DateUtils;

import java.util.Date;
import java.util.Calendar;
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
public class CreditCardSetupAction extends Action implements IWorkflowAction {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ActionForward result = mapping.findForward("failure");
        CreditCardSetupForm frm = (CreditCardSetupForm) form;
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();

        User user = User.getUserFromSession(request);
        if (user == null) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("userDoesNotExist"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        JoinDealSessionObject jdso = JoinDealSessionObject.getInstanceFromSession(request);
        if(jdso == null) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("Workflow.errors.invalidDeal"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        Deal deal = Deal.getById(jdso.getDealParticipant().getDealId());
        if("next".equalsIgnoreCase(frm.getCmd()) || "previous".equalsIgnoreCase(frm.getCmd()) ||
           "delete".equalsIgnoreCase(frm.getCmd()))
        {
            if(!deal.isRunning()) {
                errors.add(Globals.ERROR_KEY, new ActionMessage("JoinDealAction.errors.dealClosed", Jsp.getUrlBase()));
                saveErrors(request, errors);
                return (mapping.findForward("failure"));
            }
        }

        if ("next".equalsIgnoreCase(frm.getCmd())) {
            result = next(mapping, form, request, response);
        } else if("previous".equalsIgnoreCase(frm.getCmd())) {
            result = previous(mapping);
        } else if("delete".equalsIgnoreCase(frm.getCmd())) {
            // do not delete card, just mark it deleted
            CreditCardDetail ccd = CreditCardDetail.getById(frm.getDeleteId());
            if(ccd != null) {
                ccd.delete();
                frm.reset(mapping, request);
                messages.add(Globals.MESSAGE_KEY, new ActionMessage("CreditCardSetup.messages.card.deleted"));
                result = mapping.findForward("success");
            }
        }
        else {
            CreditCardDetail ccd = CreditCardDetail.getById(jdso.getDealParticipant().getCreditCardId());
            setForm(frm, jdso.getCreditCardDetail() != null ? jdso.getCreditCardDetail() : ccd);
            result = mapping.findForward("success");
        }
        return result;
    }

    // This does not save any credit card details to db
    // It only puts them into the session
    private boolean upsertCreditCard(CreditCardSetupForm form, long ccId, User user, JoinDealSessionObject jdso) {
        CreditCardDetail ccd = ccId > 0 ? CreditCardDetail.getById(ccId) : new CreditCardDetail();

        ccd.setFirstName(form.getFirstName());
        ccd.setLastName(form.getLastName());
        ccd.setAddress1(form.getAddress1());
        ccd.setAddress2(form.getAddress2());
        ccd.setCity(form.getCity());
        ccd.setState(form.getState());
        ccd.setZipCode(form.getZipCode());
        ccd.setCountry(form.getCountry());
        ccd.setCardholderName(form.getCardholderName());
        ccd.setCreditCardNum(form.getCreditCardNumber()); // not to be persisted in DB
        ccd.setCardType(form.getCardType());
        ccd.setCvv(form.getCvv());
        ccd.setLastFour(CreditCardUtil.getEncodedLastFourDigits(form.getCreditCardNumber()));
        ccd.setExpiryDate(DateUtils.getDateFromMonthYear(
                Integer.parseInt(form.getExpMonth())-1, Integer.parseInt(form.getExpYear())));
        ccd.setUserId(user.getUserId());

        Date now = new Date();
        if (ccId <= 0) {
            ccd.setDateCreated(now);
        }
        ccd.setDateModified(now);
        ccd.setDoNotSaveToDb(form.isDoNotSave());
        
        jdso.setCreditCardDetail(ccd);
        if(form.isSameAsShippingAddr()) {
            ShippingAddress shAddr = new ShippingAddress();
            shAddr.setUserId(user.getUserId());
            shAddr.setAddress1(ccd.getAddress1());
            shAddr.setAddress2(ccd.getAddress2());
            shAddr.setCity(ccd.getCity());
            shAddr.setState(ccd.getState());
            shAddr.setZipCode(ccd.getZipCode());
            shAddr.setCountry(ccd.getCountry());
            shAddr.setFirstName(ccd.getFirstName());
            shAddr.setLastName(ccd.getLastName());
            shAddr.setDoNotSaveToDb(ccd.isDoNotSaveToDb());
            jdso.setShippingAddress(shAddr);
        } else {
            // reset shipping whenever checkbox is unchecked (i.e. in edit mode) and next is pressed.
            jdso.setShippingAddress(null);
        }
        
        return true;
    }

    private void setForm(CreditCardSetupForm form, CreditCardDetail ccd) {
        if(ccd != null) {
            form.setFirstName(ccd.getFirstName());
            form.setLastName(ccd.getLastName());
            form.setAddress1(ccd.getAddress1());
            form.setAddress2(ccd.getAddress2());
            form.setCity(ccd.getCity());
            form.setState(ccd.getState());
            form.setZipCode(ccd.getZipCode());
            form.setCountry(ccd.getCountry());
            form.setCardholderName(ccd.getCardholderName());
            form.setCardType(ccd.getCardType());
            form.setCreditCardNumber(CreditCardUtil.maskCCNumberByType(
                    CreditCardUtil.decodeLastFourDigits(ccd.getLastFour()), ccd.getCardType()));
            Calendar cal = Calendar.getInstance();
            cal.setTime(ccd.getExpiryDate());
            // JAN is 0 so add 1 to account for the offset
            int month = cal.get(Calendar.MONTH) + 1;
            form.setExpMonth(month < 10 ? "0" + month : String.valueOf(month));
            form.setExpYear(String.valueOf(cal.get(Calendar.YEAR)));
            if(ccd.getCreditCardDetailsId() != null)
                form.setWccId(ccd.getCreditCardDetailsId());

            form.setDoNotSave(ccd.isDoNotSaveToDb());
        }
    }

    @Override
    public ActionForward next(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
        ActionForward next = mapping.findForward("next");
        CreditCardSetupForm frm = (CreditCardSetupForm) form;
        
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();

        JoinDealSessionObject jdso = JoinDealSessionObject.getInstanceFromSession(request);
        User user = User.getUserFromSession(request);

        if(frm.getWccId() > 0) {
            CreditCardDetail ccd = CreditCardDetail.getById(frm.getWccId());
            if(ccd == null) {
                errors.add(Globals.ERROR_KEY, new ActionMessage("errors.does.not.exist", "Credit Card"));
                saveErrors(request, errors);
                return (mapping.findForward("failure"));
            }

            jdso.setCreditCardDetail(ccd);
            return next;
        } 
        try {
            upsertCreditCard(frm, 0, user, jdso);
            if (frm.isSameAsShippingAddr()) {
                next = mapping.findForward("verify");
            }
        } catch(Exception e) {
            messages.add(Globals.ERROR_KEY, new ActionMessage("dbException", e.getMessage()));
            saveErrors(request, messages);
            return mapping.findForward("failure");
        }

        return next;
    }

    @Override
    public ActionForward previous(ActionMapping mapping) {
        return mapping.findForward("previous");
    }

}
