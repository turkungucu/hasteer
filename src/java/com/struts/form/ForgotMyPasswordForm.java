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
public class ForgotMyPasswordForm extends ValidatorForm {
    private String cmd;
    private String emailAddr;

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
        if(!"submit".equalsIgnoreCase(cmd))
            return new ActionErrors();

        return super.validate(mapping, request);
    }

}
