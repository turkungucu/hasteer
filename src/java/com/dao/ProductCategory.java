/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dao;

import com.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
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

/**
 *
 * @author ecolak
 */
@Entity
@Table(name = "product_categories")
public class ProductCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    private static String HQL_GET_ALL = "SELECT p FROM ProductCategory p";
    private static String HQL_GET_BY_ID = "select p from ProductCategory p where p.categoryId = :categoryId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "category_id")
    private int categoryId;
    @Basic(optional = false)
    @Column(name = "parent_id")
    private int parentId;
    @Basic(optional = false)
    @Column(name = "category_name")
    private String categoryName;
    @Basic(optional = false)
    @Column(name = "is_leaf")
    private boolean isLeaf;

    public ProductCategory() {
    }

    public ProductCategory(Short categoryId) {
        this.categoryId = categoryId;
    }

    public ProductCategory(int categoryId, int parentId, String categoryName, boolean isLeaf) {
        this.categoryId = categoryId;
        this.parentId = parentId;
        this.categoryName = categoryName;
        this.isLeaf = isLeaf;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public static List<ProductCategory> getAll() throws HibernateException {
        List<ProductCategory> result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_ALL);
        result = query.list();
        tx.commit();
        return result;
    }

    public static ProductCategory getById(long categoryId) throws HibernateException {
        ProductCategory result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_ID);
        query.setLong("categoryId", categoryId);
        result = (ProductCategory)query.uniqueResult();
        tx.commit();

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductCategory other = (ProductCategory) obj;
        if (this.categoryId != other.categoryId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.categoryId;
        return hash;
    }

    @Override
    public String toString() {
        return "com.dao.ProductCategory[categoryId=" + categoryId + "]";
    }

}
