/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.action;

import com.constants.HasteerConstants;
import com.dao.User;
import com.struts.form.RegistrationConfirmationForm;
import com.util.AuthUtil;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class RegistrationConfirmationAction extends Action {
    private Log log = LogFactory.getLog(RegistrationConfirmationAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response)
    {
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        RegistrationConfirmationForm frm = (RegistrationConfirmationForm)form;

        User user = User.getUserById(frm.getUserId());
        if(user == null){
            errors.add(Globals.ERROR_KEY, new ActionMessage("RegistrationConfirmationAction.errors.uidDoesNotExist"));
            saveErrors(request,errors);
            return mapping.findForward("failure");
        }

        String confCode = "";
        try {
            confCode = AuthUtil.encryptWithMD5andHex(user.getEmail());
        } catch(Exception e){
            System.err.println(e);
        }
        
        String urlDecodedConfCode = null;
        try {
            urlDecodedConfCode = URLDecoder.decode(frm.getConfCode(),"UTF-8"); 
        } catch(Exception e){
            errors.add(Globals.ERROR_KEY, new ActionMessage("dbException",e.getMessage()));
            saveErrors(request,errors);
            return mapping.findForward("failure");
        }

        if(!confCode.equals(frm.getConfCode())) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("RegistrationConfirmationAction.errors.badConfCode"));
            saveErrors(request,errors);
            return mapping.findForward("failure");
        }

        user.setEmailVerified(true);
        user.save();
        messages.add(Globals.MESSAGE_KEY, new ActionMessage("RegistrationConfirmationAction.messages.registrationConfirmed",
                                                            "<a href=\"" + HasteerConstants.LOGIN_PAGE + "\">here</a>"));
        saveMessages(request, messages);
        return mapping.findForward("success");
    }
}
