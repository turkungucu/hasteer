/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api.notification;

import org.apache.velocity.VelocityContext;

import com.api.Jsp;
import com.dao.User;
import com.constants.HasteerConstants;

/**
 *
 * @author Alinur Goksel
 */
public class DealStatusNotification extends BaseVelocityNotificationRule {

    public boolean success;
    public String productName;
    public User user;
    public long orderSummaryId;

    public DealStatusNotification(boolean success, String productName, User user, long orderSummaryId) {
        this.success = success;
        this.productName = productName;
        this.user = user;
        this.orderSummaryId = orderSummaryId;
    }

    @Override
    protected VelocityContext getVelocityContext() throws Exception {
        VelocityContext context = new VelocityContext();
        context.put("success", success);
        context.put("myDealsUrl", Jsp.getUrlBase() + HasteerConstants.MY_DEALS_PAGE);
        context.put("productName", productName);
        context.put("orderSummaryId", orderSummaryId);

        return context;
    }

        @Override
    protected User getUser() {
        return user;
    }

}
