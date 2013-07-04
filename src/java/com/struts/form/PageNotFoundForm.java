package com.struts.form;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author Alinur Goksel
 */
public class PageNotFoundForm extends ActionForm {
    
    private String badUrl;

    public String getBadUrl() {
        return badUrl;
    }

    public void setBadUrl(String badUrl) {
        this.badUrl = badUrl;
    }

}
