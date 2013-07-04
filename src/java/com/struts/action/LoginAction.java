/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.struts.action;

import com.dao.User;
import com.struts.form.LoginForm;
import com.util.AuthUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionRedirect;

/**
 *
 * @author ecolak
 */
public class LoginAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {

        ActionErrors errors = new ActionErrors();
        LoginForm f = (LoginForm) form;
        String cmd = f.getCmd();
        if(!"submit".equals(cmd))
            return (mapping.findForward("failure"));

        String username = f.getUsername();
        String password = f.getPassword();
        String encPassword = null;

        User user = null;
        try {
            user = User.getUserByUsername(username);
        } catch (Exception e) {
            System.err.println(e);
        }

        if (user == null) {
            errors.add("usernameDoesNotExist", new ActionMessage("LoginAction.errors.usernameDoesNotExist"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        if (!user.isEmailVerified()) {
            errors.add("emailNotVerified", new ActionMessage("LoginAction.errors.emailNotVerified"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        try {
            encPassword = AuthUtil.encryptWithMD5(password);
        } catch (Exception e) {
            System.err.println(e);
        }

        if (!user.getPassword().equals(encPassword)) {
            errors.add("invalidUsernamePassword", new ActionMessage("LoginAction.errors.invalidUsernamePassword"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }
        
        HttpSession session = request.getSession();
        if (session != null) {
            session.setAttribute("user", user);
        }

        String redirectUrl = (String)session.getAttribute("redirectUrl");
        if(redirectUrl != null) {
            ActionRedirect ar = new ActionRedirect(redirectUrl);
            return ar;
        }

        return (mapping.findForward("success"));
    }
}
