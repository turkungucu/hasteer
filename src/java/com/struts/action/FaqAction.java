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

import com.struts.form.PageNotFoundForm;
import com.api.Jsp;

/**
 *
 * @author Alinur Goksel
 */
public class FaqAction extends Action {
    
    private static final String SUCCESS = "success";
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
      
        return mapping.findForward(SUCCESS);
    }

}
