
package com.dao;

import com.util.HibernateUtil;
import com.api.Jsp;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ecolak
 */
@Entity
@Table(name = "product_images")
public class ProductImage implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String HQL_GET_ALL = "SELECT p FROM ProductImage p";
    private static final String HQL_GET_BY_ID = "SELECT p FROM ProductImage p WHERE p.imageId = :imageId";
    private static final String HQL_GET_BY_PRODUCT_ID = "SELECT p FROM ProductImage p WHERE p.productId = :productId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "image_id")
    private long imageId;
    @Basic(optional = false)
    @Column(name = "product_id")
    private long productId;
    @Basic(optional = false)
    @Column(name = "filename")
    private String filename;
    @Basic(optional = false)
    @Column(name = "width")
    private int width;
    @Basic(optional = false)
    @Column(name = "height")
    private int height;
    @Basic(optional = false)
    @Column(name = "is_primary")
    private boolean isPrimary;

    @Transient
    private int resizedWidth;

    @Transient
    private int resizedHeight;

    public ProductImage() {
    }

    public ProductImage(long imageId) {
        this.imageId = imageId;
    }

    public ProductImage(long imageId, long productId, String filename, int width, int height, boolean isPrimary) {
        this.imageId = imageId;
        this.productId = productId;
        this.filename = filename;
        this.width = width;
        this.height = height;
        this.isPrimary = isPrimary;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public int getResizedHeight() {
        return resizedHeight;
    }

    public void setResizedHeight(int resizedHeight) {
        this.resizedHeight = resizedHeight;
    }

    public int getResizedWidth() {
        return resizedWidth;
    }

    public void setResizedWidth(int resizedWidth) {
        this.resizedWidth = resizedWidth;
    }

    public String getImageUrl(){
        return Jsp.getImageUrl(imageId);
    }

    public String getResizedImageUrl(){
        return Jsp.getResizedImageUrl(imageId);
    }

    public String getThumbnailUrl(){
        return Jsp.getThumbnailUrl(imageId);
    }

    public void resizeImage(int maxWidth, int maxHeight){
        double ratio = (double)width / (double)height;
        if(ratio < 1)
            resizeImageForConstantHeight(maxHeight, maxWidth);
        else
            resizeImageForConstantWidth(maxWidth, maxHeight);
    }

    public void resizeImageForConstantHeight(int height, int maxWidth) {
        double ratio = (double)height / (double)this.height;
        this.resizedHeight = height;
        this.resizedWidth = new Double(this.width * ratio).intValue();

        if(this.resizedWidth > maxWidth) {
            double maxRatio = (double)maxWidth / (double)this.resizedWidth;
            this.resizedWidth = maxWidth;
            this.resizedHeight = new Double(this.resizedHeight * maxRatio).intValue();
        }
    }

    public void resizeImageForConstantWidth(int width, int maxHeight) {
        double ratio = (double)width / (double)this.width;
        this.resizedWidth = width;
        this.resizedHeight = new Double(this.height * ratio).intValue();

        if(this.resizedHeight > maxHeight) {
            double maxRatio = (double)maxHeight / (double)this.resizedHeight;
            this.resizedHeight = maxHeight;
            this.resizedWidth = new Double(this.resizedWidth * maxRatio).intValue();
        }
    }

    public static ProductImage getById(long imageId) throws HibernateException {
        ProductImage result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_ID);
        query.setLong("imageId", imageId);
        result = (ProductImage)query.uniqueResult();
        tx.commit();

        return result;
    }

    public static List<ProductImage> getByProductId(long productId) throws HibernateException {
        List<ProductImage> result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_PRODUCT_ID);
        query.setLong("productId", productId);
        result = query.list();
        tx.commit();

        return result;
    }

    public static ProductImage getPrimaryImageForProduct(long productId) throws HibernateException {
        ProductImage result = null;
        List<ProductImage> images = getByProductId(productId);

        for(ProductImage image : images) {
            if(image.isPrimary())
                result = image;
        }
        return result;
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
        final ProductImage other = (ProductImage) obj;
        if (this.imageId != other.imageId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (this.imageId ^ (this.imageId >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "com.dao.ProductImage[imageId=" + imageId + "]";
    }

}
