/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dao;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Alinur Goksel
 */
@Entity
@Table(name = "contract_acceptance_log")
public class ContractAcceptanceLog implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "acceptance_id")
    private Long acceptanceId;
    @Basic(optional = false)
    @Column(name = "user_id")
    private long userId;
    @Basic(optional = false)
    @Column(name = "contract_id")
    private long contractId;
    @Basic(optional = false)
    @Column(name = "agreed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date agreedDate;
    @Basic(optional = false)
    @Column(name = "ip_address")
    private String ipAddress;

    public ContractAcceptanceLog() {
    }

    public ContractAcceptanceLog(Long acceptanceId) {
        this.acceptanceId = acceptanceId;
    }

    public ContractAcceptanceLog(Long acceptanceId, long userId, long contractId, Date agreedDate, String ipAddress) {
        this.acceptanceId = acceptanceId;
        this.userId = userId;
        this.contractId = contractId;
        this.agreedDate = agreedDate;
        this.ipAddress = ipAddress;
    }

    public ContractAcceptanceLog(long userId, long contractId, Date agreedDate, String ipAddress) {
        this.userId = userId;
        this.contractId = contractId;
        this.agreedDate = agreedDate;
        this.ipAddress = ipAddress;
    }

    public Long getAcceptanceId() {
        return acceptanceId;
    }

    public void setAcceptanceId(Long acceptanceId) {
        this.acceptanceId = acceptanceId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public Date getAgreedDate() {
        return agreedDate;
    }

    public void setAgreedDate(Date agreedDate) {
        this.agreedDate = agreedDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void save() throws HibernateException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(this);
        tx.commit();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acceptanceId != null ? acceptanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContractAcceptanceLog)) {
            return false;
        }
        ContractAcceptanceLog other = (ContractAcceptanceLog) object;
        if ((this.acceptanceId == null && other.acceptanceId != null) || (this.acceptanceId != null && !this.acceptanceId.equals(other.acceptanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dao.ContractAcceptanceLog[acceptanceId=" + acceptanceId + "]";
    }

}
