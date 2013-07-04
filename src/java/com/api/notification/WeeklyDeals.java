/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api.notification;

import org.apache.velocity.VelocityContext;

import java.util.List;

import com.dao.Deal;
import com.dao.User;
import com.api.Jsp;

/**
 *
 * @author Alinur Goksel
 */
public class WeeklyDeals extends BaseVelocityNotificationRule {

    private User user;
    private List<Deal> deals;

    public WeeklyDeals(User user, List<Deal> deals) {
        this.user = user;
        this.deals = deals;
    }

    @Override
    protected VelocityContext getVelocityContext() {
        String unsubscribeUrl = Jsp.getUnsubscribeUrl(user.getEmail(), "Weekly Deals Newsletter");

        VelocityContext context = new VelocityContext();
        context.put("deals", deals);
        context.put("unsubscribe-url", unsubscribeUrl);
        context.put("to", user.getEmail());

        return context;
    }

    @Override
    protected User getUser() {
        return user;
    }
   
}
