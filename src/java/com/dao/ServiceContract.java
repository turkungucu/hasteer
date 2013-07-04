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
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

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
@Table(name = "service_contracts")
public class ServiceContract implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String TERMS_OF_SERVICE = "terms-of-service";
    public static final String REWARDS_TERMS_OF_SERVICE = "rewards-terms-of-service";
    public static final String TERMS_OF_USE = "terms-of-use";
    public static final String TERMS_OF_SALE = "terms-of-sale";
    public static final String PRIVACY_POLICY = "privacy-policy";

    private static final String HQL_GET_MOST_RECENT_BY_TYPE = "SELECT s FROM ServiceContract s WHERE s.type = :type ORDER BY s.revisionDate desc";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "contract_id")
    private Long contractId;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @Lob
    @Column(name = "contents")
    private byte[] contents;
    @Basic(optional = false)
    @Column(name = "revision_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date revisionDate;

    public ServiceContract() {
    }

    public ServiceContract(Long contractId) {
        this.contractId = contractId;
    }

    public ServiceContract(Long contractId, String type, byte[] contents, Date revisionDate) {
        this.contractId = contractId;
        this.type = type;
        this.contents = contents;
        this.revisionDate = revisionDate;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public static ServiceContract getMostRecentContractByType(String type)
            throws HibernateException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_MOST_RECENT_BY_TYPE);
        query.setString("type", type);
        ServiceContract contract = (ServiceContract) query.uniqueResult();
        tx.commit();

        return contract;
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
        hash += (contractId != null ? contractId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServiceContract)) {
            return false;
        }
        ServiceContract other = (ServiceContract) object;
        if ((this.contractId == null && other.contractId != null) || (this.contractId != null && !this.contractId.equals(other.contractId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dao.ServiceContract[contractId=" + contractId + "]";
    }

}
