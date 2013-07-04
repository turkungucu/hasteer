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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ecolak
 */
@Entity
@Table(name = "users")

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String HQL_GET_ALL = "SELECT u FROM User u";
    public static final String HQL_GET_BY_USER_ID = "SELECT u FROM User u WHERE u.userId = :userId";
    public static final String HQL_GET_BY_FB_USER_ID = "SELECT u FROM User u WHERE u.fbUserId = :fbUserId";
    public static final String HQL_GET_BY_EMAIL = "SELECT u FROM User u WHERE u.email = :email";
    public static final String SQL_GET_BY_USERNAME_PASSWORD =
            "from User where username = :username and password = :password";

    public static final String SQL_GET_BY_USERNAME = "from User u WHERE username = :username";

    public enum UserStatus {
        INACTIVE (0),
        ACTIVE (1);

        int _value;
        UserStatus(int value){
            _value = value;
        }

        public int getValue(){
            return _value;
        }
    }

    public enum UserType {
        BUYER (0),
        SELLER (1),
        ADVERTISER (2);

        int _value;
        UserType(int value){
            _value = value;
        }

        public int getValue(){
            return _value;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private long userId;

    @Basic(optional = true)
    @Column(name = "fb_user_id")
    private long fbUserId;

    @Basic(optional = false)
    @Column(name = "username")
    private String username;

    @Basic(optional = false)
    @Column(name = "email")
    private String email;

    @Basic(optional = false)
    @Column(name = "password")   
    private String password;

    @Basic(optional = false)
    @Column(name = "type")
    private int type;

    @Basic(optional = true)
    @Column(name = "first_name")
    private String firstName;

    @Basic(optional = true)
    @Column(name = "last_name")
    private String lastName;

    @Basic(optional = false)
    @Column(name = "registration_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationTime;
    
    @Basic(optional = false)
    @Column(name = "status")
    private int status;

    @Basic(optional = false)
    @Column(name = "email_verified")
    private boolean emailVerified;

    public User() {
    }

    public User(Long userId) {
        this.userId = userId;
    }

    public User(long userId, String username, String email, String password, int type, String firstName,
                String lastName, Date registrationTime, int status, boolean emailVerified)
    {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationTime = registrationTime;
        this.status = status;
        this.emailVerified = emailVerified;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFbUserId() {
        return fbUserId;
    }

    public void setFbUserId(long fbUserId) {
        this.fbUserId = fbUserId;
    }
    
    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public static List<User> getAll() throws HibernateException {
        List<User> users = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_ALL);
        users = query.list();
        tx.commit();
        return users;
    }

    public static User getUserById(long userId) throws HibernateException {
        User user = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_USER_ID);
        query.setLong("userId", userId);
        user = (User)query.uniqueResult();
        tx.commit();

        return user;
    }

    public static User getUserByFbId(long fbUserId) throws HibernateException {
        User user = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_FB_USER_ID);
        query.setLong("fbUserId", fbUserId);
        user = (User)query.uniqueResult();
        tx.commit();

        return user;
    }

    public static User getUserByUsername(String username) throws HibernateException {
        User user = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        //Query query = session.getNamedQuery("User.findByUsername");
        Query query = session.createQuery(SQL_GET_BY_USERNAME);
        query.setString("username", username);
        user = (User)query.uniqueResult();
        tx.commit();

        return user;
    }

    public static User getUserByEmail(String email) throws HibernateException {
        User user = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_EMAIL);
        query.setString("email", email);
        user = (User)query.uniqueResult();
        tx.commit();
        
        return user;
    }

    public static User getUser(String username, String password) throws HibernateException {
        User user = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(SQL_GET_BY_USERNAME_PASSWORD);
        query.setString("username", username);
        query.setString("password", password);
        user = (User)query.uniqueResult();
        tx.commit();
        
        return user;
    }

    public static User getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        return user;
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
        final User other = (User) obj;
        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.userId ^ (this.userId >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "com.dao.User[userId=" + userId + "]";
    }

}
