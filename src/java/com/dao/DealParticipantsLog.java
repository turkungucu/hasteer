/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dao;

import com.util.HibernateUtil;
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

/**
 *
 * @author ecolak
 */
@Entity
@Table(name = "deal_participants_log")
public class DealParticipantsLog implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String HQL_GET_ALL = "SELECT d FROM DealParticipantsLog d";

    private static String HQL_GET_BY_DEAL_ID = "SELECT d FROM DealParticipantsLog d WHERE d.dealId = :dealId";

    private static String HQL_GET_BY_BUYER_ID =
            "SELECT d FROM DealParticipantsLog d WHERE d.buyerId = :buyerId";

    public enum LogAction {
        JOIN (1),
        LEAVE (2);

        private int actionType;
        LogAction(int actionType){
            this.actionType = actionType;
        }

        public int getActionType(){
            return actionType;
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "log_id")
    private long logId;

    @Basic(optional = false)
    @Column(name = "deal_id")
    private long dealId;

    @Basic(optional = false)
    @Column(name = "option_id")
    private long optionId;

    @Basic(optional = false)
    @Column(name = "buyer_id")
    private long buyerId;

    @Basic(optional = false)
    @Column(name = "log_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;

    @Basic(optional = false)
    @Column(name = "action_")
    private int action;

    @Column(name = "reason")
    private String reason;

    @Column(name = "ip")
    private String ip;

    public DealParticipantsLog() {
    }

    public DealParticipantsLog(long logId) {
        this.logId = logId;
    }

    public DealParticipantsLog(long logId, long dealId, long optionId, long buyerId, Date logDate, int action) {
        this.logId = logId;
        this.dealId = dealId;
        this.optionId = optionId;
        this.buyerId = buyerId;
        this.logDate = logDate;
        this.action = action;
    }

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public long getDealId() {
        return dealId;
    }

    public void setDealId(long dealId) {
        this.dealId = dealId;
    }

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public static List<DealParticipantsLog> getAll() throws HibernateException {
        List<DealParticipantsLog> deals = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_ALL);
        deals = query.list();
        tx.commit();
        return deals;
    }

    public static DealParticipantsLog getByDealId(long dealId) throws HibernateException {
        DealParticipantsLog result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_DEAL_ID);
        query.setLong("dealId", dealId);
        result = (DealParticipantsLog)query.uniqueResult();
        tx.commit();

        return result;
    }

    public static DealParticipantsLog getByBuyerId(long buyerId) throws HibernateException {
        DealParticipantsLog result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_BUYER_ID);
        query.setLong("buyerId", buyerId);
        result = (DealParticipantsLog)query.uniqueResult();
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DealParticipantsLog other = (DealParticipantsLog) obj;
        if (this.logId != other.logId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (int) (this.logId ^ (this.logId >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "com.dao.DealParticipantsLog[logId=" + logId + "]";
    }

}
