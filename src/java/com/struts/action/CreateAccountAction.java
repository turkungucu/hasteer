/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.action;

import com.struts.form.CreateAccountForm;
import com.dao.User;
import com.dao.ServiceContract;
import com.dao.ContractAcceptanceLog;
import com.util.AuthUtil;
import com.api.notification.RegistrationConfirmation;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
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
public class CreateAccountAction extends Action {

    private Log log = LogFactory.getLog(CreateAccountAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
    {
        ActionMessages errors = new ActionErrors();
        CreateAccountForm f = (CreateAccountForm)form;
        if(!"submit".equals(f.getCmd()))
            return (mapping.findForward("failure"));

        User newUser = new User();
        newUser.setType(f.getUserType());
        newUser.setRegistrationTime(new Date());
        newUser.setStatus(User.UserStatus.INACTIVE.getValue());
        newUser.setEmailVerified(false);
        newUser.setEmail(f.getEmail1());
        newUser.setUsername(f.getUsername());
        
        try{
            newUser.setPassword(AuthUtil.encryptWithMD5(f.getPassword1()));
            newUser.save();
            // Generate a Registration Confirmation Email
            RegistrationConfirmation ur = new RegistrationConfirmation(newUser);
            ur.dispatchEmail();

            // Log acceptance of Terms of Use and Privacy Policy
            String ipAddr = request.getRemoteAddr();
            Date acceptedDate = newUser.getRegistrationTime();
            long userId = newUser.getUserId();

            ContractAcceptanceLog termsOfUse = new ContractAcceptanceLog(userId,
                    ServiceContract.getMostRecentContractByType(ServiceContract.TERMS_OF_USE).getContractId(),
                    acceptedDate, ipAddr);
            termsOfUse.save();

            ContractAcceptanceLog privacyPolicy = new ContractAcceptanceLog(userId,
                    ServiceContract.getMostRecentContractByType(ServiceContract.PRIVACY_POLICY).getContractId(),
                    acceptedDate, ipAddr);
            privacyPolicy.save();
        } catch(Exception e) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("dbException", e.getMessage()));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        return (mapping.findForward("success"));
    }
}
