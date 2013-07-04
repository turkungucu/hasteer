package com.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.commons.lang.StringUtils;

import com.util.HibernateUtil;

import java.util.List;
import javax.persistence.Transient;

@Entity
@Table(name = "shipping_addresses")

public class ShippingAddress implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String HQL_GET_BY_USER_ID_AND_ADDR_ID = "SELECT s "
            + "FROM ShippingAddress s WHERE s.isDeleted = 0 AND "
            + "s.shippingAddressId = :shippingAddressId AND s.userId = :userId";
    private static final String HQL_GET_BY_USER_ID = "SELECT s FROM ShippingAddress s " +
            "WHERE s.isDeleted = 0 and s.userId = :userId";
    private static final String HQL_GET_BY_ID = "SELECT s FROM ShippingAddress s " +
            "WHERE s.shippingAddressId = :shippingAddressId";
    private static final String HQL_GET_MOST_RECENT_BY_USER_ID = "SELECT s FROM ShippingAddress s " +
            "WHERE s.isDeleted = 0 and s.isMostRecent = 1 and s.userId = :userId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "shipping_address_id")
    private Long shippingAddressId;

    @Basic(optional = false)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "company")
    private String company;

    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;

    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;

    @Basic(optional = false)
    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Basic(optional = false)
    @Column(name = "zip_code")
    private String zipCode;

    @Basic(optional = false)
    @Column(name = "city")
    private String city;

    @Basic(optional = false)
    @Column(name = "state_")
    private String state;

    @Basic(optional = false)
    @Column(name = "country")
    private String country;

    @Basic(optional = true)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Basic(optional = false)
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Basic(optional = false)
    @Column(name = "is_most_recent")
    private boolean isMostRecent;

    @Transient
    private boolean doNotSaveToDb;

    public ShippingAddress() {
    }

    public ShippingAddress(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public ShippingAddress(long userId, String company, String firstName, 
            String lastName, String address1, String address2, String zipCode,
            String city, String state, String country, String phoneNumber) {
        this.userId = userId;
        this.company = company;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.phoneNumber = phoneNumber;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public boolean isDoNotSaveToDb() {
        return doNotSaveToDb;
    }

    public void setDoNotSaveToDb(boolean doNotSaveToDb) {
        this.doNotSaveToDb = doNotSaveToDb;
    }

    public String getFullAddress() {
        return "<b>" + (StringUtils.isNotBlank(company) ? company + " / " : "") +
                firstName + " " + lastName + "</b><br/>" +
                address1 + " " + address2 + "<br/>" +
                city + " " + state + ", " + zipCode + "<br/>" +
                country;
    }

    public String getAddress() {
        return address1 + " " + address2 + ", " + city + ", " + state + ", " + zipCode + ", " + country;
    }

    public String getRecipient() {
        return "<b>" + (StringUtils.isNotBlank(company) ? company + "<br/>" : "") +
                firstName + " " + lastName + "</b>";
    }

    public static ShippingAddress getByUserIdAndAddressId(long userId, long shippingAddressId) throws HibernateException {
        ShippingAddress result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_USER_ID_AND_ADDR_ID);
        query.setLong("shippingAddressId", shippingAddressId);
        query.setLong("userId", userId);
        result = (ShippingAddress) query.uniqueResult();
        tx.commit();

        return result;
    }

    public static List<ShippingAddress> getByUserId(long userId) throws HibernateException {
        List<ShippingAddress> result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_USER_ID);
        query.setLong("userId", userId);
        result = query.list();
        tx.commit();

        return result;
    }

    public static ShippingAddress getMostRecentByUserId(long userId) throws HibernateException {
        ShippingAddress result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_MOST_RECENT_BY_USER_ID);
        query.setLong("userId", userId);
        result = (ShippingAddress)query.uniqueResult();
        tx.commit();

        return result;
    }

    public static ShippingAddress getById(long id) throws HibernateException {
        ShippingAddress result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_ID);
        query.setLong("shippingAddressId", id);
        result = (ShippingAddress)query.uniqueResult();
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