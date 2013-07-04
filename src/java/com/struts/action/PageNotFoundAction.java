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
public class PageNotFoundAction extends Action {
    
    private static final String SUCCESS = "success";
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        PageNotFoundForm pageNotFoundForm = (PageNotFoundForm) form;
        String badUrl = null;

        // using getAttribute allows us to get the orginal url out of the page when a forward has taken place.
        Object queryString = request.getAttribute("javax.servlet.forward.query_string");
        Object requestURI = request.getAttribute("javax.servlet.forward.request_uri");

        if (requestURI != null) {
            badUrl = Jsp.getUrlBase() + String.valueOf(requestURI);
            if (queryString != null) {
                badUrl += "?" + String.valueOf(queryString);
            }
        }

        pageNotFoundForm.setBadUrl(badUrl);
        
        return mapping.findForward(SUCCESS);
    }

}
