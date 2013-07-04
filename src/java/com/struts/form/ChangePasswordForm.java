/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.form;

import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.Globals;

import com.dao.User;
import com.util.AuthUtil;

/**
 *
 * @author Alinur Goksel
 */
public class ChangePasswordForm extends ValidatorForm {

    public static String CLASS_NAME = ChangePasswordForm.class.getName();
    public static Logger sLogger = Logger.getLogger(CLASS_NAME);

    private String cmd;
    private String oldPassword;
    private String newPassword1;
    private String newPassword2;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }


    /**
     *
     */
    public ChangePasswordForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        if(!"submit".equals(cmd))
            return new ActionErrors();

        ActionErrors errors = super.validate(mapping, request);

        if(errors == null)
            errors = new ActionErrors();
        else if(errors.size() == 0) {
            User loggedInUser = AuthUtil.getLoggedInUser(request);

            try {
                if (!loggedInUser.getPassword().equals(AuthUtil.encryptWithMD5(getOldPassword()))) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("ChangePasswordForm.error.passwordNoMatch"));
                }
            } catch (Exception ex) {
                sLogger.log(Level.SEVERE, AuthUtil.MD5_ENCRYPTION_EXCEPTION_MSG, ex);
            }

            if(!newPassword1.equals(newPassword2))
                errors.add(Globals.ERROR_KEY, new ActionMessage("ChangePasswordForm.errors.differentPasswords"));
        }

        return errors;
    }
}
