
package com.dao;

import com.util.HibernateUtil;
import com.util.StringUtil;
import com.api.Pair;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.Date;

/**
 *
 * @author Alinur Goksel
 */
@Entity
@Table(name = "order_summary")
public class OrderSummary implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String HQL_GET_BY_ID = "SELECT o FROM OrderSummary o WHERE o.orderSummaryId = :orderSummaryId";

    public static final String COURIER_USPS = "usps";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "order_summary_id")
    private long orderSummaryId;
    @Basic(optional = false)
    @Column(name = "unit_price")
    private double unitPrice;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    @Basic(optional = false)
    @Column(name = "tax")
    private double tax;
    @Basic(optional = false)
    @Column(name = "shipping_cost")
    private double shippingCost;
    @Basic(optional = false)
    @Column(name = "shipping_method")
    private String shippingMethod;
    @Column(name = "redeemed_points")
    private int redeemedPoints;
    @Column(name = "courier")
    private String courier;
    @Column(name = "tracking_id")
    private String trackingId;
    @Column(name = "shipped_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shippedDate;

    @Transient
    // For display only. Holds all possible shipping methods and corresponding
    // prices available for this order.
    private LinkedList<Pair<String, Double>> shippingMethodsAndCost;

    public OrderSummary() {
    }

    public OrderSummary(Long orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public OrderSummary(long orderSummaryId, double unitPrice, int quantity, double tax, double shippingCost, String shippingMethod, int redeemedPoints, String courier, String trackingId, Date shippedDate) {
        this.orderSummaryId = orderSummaryId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.tax = tax;
        this.shippingCost = shippingCost;
        this.shippingMethod = shippingMethod;
        this.redeemedPoints = redeemedPoints;
        this.courier = courier;
        this.trackingId = trackingId;
        this.shippedDate = shippedDate;
    }

    public long getOrderSummaryId() {
        return orderSummaryId;
    }

    public void setOrderSummaryId(Long orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getFormattedUnitPrice() {
        return StringUtil.twoDeciFormat.format(unitPrice);
        //return StringUtil.getFormattedPrice(unitPrice);
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTax() {
        return tax;
    }

    public String getFormattedTax() {
        return StringUtil.twoDeciFormat.format(tax);
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public String getFormattedShippingCost() {
        return StringUtil.twoDeciFormat.format(shippingCost);
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public int getRedeemedPoints() {
        return redeemedPoints;
    }

    public void setRedeemedPoints(int redeemedPoints) {
        this.redeemedPoints = redeemedPoints;
    }

    public double getRedeemedAmount() {
        return redeemedPoints > 0 ? redeemedPoints / RewardPointsBalance.getRewardPointsExchangeRate() : 0;
    }

    public String getFormattedRedeemedAmount() {
        return StringUtil.twoDeciFormat.format(getRedeemedAmount());
        //return StringUtil.getFormattedPrice(getRedeemedAmount());
    }

    public double getSubtotal() {
        return quantity * unitPrice;
    }

    public String getFormattedSubtotal() {
        return StringUtil.twoDeciFormat.format(getSubtotal());
        //return StringUtil.getFormattedPrice(getSubtotal());
    }

    public double getTotal() {
        return (getSubtotal() - getRedeemedAmount()) + shippingCost + tax;
    }

    public String getFormattedTotal() {
        return StringUtil.twoDeciFormat.format(getTotal());
        //return StringUtil.getFormattedPrice(getTotal());
    }

    public LinkedList<Pair<String, Double>> getShippingMethodsAndCost() {
        return shippingMethodsAndCost;
    }

    public void setShippingMethodsAndCost(LinkedList<Pair<String, Double>> shippingMethodsAndCost) {
        this.shippingMethodsAndCost = shippingMethodsAndCost;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public static OrderSummary getById(long orderSummaryId) throws HibernateException {
        OrderSummary result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_ID);
        query.setLong("orderSummaryId", orderSummaryId);
        result = (OrderSummary)query.uniqueResult();
        tx.commit();

        return result;
    }

    public void save() throws HibernateException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(this);
        tx.commit();
    }

    public void delete() throws HibernateException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.delete(this);
        tx.commit();
    }

    @Override
    public String toString() {
        return "com.dao.OrderSummary[orderSummaryId=" + orderSummaryId + "]";
    }

}
