
package com.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import com.util.HibernateUtil;

/**
 * This class represents a Reward Points activity. An activity consists of
 * addition (earning) or subtraction (redeeming) of points.
 * @param user_id           activity belongs to this user
 * @param deal_id           the deal in question
 * @param referred_to       to whom this deal was referred to (null if points are redeemed)
 * @param referral_source   means of referral (null if points are redeemed)
 * @param points            signed integer represents points being added or subtracted
 * @param date_added        the date when this activity took place
 * @author Alinur Goksel
 */
@Entity
@Table(name = "reward_points_log")
public class RewardPointsLog implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String HQL_GET_BY_USER_ID = "SELECT r FROM RewardPointsLog r where r.userId = :userId order by dateAdded desc";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "reward_points_log_id")
    private long rewardPointsLogId;
    @Basic(optional = false)
    @Column(name = "user_id")
    private long userId;
    @Basic(optional = false)
    @Column(name = "deal_id")
    private long dealId;
    @Column(name = "referred_to")
    private Long referredTo;
    @Column(name = "referral_source")
    private String referralSource;
    @Basic(optional = false)
    @Column(name = "points")
    private int points;
    @Basic(optional = false)
    @Column(name = "date_added")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;

    public RewardPointsLog() {
    }

    public RewardPointsLog(long rewardPointsLogId) {
        this.rewardPointsLogId = rewardPointsLogId;
    }

    public RewardPointsLog(long rewardPointsLogId, long userId, long dealId, int points, Date dateAdded) {
        this.rewardPointsLogId = rewardPointsLogId;
        this.userId = userId;
        this.dealId = dealId;
        this.points = points;
        this.dateAdded = dateAdded;
    }

    public long getRewardPointsLogId() {
        return rewardPointsLogId;
    }

    public void setRewardPointsLogId(long rewardPointsLogId) {
        this.rewardPointsLogId = rewardPointsLogId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getDealId() {
        return dealId;
    }

    public void setDealId(long dealId) {
        this.dealId = dealId;
    }

    public Long getReferredTo() {
        return referredTo;
    }

    public void setReferredTo(Long referredTo) {
        this.referredTo = referredTo;
    }

    public String getReferralSource() {
        return referralSource;
    }

    public void setReferralSource(String referralSource) {
        this.referralSource = referralSource;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public static List<RewardPointsLog> getByUserId(long userId) throws HibernateException {
        List<RewardPointsLog> result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_USER_ID);
        query.setLong("userId", userId);
        result = query.list();
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
        return "com.dao.RewardPointsLog[rewardPointsLogId=" + rewardPointsLogId + "]";
    }

}
