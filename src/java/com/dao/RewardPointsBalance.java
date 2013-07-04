
package com.dao;

import java.io.Serializable;
import java.util.Date;
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
 *
 * @author Alinur Goksel
 */
@Entity
@Table(name = "reward_points_balance")
public class RewardPointsBalance implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String HQL_GET_BY_USER_ID = "SELECT r FROM RewardPointsBalance r where r.userId = :userId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "reward_points_balance_id")
    private long rewardPointsBalanceId;

    @Basic(optional = false)
    @Column(name = "user_id")
    private long userId;

    @Basic(optional = false)
    @Column(name = "points")
    private int points;

    @Basic(optional = false)
    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;

    public RewardPointsBalance() {
    }

    public RewardPointsBalance(long rewardPointsBalanceId) {
        this.rewardPointsBalanceId = rewardPointsBalanceId;
    }

    public RewardPointsBalance(long rewardPointsBalanceId, long userId, int points, Date dateModified) {
        this.rewardPointsBalanceId = rewardPointsBalanceId;
        this.userId = userId;
        this.points = points;
        this.dateModified = dateModified;
    }

    public long getRewardPointsBalanceId() {
        return rewardPointsBalanceId;
    }

    public void setRewardPointsBalanceId(long rewardPointsBalanceId) {
        this.rewardPointsBalanceId = rewardPointsBalanceId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public int getPointsCashValue() {
        int exchangeRate = getRewardPointsExchangeRate();
        int redeemablePoints = getRedeemablePoints();
        if (redeemablePoints > 0) {
            return redeemablePoints / exchangeRate;
        } else {
            return 0;
        }
    }

    public int getRedeemablePoints() {
        int exchangeRate = getRewardPointsExchangeRate();
        if (points >= exchangeRate) {
            int remainder = points % exchangeRate;
            return points - remainder;
        } else {
            return 0;
        }
    }

    public static int getRewardPointsExchangeRate() {
        return RateCardRate.getRewardPointsExchangeRate();
    }

    public static RewardPointsBalance getByUserId(long userId) throws HibernateException {
        RewardPointsBalance result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_USER_ID);
        query.setLong("userId", userId);
        result = (RewardPointsBalance)query.uniqueResult();
        tx.commit();

        return result;
    }

    public void save() throws HibernateException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(this);
        tx.commit();
    }

    @Override
    public String toString() {
        return "com.dao.RewardPointsBalance[rewardPointsBalanceId=" + rewardPointsBalanceId + "]";
    }

}
