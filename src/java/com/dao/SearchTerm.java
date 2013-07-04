
package com.dao;

import com.util.HibernateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "search_terms")

public class SearchTerm implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String HQL_GET_ALL_TERMS = "SELECT s.searchTerm FROM SearchTerm s";
    private static final String HQL_GET_TOP_TERMS = "SELECT s FROM SearchTerm s";
    private static final String HQL_GET_BY_DATE =
            "SELECT s FROM SearchTerm s where s.dateModified >= :dateModified";
    private static final String HQL_GET_BY_TERM =
            "SELECT s FROM SearchTerm s where lower(s.searchTerm) = lower(:searchTerm)";

    @Id
    @Basic(optional = false)
    @Column(name = "search_term_id")
    private long searchTermId;

    @Basic(optional = false)
    @Column(name = "search_term")
    private String searchTerm;

    @Basic(optional = false)
    @Column(name = "count")
    private int count;

    @Basic(optional = false)
    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;

    public SearchTerm() {
    }

    public SearchTerm(String searchTerm, int count) {
        this.searchTerm = searchTerm;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public long getSearchTermId() {
        return searchTermId;
    }

    public void setSearchTermId(long searchTermId) {
        this.searchTermId = searchTermId;
    }

    public static List<String> getAllTerms() throws HibernateException {
        List<String> terms = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_ALL_TERMS);
        terms = query.list();
        tx.commit();
        return terms;
    }

    public static List<SearchTerm> getTopTerms() throws HibernateException {
        List<SearchTerm> terms = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_TOP_TERMS);
        terms = query.list();
        tx.commit();
        return terms;
    }

    public static List<SearchTerm> getAllAfterDate(Date minDate) throws HibernateException {
        List<SearchTerm> terms = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_DATE);
        query.setDate("dateModified", minDate);
        terms = query.list();
        tx.commit();
        return terms;
    }

    public static SearchTerm getBySearchTerm(String searchTerm) throws HibernateException {
        SearchTerm term = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_TERM);
        query.setString("searchTerm", searchTerm);
        term = (SearchTerm) query.uniqueResult();
        tx.commit();
        return term;
    }

    public void save() throws HibernateException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        this.dateModified = new Date();
        session.saveOrUpdate(this);
        tx.commit();
    }
}
