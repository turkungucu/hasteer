/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dao;

import com.util.HibernateUtil;
import com.util.StringUtil;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
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
@Table(name = "deal_pricing_options")

public class DealPricingOption implements Serializable {

    private static final long serialVersionUID = 1L;

    private static String HQL_GET_ALL = "SELECT d FROM DealPricingOption d";

    private static String HQL_GET_BY_OPTION_ID = "SELECT d FROM DealPricingOption d WHERE d.optionId = :optionId";

    private static String HQL_GET_PRICING_OPTIONS_BY_DEAL_ID =
            "SELECT d FROM DealPricingOption d WHERE d.dealId = :dealId";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "option_id")
    private long optionId;

    @Basic(optional = false)
    @Column(name = "deal_id")
    private long dealId;

    @Basic(optional = false)
    @Column(name = "min_num_participants")
    private int minNumParticipants;
    
    @Basic(optional = false)
    @Column(name = "price")
    private double price;

    public DealPricingOption() {
    }

    public DealPricingOption(long optionId) {
        this.optionId = optionId;
    }

    public DealPricingOption(long dealId, int minNumParticipants, double price) {
        this.dealId = dealId;
        this.minNumParticipants = minNumParticipants;
        this.price = price;
    }

    public DealPricingOption(long optionId, long dealId, int minNumParticipants, double price) {
        this.optionId = optionId;
        this.dealId = dealId;
        this.minNumParticipants = minNumParticipants;
        this.price = price;
    }

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public long getDealId() {
        return dealId;
    }

    public void setDealId(long dealId) {
        this.dealId = dealId;
    }

    public int getMinNumParticipants() {
        return minNumParticipants;
    }

    public void setMinNumParticipants(int minNumParticipants) {
        this.minNumParticipants = minNumParticipants;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCumulativeParticipants(){
        return Deal.getCumulativeParticipantsMap(dealId).get(optionId);
    }

    public int getNumOfParticipantsRemaining() {
        return minNumParticipants - getCumulativeParticipants();
    }

    public static List<DealPricingOption> getAll() throws HibernateException {
        List<DealPricingOption> deals = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_ALL);
        deals = query.list();
        tx.commit();
        return deals;
    }

    public static DealPricingOption getById(long optionId) throws HibernateException {
        DealPricingOption option = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_OPTION_ID);
        query.setLong("optionId", optionId);
        option = (DealPricingOption)query.uniqueResult();
        tx.commit();

        return option;
    }

    public static List<DealPricingOption> getByDealId(long dealId) throws HibernateException {
        List<DealPricingOption> options = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_PRICING_OPTIONS_BY_DEAL_ID);
        query.setLong("dealId", dealId);
        options = query.list();
        tx.commit();

        return options;
    }

    public static List<DealPricingOption> getByDealIdOrderDesc(long dealId) throws HibernateException {
        List<DealPricingOption> options = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        StringBuffer sqlBuf = new StringBuffer(HQL_GET_PRICING_OPTIONS_BY_DEAL_ID);
        sqlBuf.append(" order by minNumParticipants desc");

        Query query = session.createQuery(sqlBuf.toString());
        query.setLong("dealId", dealId);
        options = query.list();
        tx.commit();

        return options;
    }

    public static List<DealPricingOption> getByDealIdOrderAsc(long dealId) throws HibernateException {
        List<DealPricingOption> options = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        StringBuffer sqlBuf = new StringBuffer(HQL_GET_PRICING_OPTIONS_BY_DEAL_ID);
        sqlBuf.append(" order by minNumParticipants asc");

        Query query = session.createQuery(sqlBuf.toString());
        query.setLong("dealId", dealId);
        options = query.list();
        tx.commit();

        return options;
    }

    public static DealPricingOption getLowestPriceOption(long dealId) throws HibernateException {
        DealPricingOption result = null;
        List<DealPricingOption> options = getByDealId(dealId);
        if(options != null && options.size() > 0) {
            result = Collections.min(options, new Comparator<DealPricingOption>(){
                public int compare(DealPricingOption o1, DealPricingOption o2) {
                    double price1 = o1 != null ? o1.getPrice() : 0;
                    double price2 = o2 != null ? o2.getPrice() : 0;
                    return (price1 < price2 ? -1 : (price1 > price2 ? 1 : 0));
                }
            });
        }
        return result;
    }

    public static double getPriceForNumParticipants(long dealId, int numParticipants){
        double result = 0;
        List<DealPricingOption> prOptions = DealPricingOption.getByDealIdOrderDesc(dealId);
        for(DealPricingOption option : prOptions) {
            if(numParticipants >= option.getMinNumParticipants()) {
                result = option.getPrice();
                break;
            }
        }
        return result;
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

    public String getFormattedOptionPrice() {
        return StringUtil.getFormattedPrice(this.price);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DealPricingOption other = (DealPricingOption) obj;
        if (this.optionId != other.optionId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (int) (this.optionId ^ (this.optionId >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "com.dao.DealPricingOption[optionId=" + optionId + "]";
    }

}
