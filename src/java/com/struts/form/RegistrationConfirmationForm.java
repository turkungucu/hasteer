/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author ecolak
 */
public class RegistrationConfirmationForm extends ValidatorForm {
    private String uid;
    private String confCode;

    private long userId;

    public String getUid(){
        return uid;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public String getConfCode(){
        return confCode;
    }

    public void setConfCode(String confCode){
        this.confCode = confCode;
    }

    public long getUserId(){
        return userId;
    }

    public void setUserId(long userId){
        this.userId = userId;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
        ActionErrors errors = super.validate(mapping, request);

        if(errors == null)
            errors = new ActionErrors();

        long u = 0;
        try{
            u = Long.parseLong(uid);
            setUserId(u);
        } catch(Exception e){
            errors.add(Globals.ERROR_KEY, new ActionMessage("RegistrationConfirmation.errors.unparseableUid"));
        }

        return errors;
    }
}
