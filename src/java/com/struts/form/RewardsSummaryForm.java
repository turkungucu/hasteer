
package com.struts.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

import com.dao.RewardPointsLog;

import java.util.List;
import java.util.ArrayList;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Alinur Goksel
 */
public class RewardsSummaryForm extends ActionForm {
    
    private int points;
    private String lastUpdated;
    private List<RewardPointsLog> activities;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        activities = new ArrayList<RewardPointsLog>();
    }
    
    public List<RewardPointsLog> getActivities() {
        return activities;
    }

    public void setActivities(List<RewardPointsLog> activities) {
        this.activities = activities;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
