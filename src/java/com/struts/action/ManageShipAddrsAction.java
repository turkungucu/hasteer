/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionErrors;

import com.api.JoinDealSessionObject;
import com.api.Jsp;
import com.struts.form.ManageShipAddrsForm;
import com.dao.ShippingAddress;
import com.dao.User;
import com.dao.Deal;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Alinur Goksel
 */
public class ManageShipAddrsAction extends Action implements IWorkflowAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ActionForward result = mapping.findForward("failure");
        ManageShipAddrsForm frm = (ManageShipAddrsForm) form;
       
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();

        JoinDealSessionObject jdso = JoinDealSessionObject.getInstanceFromSession(request);
        if(jdso == null) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("Workflow.errors.invalidDeal"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        Deal deal = Deal.getById(jdso.getDealParticipant().getDealId());
        String cmd = frm.getCmd();
        if("next".equalsIgnoreCase(cmd) || "previous".equalsIgnoreCase(cmd) ||
           "delete".equalsIgnoreCase(cmd))
        {
            if(!deal.isRunning()) {
                errors.add(Globals.ERROR_KEY, new ActionMessage("JoinDealAction.errors.dealClosed", Jsp.getUrlBase()));
                saveErrors(request, errors);
                return (mapping.findForward("failure"));
            }
        }

        if ("next".equalsIgnoreCase(cmd)) {
            result = next(mapping, form, request, response);
        } else if("previous".equalsIgnoreCase(cmd)) {
            result = previous(mapping);
        } else if("delete".equalsIgnoreCase(cmd)) {
            ShippingAddress addr = ShippingAddress.getById(frm.getDeleteId());
            if(addr != null) {
                addr.delete();
                frm.reset(mapping, request);
                messages.add(Globals.MESSAGE_KEY, new ActionMessage("shared.forms.deleted.success", "Shipping Address"));
                result = mapping.findForward("success");
            }
        } else {
            setForm(frm, jdso.getShippingAddress() != null
                    ? jdso.getShippingAddress()
                    : ShippingAddress.getById(jdso.getDealParticipant().getShippingAddressId()));
            result = mapping.findForward("success");
        }
        return result;
    }

    private ShippingAddress getNewShippingAddress(ManageShipAddrsForm form, long userId) {
        ShippingAddress shipAddr = new ShippingAddress();
        shipAddr.setUserId(userId);
        shipAddr.setCompany(form.getCompany());
        shipAddr.setFirstName(form.getFirstName());
        shipAddr.setLastName(form.getLastName());
        shipAddr.setAddress1(form.getAddress1());
        shipAddr.setAddress2(form.getAddress2());
        shipAddr.setCity(form.getCity());
        shipAddr.setState(form.getState());
        shipAddr.setZipCode(form.getZipCode());
        shipAddr.setCountry(form.getCountry());
        shipAddr.setPhoneNumber(form.getPhone1() + "-" + form.getPhone2() + "-" + form.getPhone3());
        shipAddr.setDoNotSaveToDb(form.isDoNotSave());
        
        return shipAddr;
    }

    private void setForm(ManageShipAddrsForm form, ShippingAddress shipAddr) {
        if(shipAddr != null) {
            form.setCompany(shipAddr.getCompany());
            form.setFirstName(shipAddr.getFirstName());
            form.setLastName(shipAddr.getLastName());
            form.setAddress1(shipAddr.getAddress1());
            form.setAddress2(shipAddr.getAddress2());
            form.setCity(shipAddr.getCity());
            form.setState(shipAddr.getState());
            form.setZipCode(shipAddr.getZipCode());
            form.setCountry(shipAddr.getCountry());
            String phoneNumber = shipAddr.getPhoneNumber();
            String[] phoneTokens = StringUtils.split(phoneNumber, '-');
            if(phoneTokens != null && phoneTokens.length == 3) {
                form.setPhone1(phoneTokens[0]);
                form.setPhone2(phoneTokens[1]);
                form.setPhone3(phoneTokens[2]);
            }

            if(shipAddr.getShippingAddressId() > 0)
                form.setwAddrId(shipAddr.getShippingAddressId());

            form.setDoNotSave(shipAddr.isDoNotSaveToDb());
        }
    }

    @Override
    public ActionForward next(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {

        ManageShipAddrsForm frm = (ManageShipAddrsForm) form;
        ActionErrors errors = new ActionErrors();

        JoinDealSessionObject jdso = JoinDealSessionObject.getInstanceFromSession(request);
        User user = User.getUserFromSession(request);

        ShippingAddress addr = null;

        if (frm.getwAddrId() > 0) {
            addr = ShippingAddress.getById(frm.getwAddrId());
        } else {
            addr = getNewShippingAddress(frm, user.getUserId());
        }

        if(addr == null) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("errors.does.not.exist", "Shipping Address"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }      

        jdso.setShippingAddress(addr);

        return mapping.findForward("next");
    }

    @Override
    public ActionForward previous(ActionMapping mapping) {
        return mapping.findForward("previous");
    }

}