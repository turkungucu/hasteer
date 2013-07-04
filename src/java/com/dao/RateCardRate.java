
package com.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Alinur Goksel
 */
@Entity
@Table(name = "rate_card_rates")

public class RateCardRate implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String HQL_GET_RATE_BY_DEAL_ID =
            "SELECT rcr FROM RateCard rc, RateCardSubscriber rcs, RateCardRate rcr where "
            + "rc.rateCardId = rcs.rateCardId and rcs.rateCardId = rcr.rateCardId and "
            + "rc.rateType = :rateType and rcs.dealId = :dealId and "
            + "rcs.effectiveDate <= now() and (rcs.endDate is null or rcs.endDate > now())";

    private static final String HQL_GET_RATE_BY_SELLER_AND_CAT_ID =
            "SELECT rcr FROM RateCard rc, RateCardSubscriber rcs, RateCardRate rcr where "
            + "rc.rateCardId = rcs.rateCardId and rcs.rateCardId = rcr.rateCardId and "
            + "rc.rateType = :rateType and rcs.sellerId = :sellerId and rcr.categoryId = :categoryId and "
            + "rcs.effectiveDate <= now() and (rcs.endDate is null or rcs.endDate > now())";

    private static final String HQL_GET_RATE_BY_SELLER_ID =
            "SELECT rcr FROM RateCard rc, RateCardSubscriber rcs, RateCardRate rcr where "
            + "rc.rateCardId = rcs.rateCardId and rcs.rateCardId = rcr.rateCardId and "
            + "rc.rateType = :rateType and rcs.sellerId = :sellerId and rcr.categoryId is null and "
            + "rcs.effectiveDate <= now() and (rcs.endDate is null or rcs.endDate > now())";

    private static final String HQL_GET_RATE_BY_CAT_ID =
            "SELECT rcr FROM RateCard rc, RateCardSubscriber rcs, RateCardRate rcr where "
            + "rc.rateCardId = rcs.rateCardId and rcs.rateCardId = rcr.rateCardId and "
            + "rc.rateType = :rateType and rcs.sellerId is null and rcs.dealId is null and "
            + "rcr.categoryId = :categoryId and rcs.effectiveDate <= now() and "
            + "(rcs.endDate is null or rcs.endDate > now())";

    private static final String HQL_GET_DEFAULT_RATE =
            "SELECT rcr FROM RateCard rc, RateCardSubscriber rcs, RateCardRate rcr where "
            + "rc.rateCardId = rcs.rateCardId and rcs.rateCardId = rcr.rateCardId and "
            + "rc.rateType = :rateType and rcs.sellerId is null and rcs.dealId is null and "
            + "rcr.categoryId is null and rcs.effectiveDate <= now() and "
            + "(rcs.endDate is null or rcs.endDate > now())";

    @Id
    @Basic(optional = false)
    @Column(name = "rate_card_id")
    private long rateCardId;

    @Basic(optional = false)
    @Column(name = "rate")
    private int rate;
    
    @Column(name = "category_id")
    private Long categoryId;

    public RateCardRate() {
    }

    public RateCardRate(long rateCardId) {
        this.rateCardId = rateCardId;
    }

    public RateCardRate(long rateCardId, int rate) {
        this.rateCardId = rateCardId;
        this.rate = rate;
    }

    public long getRateCardId() {
        return rateCardId;
    }

    public void setRateCardId(long rateCardId) {
        this.rateCardId = rateCardId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Applies 5 step precedence system to get the billable
     * revshare percentage
     *
     * @param sellerId
     * @param dealId
     * @param categoryId
     * @return revshare percentage
     */
    public static int getBillableRevshare(long sellerId, long dealId, long categoryId)
            throws HibernateException {
        int rate = getRate(RateCard.RateType.REVSHARE.getValue(), sellerId, dealId, categoryId);
        return rate > 0 ? rate : RateCard.RateType.REVSHARE.getDefaultRate();
    }

    /**
     * Applies 5 step precedence system to get the reward points
     * per lead
     *
     * @param sellerId
     * @param dealId
     * @param categoryId
     * @return reward point(s) per lead
     */
    public static int getRewardPoints(long sellerId, long dealId, long categoryId) 
            throws HibernateException {
        int rate = getRate(RateCard.RateType.REWARD_POINTS.getValue(), sellerId, dealId, categoryId);
        return rate > 0 ? rate : RateCard.RateType.REWARD_POINTS.getDefaultRate();
    }

    public static int getRewardPointsExchangeRate() throws HibernateException {
        int rate = getRate(RateCard.RateType.REWARD_POINTS_EXCHANGE_RATE.getValue(), 0, 0, 0);
        return rate > 0 ? rate : RateCard.RateType.REWARD_POINTS_EXCHANGE_RATE.getDefaultRate();
    }

    private static int getRate(int rateType, long sellerId, long dealId, long categoryId)
            throws HibernateException {
        // 1.
        RateCardRate rcr = getRateByDealId(rateType, dealId);

        // 2.
        if (rcr == null) {
            rcr = getRateBySellerAndCategoryId(rateType, sellerId, categoryId);
        }

        // 3.
        if (rcr == null) {
            rcr = getRateBySellerId(rateType, sellerId);
        }

        // 4.
        if (rcr == null) {
            rcr = getRateByCategoryId(rateType, categoryId);
        }

        // 5.
        if (rcr == null) {
            rcr = getDefaultRate(rateType);
        }

        return rcr != null ? rcr.getRate() : -1;
    }

    private static RateCardRate getRateByDealId(int rateType, long dealId) throws HibernateException {
        RateCardRate rate = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_RATE_BY_DEAL_ID);
        query.setLong("rateType", rateType);
        query.setLong("dealId", dealId);
        rate = (RateCardRate)query.uniqueResult();
        tx.commit();

        return rate;
    }

    private static RateCardRate getRateBySellerAndCategoryId(int rateType, long sellerId, long categoryId)
            throws HibernateException {
        RateCardRate rate = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_RATE_BY_SELLER_AND_CAT_ID);
        query.setLong("rateType", rateType);
        query.setLong("sellerId", sellerId);
        query.setLong("categoryId", categoryId);
        rate = (RateCardRate)query.uniqueResult();
        tx.commit();

        return rate;
    }

    private static RateCardRate getRateBySellerId(int rateType, long sellerId)
            throws HibernateException {
        RateCardRate rate = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_RATE_BY_SELLER_ID);
        query.setLong("rateType", rateType);
        query.setLong("sellerId", sellerId);
        rate = (RateCardRate)query.uniqueResult();
        tx.commit();

        return rate;
    }

    private static RateCardRate getRateByCategoryId(int rateType, long categoryId)
            throws HibernateException {
        RateCardRate rate = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_RATE_BY_CAT_ID);
        query.setLong("rateType", rateType);
        query.setLong("categoryId", categoryId);
        rate = (RateCardRate)query.uniqueResult();
        tx.commit();

        return rate;
    }

    private static RateCardRate getDefaultRate(int rateType)
            throws HibernateException {
        RateCardRate rate = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_DEFAULT_RATE);
        query.setLong("rateType", rateType);
        rate = (RateCardRate)query.uniqueResult();
        tx.commit();

        return rate;
    }

    public void save() throws HibernateException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(this);
        tx.commit();
    }

    @Override
    public String toString() {
        return "com.dao.RateCardRate[rateCardId=" + rateCardId + "]";
    }

}
