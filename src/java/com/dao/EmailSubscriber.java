
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
@Table(name = "email_subscribers")
public class EmailSubscriber implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String HQL_GET_BY_EMAIL_TYPE_KEY =
            "SELECT e FROM EmailSubscriber e WHERE e.emailTypeKey = :emailTypeKey";
    private static final String HQL_GET_BY_EMAIL_AND_TYPE_KEY =
            "SELECT e FROM EmailSubscriber e WHERE e.emailAddress = :emailAddress "
            + "and e.emailTypeKey = :emailTypeKey";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "email_subscriber_id")
    private Long emailSubscriberId;
    @Basic(optional = false)
    @Column(name = "email_address")
    private String emailAddress;
    @Basic(optional = false)
    @Column(name = "email_type_key")
    private String emailTypeKey;
    @Basic(optional = false)
    @Column(name = "date_subscribed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSubscribed;

    public EmailSubscriber() {
    }

    public EmailSubscriber(Long emailSubscriberId) {
        this.emailSubscriberId = emailSubscriberId;
    }

    public EmailSubscriber(Long emailSubscriberId, String emailAddress, String emailTypeKey, Date dateSubscribed) {
        this.emailSubscriberId = emailSubscriberId;
        this.emailAddress = emailAddress;
        this.emailTypeKey = emailTypeKey;
        this.dateSubscribed = dateSubscribed;
    }

    public EmailSubscriber(String emailAddress, String emailTypeKey, Date dateSubscribed) {
        this.emailAddress = emailAddress;
        this.emailTypeKey = emailTypeKey;
        this.dateSubscribed = dateSubscribed;
    }

    public Long getEmailSubscriberId() {
        return emailSubscriberId;
    }

    public void setEmailSubscriberId(Long emailSubscriberId) {
        this.emailSubscriberId = emailSubscriberId;
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

    public Date getDateSubscribed() {
        return dateSubscribed;
    }

    public void setDateSubscribed(Date dateSubscribed) {
        this.dateSubscribed = dateSubscribed;
    }

    public static List<EmailSubscriber> getByEmailTypeKey(String emailTypeKey)
            throws HibernateException {
        List<EmailSubscriber> subscribers = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_EMAIL_TYPE_KEY);
        query.setString("emailTypeKey", emailTypeKey);
        subscribers = query.list();
        tx.commit();

        return subscribers;
    }

    public static EmailSubscriber getByEmailAndTypeKey(String emailAddress, String emailTypeKey)
            throws HibernateException {
        EmailSubscriber subscriber = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_EMAIL_AND_TYPE_KEY);
        query.setString("emailAddress", emailAddress);
        query.setString("emailTypeKey", emailTypeKey);
        subscriber = (EmailSubscriber)query.uniqueResult();
        tx.commit();

        return subscriber;
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
    public int hashCode() {
        int hash = 0;
        hash += (emailSubscriberId != null ? emailSubscriberId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmailSubscriber)) {
            return false;
        }
        EmailSubscriber other = (EmailSubscriber) object;
        if ((this.emailSubscriberId == null && other.emailSubscriberId != null) || (this.emailSubscriberId != null && !this.emailSubscriberId.equals(other.emailSubscriberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dao.EmailSubscriber[emailSubscriberId=" + emailSubscriberId + "]";
    }

}
