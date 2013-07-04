/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api.notification;

import com.dao.CreditCardDetail;
import com.dao.User;
import com.dao.OrderSummary;

import org.apache.velocity.VelocityContext;

/**
 *
 * @author Alinur Goksel
 */
public class ShipmentNotification extends BaseVelocityNotificationRule {
    
    private OrderSummary orderSummary;
    private CreditCardDetail ccDetail;
    private String productName;
    private String shippingAddress;
    private User user;

    public ShipmentNotification(OrderSummary orderSummary, CreditCardDetail ccDetail, String productName, String shippingAddress) {
        this.orderSummary = orderSummary;
        this.ccDetail = ccDetail;
        this.productName = productName;
        this.shippingAddress = shippingAddress;
        this.user = User.getUserById(ccDetail.getUserId());
    }
    
    @Override
    protected VelocityContext getVelocityContext() throws Exception {
        VelocityContext context = new VelocityContext();
        context.put("orderSummary", orderSummary);
        context.put("ccDetail", ccDetail);
        context.put("productName", productName);
        context.put("shippingAddress", shippingAddress);
        context.put("trackUrl", getTrackingUrl());

        return context;
    }

    @Override
    protected User getUser() {
        return user;
    }

    // Temp solution, there should be a Hasteer JSP that retrieves tracking info
    private String getTrackingUrl() {
        String trackingUrl = null;

        if (OrderSummary.COURIER_USPS.equals(orderSummary.getCourier())) {
            trackingUrl = orderSummary.getTrackingId();
        }

        return trackingUrl;
    }

}
