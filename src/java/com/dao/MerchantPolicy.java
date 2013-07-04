/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = "merchant_policies")
public class MerchantPolicy implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String SHIPPING_POLICY = "shipping-policy";
    public static final String RETURN_POLICY = "return-policy";

        public static final String HQL_GET_BY_POLICY_ID =
            "SELECT m FROM MerchantPolicy m WHERE m.merchantPolicyId = :merchantPolicyId";

    public static final String HQL_GET_BY_SELLER_AND_TYPE =
            "SELECT m FROM MerchantPolicy m WHERE m.sellerId = :sellerId and m.policyType = :policyType";

    @Id
    @Basic(optional = false)
    @Column(name = "merchant_policy_id")
    private Long merchantPolicyId;
    @Basic(optional = false)
    @Column(name = "seller_id")
    private long sellerId;
    @Basic(optional = false)
    @Column(name = "policy_type")
    private String policyType;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Lob
    @Column(name = "content")
    private byte[] content;
    @Basic(optional = false)
    @Column(name = "date_added")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;
    @Basic(optional = false)
    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;

    public MerchantPolicy() {
    }

    public MerchantPolicy(Long merchantPolicyId) {
        this.merchantPolicyId = merchantPolicyId;
    }

    public MerchantPolicy(Long merchantPolicyId, long sellerId, String policyType, String description, byte[] content, Date dateAdded, Date dateModified) {
        this.merchantPolicyId = merchantPolicyId;
        this.sellerId = sellerId;
        this.policyType = policyType;
        this.description = description;
        this.content = content;
        this.dateAdded = dateAdded;
        this.dateModified = dateModified;
    }

    public Long getMerchantPolicyId() {
        return merchantPolicyId;
    }

    public void setMerchantPolicyId(Long merchantPolicyId) {
        this.merchantPolicyId = merchantPolicyId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public static MerchantPolicy getById(long merchantPolicyId)
            throws HibernateException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_POLICY_ID);
        query.setLong("merchantPolicyId", merchantPolicyId);
        MerchantPolicy policy = (MerchantPolicy) query.uniqueResult();
        tx.commit();

        return policy;
    }

    public static List<MerchantPolicy> getBySellerAndType(long sellerId, String policyType)
            throws HibernateException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_SELLER_AND_TYPE);
        query.setLong("sellerId", sellerId);
        query.setString("policyType", policyType);
        List<MerchantPolicy> policies = query.list();
        tx.commit();

        return policies;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (merchantPolicyId != null ? merchantPolicyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MerchantPolicy)) {
            return false;
        }
        MerchantPolicy other = (MerchantPolicy) object;
        if ((this.merchantPolicyId == null && other.merchantPolicyId != null) || (this.merchantPolicyId != null && !this.merchantPolicyId.equals(other.merchantPolicyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dao.MerchantPolicy[merchantPolicyId=" + merchantPolicyId + "]";
    }

}
