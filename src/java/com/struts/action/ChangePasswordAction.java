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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.Globals;

import com.struts.form.ChangePasswordForm;
import com.util.AuthUtil;
import com.dao.User;

/**
 *
 * @author Alinur Goksel
 */
public class ChangePasswordAction extends Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ChangePasswordForm frm = (ChangePasswordForm)form;

        if("submit".equals(frm.getCmd())) {
            User loggedInUser = AuthUtil.getLoggedInUser(request);
            loggedInUser.setPassword(AuthUtil.encryptWithMD5(frm.getNewPassword1()));
            loggedInUser.save();

            ActionMessages messages = new ActionMessages();
            messages.add(Globals.MESSAGE_KEY, new ActionMessage("shared.success.changesSaved"));
            saveMessages(request, messages);
        }

        return mapping.findForward(SUCCESS);
    }
}
