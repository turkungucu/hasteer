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
import javax.persistence.Lob;
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
@Table(name = "braintree_errors")
public class BraintreeError implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String HQL_GET_ALL = "SELECT e FROM BraintreeError e";
    private static final String HQL_GET_BY_ID = "SELECT e FROM BraintreeError e WHERE e.errorId = :errorId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "error_id")
    private Long errorId;

    @Basic(optional = false)
    @Column(name = "deal_id")
    private long dealId;

    @Basic(optional = false)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "error_code")
    private String errorCode;

    @Lob
    @Column(name = "error_text")
    private String errorText;

    @Column(name = "transaction_status")
    private Integer transactionStatus;

    @Basic(optional = false)
    @Column(name = "error_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date errorTime;

    public BraintreeError() {
    }

    public BraintreeError(Long errorId) {
        this.errorId = errorId;
    }

    public BraintreeError(Long errorId, long dealId, long userId, Date errorTime) {
        this.errorId = errorId;
        this.dealId = dealId;
        this.userId = userId;
        this.errorTime = errorTime;
    }

    public Long getErrorId() {
        return errorId;
    }

    public void setErrorId(Long errorId) {
        this.errorId = errorId;
    }

    public long getDealId() {
        return dealId;
    }

    public void setDealId(long dealId) {
        this.dealId = dealId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public Integer getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(Integer transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Date getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

    public static List<BraintreeError> getAll() throws HibernateException {
        List<BraintreeError> result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_ALL);
        result = query.list();
        tx.commit();
        return result;
    }

    public static BraintreeError getById(long errorId) throws HibernateException {
        BraintreeError result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_ID);
        query.setLong("errorId", errorId);
        result = (BraintreeError)query.uniqueResult();
        tx.commit();

        return result;
    }

    public void save() throws HibernateException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(this);
        tx.commit();
    }

    public static void insertError(long dealId, long userId, String errorCode, String errorText, int transactionStatus) {
        BraintreeError error = new BraintreeError();
        error.setDealId(dealId);
        error.setUserId(userId);
        error.setErrorCode(errorCode);
        error.setErrorText(errorText);
        error.setErrorTime(new Date());
        error.setTransactionStatus(transactionStatus);
        error.save();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (errorId != null ? errorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BraintreeError)) {
            return false;
        }
        BraintreeError other = (BraintreeError) object;
        if ((this.errorId == null && other.errorId != null) || (this.errorId != null && !this.errorId.equals(other.errorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dao.BraintreeErrors[errorId=" + errorId + "]";
    }

}
