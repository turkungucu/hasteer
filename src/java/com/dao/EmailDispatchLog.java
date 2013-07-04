
package com.dao;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "email_dispatch_log")
@NamedQueries({
    @NamedQuery(name = "EmailDispatchLog.findAll", query = "SELECT e FROM EmailDispatchLog e"),
    @NamedQuery(name = "EmailDispatchLog.findByEmailDispatchLogId", query = "SELECT e FROM EmailDispatchLog e WHERE e.emailDispatchLogId = :emailDispatchLogId"),
    @NamedQuery(name = "EmailDispatchLog.findByEmailAddress", query = "SELECT e FROM EmailDispatchLog e WHERE e.emailAddress = :emailAddress"),
    @NamedQuery(name = "EmailDispatchLog.findByEmailTypeKey", query = "SELECT e FROM EmailDispatchLog e WHERE e.emailTypeKey = :emailTypeKey"),
    @NamedQuery(name = "EmailDispatchLog.findByDateDispatched", query = "SELECT e FROM EmailDispatchLog e WHERE e.dateDispatched = :dateDispatched")})
public class EmailDispatchLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "email_dispatch_log_id")
    private Long emailDispatchLogId;
    @Basic(optional = false)
    @Column(name = "email_address")
    private String emailAddress;
    @Basic(optional = false)
    @Column(name = "email_type_key")
    private String emailTypeKey;
    @Basic(optional = false)
    @Column(name = "date_dispatched")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDispatched;

    public EmailDispatchLog() {
    }

    public EmailDispatchLog(String emailAddress, String emailTypeKey, Date dateDispatched) {
        this.emailAddress = emailAddress;
        this.emailTypeKey = emailTypeKey;
        this.dateDispatched = dateDispatched;
    }

    public Long getEmailDispatchLogId() {
        return emailDispatchLogId;
    }

    public void setEmailDispatchLogId(Long emailDispatchLogId) {
        this.emailDispatchLogId = emailDispatchLogId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailTypeKey() {
        return emailTypeKey;
    }

    public void setEmailTypeKey(String emailTypeKey) {
        this.emailTypeKey = emailTypeKey;
    }

    public Date getDateDispatched() {
        return dateDispatched;
    }

    public void setDateDispatched(Date dateDispatched) {
        this.dateDispatched = dateDispatched;
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
        hash += (emailDispatchLogId != null ? emailDispatchLogId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmailDispatchLog)) {
            return false;
        }
        EmailDispatchLog other = (EmailDispatchLog) object;
        if ((this.emailDispatchLogId == null && other.emailDispatchLogId != null) || (this.emailDispatchLogId != null && !this.emailDispatchLogId.equals(other.emailDispatchLogId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dao.EmailDispatchLog[emailDispatchLogId=" + emailDispatchLogId + "]";
    }

}
