/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author ecolak
 */
public class LoginForm extends ValidatorForm {
    private String username;
    private String password;
    private String redirectUrl;
    private String cmd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRedirectUrl(){
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl){
        this.redirectUrl = redirectUrl;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
        if(!"submit".equals(cmd))
            return new ActionErrors();

        return super.validate(mapping, request);
    }
}
