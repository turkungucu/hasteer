/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api.notification;

import com.dao.OrderSummary;
import com.dao.CreditCardDetail;
import com.dao.User;
import com.api.Jsp;
import com.constants.HasteerConstants;

import org.apache.velocity.VelocityContext;

/**
 *
 * @author Alinur Goksel
 */
public class JoinDealConfirmation extends BaseVelocityNotificationRule {

    private OrderSummary orderSummary;
    private CreditCardDetail ccDetail;
    private String productName;
    private String shippingAddress;
    private User user;

    public JoinDealConfirmation(OrderSummary orderSummary, CreditCardDetail ccDetail, 
            String productName, String shippingAddress) {
        this.orderSummary = orderSummary;
        this.ccDetail = ccDetail;
        this.productName = productName;
        this.shippingAddress = shippingAddress;
        this.user = User.getUserById(ccDetail.getUserId());
    }

    @Override
    protected VelocityContext getVelocityContext() {
        String baseUrl = Jsp.getUrlBase();

        VelocityContext context = new VelocityContext();
        context.put("orderSummary", orderSummary);
        context.put("ccDetail", ccDetail);
        context.put("productName", productName);
        context.put("shippingAddress", shippingAddress);
        context.put("myDealsUrl", baseUrl + HasteerConstants.MY_DEALS_PAGE);

        return context;
    }

    @Override
    protected User getUser() {
        return user;
    }

}
