package com.struts.form;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author Alinur Goksel
 */
public class ServiceContractForm extends ActionForm {
    
    private String view;
    private String contents;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

}
