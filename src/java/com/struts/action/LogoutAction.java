/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.dao.User;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author ecolak
 */
public class LogoutAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
    {
        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession(false);
        if(session == null){
            errors.add("noSession", new ActionMessage("LogoutAction.errors.noSession"));
            saveErrors(request,errors);
            return (mapping.findForward("failure"));
        }

        User user = (User)session.getAttribute("user");
        if(user == null){
            errors.add("userNotInSession", new ActionMessage("LogoutAction.errors.userNotInSession"));
            saveErrors(request,errors);
            return (mapping.findForward("failure"));
        }

        session.removeAttribute("user");
        session.invalidate();
        return (mapping.findForward("success"));
    }
}
