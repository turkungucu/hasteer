/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dao;

import com.util.HibernateUtil;
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
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ecolak
 */
@Entity
@Table(name = "transactions_log")
public class TransactionsLog implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum TransactionType {
        WITHDRAW (0),
        REFUND (1);

        int mType;
        TransactionType(int type){
            mType = type;
        }

        public int getValue(){
            return mType;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transaction_id")
    private long transactionId;
    @Basic(optional = false)
    @Column(name = "user_id")
    private long userId;

    @Basic(optional = false)
    @Column(name = "transaction_type")
    private int transactionType;

    @Basic(optional = false)
    @Column(name = "amount")
    private double amount;

    @Basic(optional = false)
    @Column(name = "is_successful")
    private boolean isSuccessful;

    @Basic(optional = false)
    @Column(name = "transaction_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionTime;

    public TransactionsLog() {
    }

    public TransactionsLog(long transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionsLog(long transactionId, long userId, int transactionType, double amount, Date transactionTime) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionTime = transactionTime;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isSuccessful(){
        return isSuccessful;
    }

    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
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
        final TransactionsLog other = (TransactionsLog) obj;
        if (this.transactionId != other.transactionId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (int) (this.transactionId ^ (this.transactionId >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "com.dao.TransactionsLog[transactionId=" + transactionId + "]";
    }

}
