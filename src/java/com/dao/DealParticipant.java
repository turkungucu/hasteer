/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dao;

import com.util.HibernateUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ecolak
 */
@Entity
@Table(name = "deal_participants")
public class DealParticipant implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String HQL_GET_ALL = "SELECT d FROM DealParticipant d";
    private static String HQL_GET_BY_ID = "SELECT d FROM DealParticipant d WHERE d.dealParticipantId = :dealParticipantId";
    private static String HQL_GET_BY_DEAL_ID = "SELECT d FROM DealParticipant d WHERE d.dealId = :dealId";
    private static String HQL_GET_BY_PRICING_OPTION_ID = "SELECT d FROM DealParticipant d WHERE d.pricingOptionId = :pricingOptionId";
    private static String HQL_GET_BY_BUYER_ID = "SELECT d FROM DealParticipant d WHERE d.buyerId = :buyerId";
    private static String HQL_GET_BY_DEAL_ID_BUYER_ID = "SELECT d FROM DealParticipant d WHERE d.dealId = :dealId and d.buyerId = :buyerId";
    private static String HQL_GET_BY_DEAL_ID_OPTION_ID = "SELECT d FROM DealParticipant d WHERE d.dealId = :dealId and " +
                                                         "d.pricingOptionId = :pricingOptionId";
    private static String HQL_NUM_PARTICIPANTS_IN_DEAL = "SELECT count(0) from DealParticipant d where d.dealId = :dealId and " +
                                                         "d.pricingOptionId = :pricingOptionId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "deal_participant_id")
    private long dealParticipantId;

    @Basic(optional = false)
    @Column(name = "deal_id")
    private long dealId;

    @Basic(optional = false)
    @Column(name = "order_summary_id")
    private long orderSummaryId;

    @Basic(optional = false)
    @Column(name = "buyer_id")
    private long buyerId;

    @Basic(optional = false)
    @Column(name = "credit_card_id")
    private long creditCardId;

    @Basic(optional = false)
    @Column(name = "shipping_address_id")
    private long shippingAddressId;

    @Basic(optional = false)
    @Column(name = "join_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;

    @Basic(optional = false)
    @Column(name = "pricing_option_id")
    private long pricingOptionId;

    @Basic(optional = true)
    @Column(name = "referrer_id")
    private Long referrerId;

    @Basic(optional = true)
    @Column(name = "referral_source")
    private String referralSource;

    @Basic(optional = true)
    @Column(name = "ip")
    private String ip;

    @Basic(optional = true)
    @Column(name = "transaction_id")
    private String transactionId;

    public DealParticipant() {
    }

    public DealParticipant(long dealParticipantId) {
        this.dealParticipantId = dealParticipantId;
    }

    public DealParticipant(long dealParticipantId, long dealId, long buyerId, Date joinDate, long pricingOptionId,
            long creditCardId, long shippingAddressId, long orderSummaryId)
    {
        this.dealParticipantId = dealParticipantId;
        this.dealId = dealId;
        this.buyerId = buyerId;
        this.joinDate = joinDate;
        this.pricingOptionId = pricingOptionId;
        this.creditCardId = creditCardId;
        this.shippingAddressId = shippingAddressId;
        this.orderSummaryId = orderSummaryId;
    }

    public long getDealParticipantId() {
        return dealParticipantId;
    }

    public void setDealParticipantId(long dealParticipantId) {
        this.dealParticipantId = dealParticipantId;
    }

    public long getDealId() {
        return dealId;
    }

    public void setDealId(long dealId) {
        this.dealId = dealId;
    }

    public long getOrderSummaryId() {
        return orderSummaryId;
    }

    public void setOrderSummaryId(long orderSummaryId) {
        this.orderSummaryId = orderSummaryId;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public long getPricingOptionId() {
        return pricingOptionId;
    }

    public void setPricingOptionId(long pricingOptionId) {
        this.pricingOptionId = pricingOptionId;
    }

    public long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getReferralSource() {
        return referralSource;
    }

    public void setReferralSource(String referralSource) {
        this.referralSource = referralSource;
    }

    public long getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(Long referrerId) {
        this.referrerId = referrerId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public static List<DealParticipant> getAll() throws HibernateException {
        List<DealParticipant> result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_ALL);
        result = query.list();
        tx.commit();
        return result;
    }

    public static DealParticipant getById(long id) throws HibernateException {
        DealParticipant result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_ID);
        query.setLong("id", id);
        result = (DealParticipant)query.uniqueResult();
        tx.commit();

        return result;
    }

    public static List<DealParticipant> getByDealId(long dealId) throws HibernateException {
        List<DealParticipant> result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_DEAL_ID);
        query.setLong("dealId", dealId);
        result = query.list();
        tx.commit();

        return result;
    }

    public static List<DealParticipant> getByPricingOptionId(long prOptionId) throws HibernateException {
        List<DealParticipant> result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_PRICING_OPTION_ID);
        query.setLong("prOptionId", prOptionId);
        result = query.list();
        tx.commit();

        return result;
    }

    public static List<DealParticipant> getByBuyerId(long buyerId) throws HibernateException {
        List<DealParticipant> result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_BUYER_ID);
        query.setLong("buyerId", buyerId);
        result = query.list();
        tx.commit();

        return result;
    }

    public static DealParticipant getByDealIdBuyerId(long dealId, long buyerId) throws HibernateException {
        DealParticipant result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_DEAL_ID_BUYER_ID);
        query.setLong("dealId", dealId);
        query.setLong("buyerId", buyerId);
        result = (DealParticipant)query.uniqueResult();
        tx.commit();

        return result;
    }

    public static DealParticipant getByDealIdOptionId(long dealId, long optionId) throws HibernateException {
        DealParticipant result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_DEAL_ID_OPTION_ID);
        query.setLong("dealId", dealId);
        query.setLong("optionId", optionId);
        result = (DealParticipant)query.uniqueResult();
        tx.commit();

        return result;
    }

    public static int getNumParticipantsInDeal(long dealId) throws HibernateException {
       return getNumParticipantsInDeal(dealId, 0);
    }

    public static int getNumParticipantsInDeal(long dealId, long optionId) throws HibernateException {
        int numParticipants = 0;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        StringBuffer queryBuf = new StringBuffer("SELECT sum(os.quantity) from deal_participants dp, "
                + "order_summary os where dp.order_summary_id = os.order_summary_id and dp.deal_id = ");
        queryBuf.append(dealId);
        if(optionId > 0) {
            queryBuf.append(" and dp.pricing_option_id = ").append(optionId);
        }
        Query query = session.createSQLQuery(queryBuf.toString());
        BigDecimal result = (BigDecimal)query.uniqueResult();
        if(result != null)
            numParticipants = result.intValue();
        tx.commit();

        return numParticipants;
    }

    /**
     * @return Map from price to number of participants
     */
    public static Map<Double,Integer> getPriceToParticipantsMap(long dealId) {
        Map<Double,Integer> result = new HashMap<Double,Integer>();
        List<DealParticipant> participantList = DealParticipant.getByDealId(dealId);
        for(DealParticipant participant : participantList) {
            DealPricingOption dp = DealPricingOption.getById(participant.getPricingOptionId());
            OrderSummary os = OrderSummary.getById(participant.getOrderSummaryId());
            Integer numParticipants = result.get(dp.getPrice());
            if(numParticipants == null) {
                result.put(dp.getPrice(), os.getQuantity());
            } else{
                result.put(dp.getPrice(), new Integer(numParticipants.intValue() + os.getQuantity()));
            }
        }

        return result;
    }

    public static int getCumulativeParticipants(long dealId, long optionId){
        return Deal.getCumulativeParticipantsMap(dealId).get(optionId);
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DealParticipant other = (DealParticipant) obj;
        if (this.dealParticipantId != other.dealParticipantId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (int) (this.dealParticipantId ^ (this.dealParticipantId >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "com.dao.DealParticipant[dealParticipantId=" + dealParticipantId + "]";
    }

}
