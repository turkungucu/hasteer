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
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ecolak
 */
@Entity
@Table(name = "products")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String HQL_GET_ALL = "SELECT p FROM Product p";
    private static final String HQL_GET_BY_PRODUCT_ID = "SELECT p FROM Product p WHERE p.productId = :productId";
    private static final String HQL_GET_BY_SELLER_ID = "SELECT p FROM Product p WHERE p.sellerId = :sellerId";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "product_id")
    private long productId;

    @Basic(optional = false)
    @Column(name = "product_name")
    private String productName;

    @Basic(optional = false)
    @Lob
    @Column(name = "details")
    private String details;
    
    @Basic(optional = false)
    @Column(name = "category_id")
    private int categoryId;

    @Basic(optional = false)
    @Column(name = "seller_id")
    private long sellerId;

    @Basic(optional = false)
    @Column(name = "weight")
    private double weight;

    @Basic(optional = false)
    @Column(name = "length_")
    private double length;

    @Basic(optional = false)
    @Column(name = "height")
    private double height;

    @Basic(optional = false)
    @Column(name = "width")
    private double width;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    public Product() {
    }

    public Product(long productId) {
        this.productId = productId;
    }

    public Product(long productId, String productName, String details, 
            int categoryId, long sellerId, double weight,
            double length, double height, double width) {
        this.productId = productId;
        this.productName = productName;
        this.details = details;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.weight = weight;
        this.length = length;
        this.height = height;
        this.width = width;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getSoldBy() {
        User user = User.getUserById(sellerId);
        return user.getUsername();
    }

    public List<ProductImage> getImages(){
        return ProductImage.getByProductId(productId);
    }

    public ProductImage getPrimaryImage(){
        return ProductImage.getPrimaryImageForProduct(productId);
    }

    public String getCategoryName() {
        ProductCategory prodCat = ProductCategory.getById(categoryId);
        if (prodCat != null) {
            String catName = prodCat.getCategoryName();
            return catName != null ? catName : "No Category Assigned";
        } else {
            return "Invalid Category";
        }
    }

    public double getGirth() {
        return 2 * (width + height);
    }

    public static List<Product> getAll() throws HibernateException {
        List<Product> result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_ALL);
        result = query.list();
        tx.commit();
        return result;
    }

    public static Product getById(long productId) throws HibernateException {
        Product result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_PRODUCT_ID);
        query.setLong("productId", productId);
        result = (Product)query.uniqueResult();
        tx.commit();

        return result;
    }

    public static List<Product> getBySellerId(long sellerId) throws HibernateException {
        List<Product> result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_SELLER_ID);
        query.setLong("sellerId", sellerId);
        result = query.list();
        tx.commit();
        return result;
    }

    public void save() throws HibernateException {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(this);
        tx.commit();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (this.productId ^ (this.productId >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dao.Product[productId=" + productId + "]";
    }
}