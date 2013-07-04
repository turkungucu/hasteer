/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api;

import com.dao.CreditCardDetail;
import com.dao.DealParticipant;
import com.dao.ShippingAddress;
import com.dao.OrderSummary;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ecolak
 */
public class JoinDealSessionObject {
    private DealParticipant dealParticipant;
    private CreditCardDetail creditCardDetail;
    private ShippingAddress shippingAddress;
    private OrderSummary orderSummary;

    public CreditCardDetail getCreditCardDetail() {
        return creditCardDetail;
    }

    public void setCreditCardDetail(CreditCardDetail creditCardDetail) {
        this.creditCardDetail = creditCardDetail;
    }

    public DealParticipant getDealParticipant() {
        return dealParticipant;
    }

    public void setDealParticipant(DealParticipant dealParticipant) {
        this.dealParticipant = dealParticipant;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public OrderSummary getOrderSummary() {
        return orderSummary;
    }

    public void setOrderSummary(OrderSummary orderSummary) {
        this.orderSummary = orderSummary;
    }

    public static JoinDealSessionObject getInstanceFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (JoinDealSessionObject)session.getAttribute("jdso");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JoinDealSessionObject other = (JoinDealSessionObject) obj;
        if (this.dealParticipant != other.dealParticipant && (this.dealParticipant == null || !this.dealParticipant.equals(other.dealParticipant))) {
            return false;
        }
        if (this.creditCardDetail != other.creditCardDetail && (this.creditCardDetail == null || !this.creditCardDetail.equals(other.creditCardDetail))) {
            return false;
        }
        if (this.shippingAddress != other.shippingAddress && (this.shippingAddress == null || !this.shippingAddress.equals(other.shippingAddress))) {
            return false;
        }
        if (this.orderSummary != other.orderSummary && (this.orderSummary == null || !this.orderSummary.equals(other.orderSummary))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.dealParticipant != null ? this.dealParticipant.hashCode() : 0);
        hash = 89 * hash + (this.creditCardDetail != null ? this.creditCardDetail.hashCode() : 0);
        hash = 89 * hash + (this.shippingAddress != null ? this.shippingAddress.hashCode() : 0);
        hash = 89 * hash + (this.orderSummary != null ? this.orderSummary.hashCode() : 0);
        return hash;
    }

}
