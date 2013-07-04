/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dao;

import com.util.HibernateUtil;
import com.util.CreditCardUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Transient;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ecolak, agoksel
 */
@Entity
@Table(name = "credit_card_details")

public class CreditCardDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String HQL_GET_BY_USER_ID = "SELECT c FROM CreditCardDetail c " +
            "WHERE c.isDeleted = 0 and c.userId = :userId";
    private static final String HQL_GET_MOST_RECENT_BY_USER_ID = "SELECT c FROM CreditCardDetail c " +
            "WHERE c.isDeleted = 0 and c.isMostRecent = 1 and c.userId = :userId";
    private static final String HQL_GET_BY_ID = "SELECT c FROM CreditCardDetail c " +
            "WHERE c.creditCardDetailsId = :creditCardDetailsId";

    private static SimpleDateFormat expiryDateFormatter = new SimpleDateFormat ("MM/yyyy");

    public enum CreditCardType {
        VISA ("Visa", "Visa", "XXXX-XXXX-XXXX-"),
        MASTER ("Master", "MasterCard", "XXXX-XXXX-XXXX-"),
        AMEX ("Amex", "American Express", "XXXX-XXXXXX-X"),
        DISCOVER ("Discover", "Discover", "XXXX-XXXX-XXXX-");

        String _value;
        String _label;
        String _mask;

        private CreditCardType(String _value, String _label, String _mask) {
            this._value = _value;
            this._label = _label;
            this._mask = _mask;
        }

        public String getLabel() {
            return _label;
        }

        public String getValue() {
            return _value;
        }

        public String getMask() {
            return _mask;
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "credit_card_details_id")
    private Long creditCardDetailsId;

    @Basic(optional = false)
    @Column(name = "user_id")
    private long userId;

    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;

    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;

    @Basic(optional = false)
    @Column(name = "address1")
    private String address1;

    @Basic(optional = false)
    @Column(name = "address2")
    private String address2;

    @Basic(optional = false)
    @Column(name = "city")
    private String city;

    @Basic(optional = false)
    @Column(name = "state")
    private String state;

    @Basic(optional = false)
    @Column(name = "zip_code")
    private String zipCode;

    @Basic(optional = false)
    @Column(name = "country")
    private String country;

    @Basic(optional = false)
    @Column(name = "cardholder_name")
    private String cardholderName;

    @Basic(optional = false)
    @Column(name = "card_type")
    private String cardType;

    @Basic(optional = false)
    @Column(name = "last_four")
    private String lastFour;

    @Basic(optional = false)
    @Column(name = "expiry_date")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;

    @Basic(optional = true)
    @Column(name = "vault_token")
    private String vaultToken;

    @Basic(optional = false)
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Basic(optional = false)
    @Column(name = "is_most_recent")
    private boolean isMostRecent;

    @Transient // not to be persisted in DB
    private String creditCardNum;

    @Transient
    private String cvv;

    @Transient
    private boolean doNotSaveToDb;

    public CreditCardDetail() {
    }

    public CreditCardDetail(Long creditCardDetailsId) {
        this.creditCardDetailsId = creditCardDetailsId;
    }

    public CreditCardDetail(long userId, String lastName, String address1,
            String address2, String city, String state, String zipCode,
            String country, String cardholderName, String cardType,
            String lastFour, Date expiryDate, String vaultToken) {
        this.userId = userId;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.cardholderName = cardholderName;
        this.cardType = cardType;
        this.lastFour = lastFour;
        this.expiryDate = expiryDate;
        this.vaultToken = vaultToken;
    }

    public Long getCreditCardDetailsId() {
        return creditCardDetailsId;
    }

    public void setCreditCardDetailsId(Long creditCardDetailsId) {
        this.creditCardDetailsId = creditCardDetailsId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }
    
    public String getCardType() {
        if (CreditCardType.AMEX.getValue().equals(cardType)) {
            return CreditCardType.AMEX.getLabel();
        } else if (CreditCardType.DISCOVER.getValue().equals(cardType)) {
            return CreditCardType.DISCOVER.getLabel();
        } else if (CreditCardType.MASTER.getValue().equals(cardType)) {
            return CreditCardType.MASTER.getLabel();
        } else if (CreditCardType.VISA.getValue().equals(cardType)) {
            return CreditCardType.VISA.getLabel();
        } else {
            return "unknown";
        }
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public String getFormattedExpiryDate() {
        return expiryDateFormatter.format(expiryDate);
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastFour() {
        return lastFour;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getVaultToken() {
        return vaultToken;
    }

    public void setVaultToken(String vaultToken) {
        this.vaultToken = vaultToken;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isIsMostRecent() {
        return isMostRecent;
    }

    public void setIsMostRecent(boolean isMostRecent) {
        this.isMostRecent = isMostRecent;
    }

    public String getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(String creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public boolean isDoNotSaveToDb() {
        return doNotSaveToDb;
    }

    public void setDoNotSaveToDb(boolean doNotSaveToDb) {
        this.doNotSaveToDb = doNotSaveToDb;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryDate.getTime();
    }

    public String getFullAddress() {
        return "<b>" + firstName + " " + lastName + "</b><br/>" +
                address1 + " " + address2 + "<br/>" +
                city + " " + state + ", " + zipCode + "<br/>" +
                country;
    }

    // decodes and masks last four by card type
    public String getMaskedCardNumber() {
        return CreditCardUtil.maskCCNumberByType(lastFour, cardType);
    }

    public String getCardTypeLabel() {
        if (CreditCardType.AMEX._value.equals(cardType)) {
            return CreditCardType.AMEX._label;
        } else if (CreditCardType.DISCOVER._value.equals(cardType)) {
            return CreditCardType.DISCOVER._label;
        } else if (CreditCardType.MASTER._value.equals(cardType)) {
            return CreditCardType.MASTER._label;
        } else if (CreditCardType.VISA._value.equals(cardType)) {
            return CreditCardType.VISA._label;
        } else {
            return null;
        }
    }

    public static List<CreditCardDetail> getByUserId(long userId) throws HibernateException {
        List<CreditCardDetail> result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_USER_ID);
        query.setLong("userId", userId);
        result = query.list();
        tx.commit();

        return result;
    }

    public static CreditCardDetail getMostRecentByUserId(long userId) throws HibernateException {
        CreditCardDetail result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_MOST_RECENT_BY_USER_ID);
        query.setLong("userId", userId);
        result = (CreditCardDetail)query.uniqueResult();
        tx.commit();

        return result;
    }

    public static CreditCardDetail getById(long id) throws HibernateException {
        CreditCardDetail result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_ID);
        query.setLong("creditCardDetailsId", id);
        result = (CreditCardDetail)query.uniqueResult();
        tx.commit();

        return result;
    }

    public void save() throws HibernateException {
        if(isDoNotSaveToDb())
            setIsDeleted(true);

        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(this);
        tx.commit();
    }

    public void delete() throws HibernateException {
        setIsDeleted(true);
        save();
    }

}
