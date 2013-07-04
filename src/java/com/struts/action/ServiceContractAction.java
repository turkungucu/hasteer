/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dao.ServiceContract;

import com.struts.form.ServiceContractForm;
/**
 *
 * @author Alinur Goksel
 */
public class ServiceContractAction extends Action {
    
    private static final String SUCCESS = "success";
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ServiceContractForm scform = (ServiceContractForm) form;
        ServiceContract serviceContract = ServiceContract.getMostRecentContractByType(scform.getView());

        if (serviceContract == null) {
            scform.setContents("invalid type");
        } else {
            scform.setContents(new String(serviceContract.getContents()));
        }
        
        return mapping.findForward(SUCCESS);
    }

}
