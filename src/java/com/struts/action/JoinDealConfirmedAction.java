/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.action;

import com.api.JoinDealSessionObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionErrors;

/**
 *
 * @author ecolak
 */
public class JoinDealConfirmedAction extends Action{
    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
    {
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();

        JoinDealSessionObject jdso = (JoinDealSessionObject)request.getSession().getAttribute("jdso");
        if(jdso == null || jdso.getOrderSummary() == null) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("Workflow.errors.invalidDeal"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        messages.add(Globals.MESSAGE_KEY,
                new ActionMessage("JoinDealAction.messages.joinDeal",
                                  jdso.getOrderSummary().getOrderSummaryId()));
        saveMessages(request, messages);

        jdso = null;
        return mapping.findForward("success");
    }
}
