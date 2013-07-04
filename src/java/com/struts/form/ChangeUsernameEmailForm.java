/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.form;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

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
public class ChangeUsernameEmailForm extends ValidatorForm {

    public static String CLASS_NAME = ChangeUsernameEmailForm.class.getName();
    public static Logger sLogger = Logger.getLogger(CLASS_NAME);

    private String cmd;
    private String username;
    private String email;
    private String password;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }


    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        if(!"submit".equals(cmd))
            return new ActionErrors();

        ActionErrors errors = super.validate(mapping, request);

        if(errors == null)
            errors = new ActionErrors();
        else if(errors.size() == 0) {
            User loggedInUser = AuthUtil.getLoggedInUser(request);

            try {
                // If password doesn't match
                if (!loggedInUser.getPassword().equals(AuthUtil.encryptWithMD5(getPassword()))) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("ChangeUsernameEmailForm.error.passwordNoMatch"));
                }
            } catch (Exception ex) {
                sLogger.log(Level.SEVERE, AuthUtil.MD5_ENCRYPTION_EXCEPTION_MSG, ex);
            }

            // If username is changed
            if(!loggedInUser.getUsername().equalsIgnoreCase(getUsername())) {
                User existingUser = User.getUserByUsername(getUsername());
                if(existingUser != null){
                    errors.add(Globals.ERROR_KEY, new ActionMessage("CreateAccountForm.errors.usernameExists"));
                }
            }

            // If email is changed
            if(!loggedInUser.getEmail().equalsIgnoreCase(getEmail())) {
                User existingUser = User.getUserByEmail(getEmail());
                if(existingUser != null){
                    errors.add(Globals.ERROR_KEY, new ActionMessage("CreateAccountForm.errors.emailExists"));
                }
            }
        }

        return errors;
    }
}
