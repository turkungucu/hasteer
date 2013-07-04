/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

import com.dao.User;
import org.apache.struts.Globals;
/**
 *
 * @author ecolak
 */
public class CreateAccountForm extends ValidatorForm {
    private static final String GLOBAL_ERROR_MESSAGE = "CreateAccountForm.error";
    private String username;
    private String email1;
    private String email2;
    private String password1;
    private String password2;
    private String userTypeStr;
    private int userType;
    private String cmd;

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getUserTypeStr(){
        return userTypeStr;
    }

    public void setUserTypeStr(String userTypeStr){
        this.userTypeStr = userTypeStr;
    }

    public int getUserType(){
        return userType;
    }

    public void setUserType(int userType){
        this.userType = userType;
    }

    public String getCmd(){
        return cmd;
    }

    public void setCmd(String cmd){
        this.cmd = cmd;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
        if(!"submit".equals(cmd))
            return new ActionErrors();

        ActionErrors errors = super.validate(mapping, request);
        
        if(errors == null)
            errors = new ActionErrors();
        
        else if(errors.size() == 0) {
            try {
                setUserType(Integer.parseInt(userTypeStr));
            } catch(NumberFormatException ne){
                errors.add(Globals.ERROR_KEY,
                        new ActionMessage("CreateAccountForm.errors.invalidUserType"));
            }
      
            if(!email1.equals(email2))
                errors.add(Globals.ERROR_KEY,
                        new ActionMessage("CreateAccountForm.errors.differentEmails"));

            if(!password1.equals(password2))
                errors.add(Globals.ERROR_KEY,
                        new ActionMessage("CreateAccountForm.errors.differentPasswords"));

            if(errors.size() == 0){
                User existingUser = User.getUserByUsername(getUsername());
                if(existingUser != null){
                    errors.add(Globals.ERROR_KEY, new ActionMessage("CreateAccountForm.errors.usernameExists"));
                }
            }

            if(errors.size() == 0){
                User existingUser = User.getUserByEmail(getEmail1());
                if(existingUser != null){
                    errors.add(Globals.ERROR_KEY, new ActionMessage("CreateAccountForm.errors.emailExists"));
                }
            }
        }
        
        return errors;
    }
}
