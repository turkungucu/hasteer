/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.action;

import com.api.JoinDealSessionObject;
import com.dao.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author ecolak
 */
public interface IWorkflowAction {
    public ActionForward previous(ActionMapping mapping);
    
    public ActionForward next(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response);
}
