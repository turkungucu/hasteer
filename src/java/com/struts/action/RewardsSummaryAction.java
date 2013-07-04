
package com.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dao.RewardPointsBalance;
import com.dao.RewardPointsLog;
import com.dao.User;
import com.util.AuthUtil;
import com.util.DateUtils;
import com.struts.form.RewardsSummaryForm;
/**
 *
 * @author Alinur Goksel
 */
public class RewardsSummaryAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        User user = AuthUtil.getLoggedInUser(request);
        RewardsSummaryForm frm = (RewardsSummaryForm) form;
        long userId = user.getUserId();

        RewardPointsBalance rpb = RewardPointsBalance.getByUserId(userId);
        if (rpb != null) {
            frm.setPoints(rpb.getPoints());
            frm.setLastUpdated(DateUtils.dateFormat.format(rpb.getDateModified()));
            frm.setActivities(RewardPointsLog.getByUserId(userId));
        }
       
        return mapping.findForward(SUCCESS);
    }
}
