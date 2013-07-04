/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.User;
import com.util.AuthUtil;
import com.struts.form.ForgotMyPasswordForm;
import com.api.notification.PasswordReset;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 *
 * @author ecolak
 */
public class ForgotMyPasswordAction extends Action {

    private Log log = LogFactory.getLog(ForgotMyPasswordAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        
        ForgotMyPasswordForm f = (ForgotMyPasswordForm) form;
        if(!"submit".equals(f.getCmd()))
            return (mapping.findForward("failure"));

        User user = User.getUserByEmail(f.getEmailAddr());
        if (user == null) {
            errors.add("emailDoesNotExist",
                    new ActionMessage("ForgotMyPasswordForm.errors.emailDoesNotExist"));
            saveErrors(request,errors);
            return (mapping.findForward("failure"));
        }

        try {
            String randomPassword = AuthUtil.generateRandomPassword();      
            user.setPassword(AuthUtil.encryptWithMD5(randomPassword));
            user.save();

            // generate email
            PasswordReset pr = new PasswordReset(user, randomPassword);
            pr.dispatchEmail();
        } catch (Exception e) {
            errors.add("randomPasswordUnsuccessful", new ActionMessage("ForgotMyPasswordAction.errors.cannotgeneratePassword"));
            saveErrors(request,errors);
            return (mapping.findForward("failure"));
        }

        messages.add(Globals.MESSAGE_KEY, new ActionMessage("ForgotMyPasswordForm.passwordResetSuccessfully"));
        saveMessages(request, messages);
        return (mapping.findForward("success"));
    }
}
