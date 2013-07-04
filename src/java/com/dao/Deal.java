/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dao;

import com.util.HibernateUtil;
import com.api.DealItem;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ecolak
 */
@Entity
@Table(name = "deals")
public class Deal implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String HQL_GET_ALL = "SELECT d FROM Deal d order by d.endDate asc";
    private static final String HQL_GET_FEATURED = "SELECT d FROM Deal d where d.dealType = :dealType";
    private static final String HQL_GET_BY_DEAL_ID = "SELECT d FROM Deal d WHERE d.dealId = :dealId";
    private static final String HQL_GET_BY_PRODUCT_ID = "SELECT d FROM Deal d WHERE d.productId = :productId";
    private static final String HQL_GET_ACTIVE_DEALS = "select d from Deal d where d.startDate <= now() "
            + "and d.endDate >= now() and d.status = " + Status.OPEN.getValue();
    private static final String HQL_GET_EXPIRED_DEALS = "select d from Deal d where d.endDate < now()";
    private static final String HQL_GET_UPCOMING_DEALS = "select d from Deal d where d.startDate > now()";
    private static final String HQL_GET_DEALS_BY_CATEGORY = "SELECT d FROM Deal d, Product p " +
            "WHERE d.productId = p.productId and p.categoryId = :categoryId";
    private static final String HQL_GET_DEALS_BY_SELLER = "select d from Deal d where d.sellerId = :sellerId";
    private static final String HQL_GET_PAST_DEALS = "select d from Deal d where d.status = "
            + Status.PROCESSED.getValue();

    public static final double HASTEER_SCORE_HOURS_WEIGHT = 1.0;
    public static final double HASTEER_SCORE_PARTICIPANTS_WEIGHT = 1.0;
    public static final double HASTEER_SCORE_VALUE_WEIGHT = 0.1;

    public static SimpleDateFormat dateFormatter = new SimpleDateFormat ("MM/dd/yyyy hh:mm a");
    public static SimpleDateFormat dateFormatterTZ = new SimpleDateFormat ("MM/dd/yyyy hh:mm a, z");
    private static NumberFormat percFormatter = NumberFormat.getPercentInstance();

    public enum DealType {
        REGULAR(0),
        FEATURED(1);

        int mValue;
        DealType(int value) {
            mValue = value;
        }

        public int getValue() {
            return mValue;
        }
    }

    public enum Status {
        OPEN(0),
        PROCESSED(1);

        int mValue;
        Status(int value) {
            mValue = value;
        }
        public int getValue() {
            return mValue;
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "deal_id")
    private long dealId;

    @Basic(optional = false)
    @Column(name = "product_id")
    private long productId;

    @Basic(optional = false)
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Basic(optional = false)
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Basic(optional = false)
    @Column(name = "seller_id")
    private long sellerId;

    @Basic(optional = false)
    @Column(name = "status")
    private int status;

    @Basic(optional = false)
    @Column(name = "deal_type")
    private int dealType;

    @Basic(optional = false)
    @Column(name = "deal_name")
    private String dealName;

    @Basic(optional = false)
    @Column(name = "retail_price")
    private double retailPrice;

    @Basic(optional = false)
    @Column(name = "return_policy_id")
    private long returnPolicyId;

    @Basic(optional = false)
    @Column(name = "shipping_policy_id")
    private long shippingPolicyId;

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

    @Column(name = "processed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedDate;

    public Deal() {
    }

    public Deal(long dealId) {
        this.dealId = dealId;
    }

    public Deal(long productId, Date startDate, Date endDate, long sellerId,
            int status, int dealType, String dealName, double retailPrice,
            long returnPolicyId, long shippingPolicyId, Date createDate,
            String createdBy, Date modifiedDate, String modifiedBy, Date processedDate) {
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sellerId = sellerId;
        this.status = status;
        this.dealType = dealType;
        this.dealName = dealName;
        this.retailPrice = retailPrice;
        this.returnPolicyId = returnPolicyId;
        this.shippingPolicyId = shippingPolicyId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.modifiedDate = modifiedDate;
        this.modifiedBy = modifiedBy;
        this.processedDate = processedDate;
    }

    public long getDealId() {
        return dealId;
    }

    public void setDealId(long dealId) {
        this.dealId = dealId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getFormattedEndDate() {
        return dateFormatterTZ.format(endDate);
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDealType() {
        return dealType;
    }

    public void setDealType(int dealType) {
        this.dealType = dealType;
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

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
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

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public long getReturnPolicyId() {
        return returnPolicyId;
    }

    public void setReturnPolicyId(long returnPolicyId) {
        this.returnPolicyId = returnPolicyId;
    }

    public long getShippingPolicyId() {
        return shippingPolicyId;
    }

    public void setShippingPolicyId(long shippingPolicyId) {
        this.shippingPolicyId = shippingPolicyId;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    public String getFormattedProcessedDate() {
        return dateFormatterTZ.format(processedDate);
    }
    
    public String getStatusMessage() {
        if (isUpcoming()) {
            return "Pending";
        } else if (isRunning()) {
            return "Active";
        } else if (hasReachedMaxCapacity()) {
            return "Completed";
        } else {
            return "Expired";
        }
    }

    public boolean hasReachedMaxCapacity(){
        boolean result = false;
        int numParticipants = DealParticipant.getNumParticipantsInDeal(dealId);
        int maxParticipants = Deal.getMaxAllowedParticipants(dealId);
        if(numParticipants >= maxParticipants) {
            result = true;
        }
        return result;
    }

    public double getClosingPrice(){
        int currentNumParticipants = DealParticipant.getNumParticipantsInDeal(dealId);
        Map<Double,Integer> priceToParticipantsMap = DealParticipant.getPriceToParticipantsMap(dealId);
        double initialPrice = DealPricingOption.getPriceForNumParticipants(dealId, currentNumParticipants);

        return getClosingPriceBody(initialPrice, priceToParticipantsMap);
    }

    private double getClosingPriceBody(double initialPrice, Map<Double,Integer> priceToParticipantsMap) {
        if(everyoneCanPayPrice(initialPrice, priceToParticipantsMap))
            return initialPrice;

        Map<Double,Integer> newMap = new HashMap<Double,Integer>();
        int remainingNumParticipants = 0;
        for(double price : priceToParticipantsMap.keySet()){
            int numParticipants = priceToParticipantsMap.get(price);
            if(price >= initialPrice) {
                remainingNumParticipants += numParticipants;
                newMap.put(price, numParticipants);
            }
        }

        double newPrice = DealPricingOption.getPriceForNumParticipants(dealId, remainingNumParticipants);
        return getClosingPriceBody(newPrice, newMap);
    }

    private boolean everyoneCanPayPrice(double price, Map<Double,Integer> priceToParticipantsMap) {
        boolean result = true;
        for(double priceInMap : priceToParticipantsMap.keySet()){
            if(priceInMap < price){
                result = false;
                break;
            }
        }
        return result;
    }

    public double getHasteerScore(){
        int remainingHours = com.util.DateUtils.hoursDiff(endDate, new Date());
        DealPricingOption po = DealPricingOption.getLowestPriceOption(dealId);
        int pplDiff = po.getNumOfParticipantsRemaining();
        double totalValue = po.getPrice() * po.getMinNumParticipants() / 1000;
        return Math.log(Math.pow(remainingHours+1, 2)) * Math.log(Math.pow(pplDiff+1, 2)) + (1/totalValue);
    }

    public boolean isUpcoming() {
       return System.currentTimeMillis() < startDate.getTime();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > endDate.getTime();
    }

    public boolean isRunning() {
        return (!this.isExpired() && !this.isUpcoming() 
                && status != Deal.Status.PROCESSED.getValue()
                && !this.hasReachedMaxCapacity());
    }

    /**
     * @return Map of deal pricing option id to number of participants in that pricing option
     */

    public static Map<Long,Integer> getCumulativeParticipantsMap(long dealId){
        Map<Long,Integer> result = new HashMap<Long,Integer>();
        List<DealPricingOption> options = DealPricingOption.getByDealIdOrderAsc(dealId);
        int cumulativeTotal = 0;
        for(DealPricingOption op : options) {
            int count = DealParticipant.getNumParticipantsInDeal(dealId, op.getOptionId());
            cumulativeTotal += count;
            result.put(op.getOptionId(), cumulativeTotal);
        }
        return result;
    }

    public static List<Deal> getAll() throws HibernateException {
        List<Deal> deals = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_ALL);
        deals = query.list();
        tx.commit();
        return deals;
    }

    private static List<DealItem> getAsDealItems(List<Deal> deals) {
        return getAsDealItems(deals, false);
    }

    private static List<DealItem> getAsDealItems(List<Deal> deals, boolean processedDeal) {
        List<DealItem> dealItems = new ArrayList<DealItem>();

        for (Deal deal : deals) {
            long dealId = deal.getDealId();

            int currentParticipants = 0;
            int requiredParticipants = 0;
            double price = 0;
            String endDate = null;

            // get closing pricing option
            if (processedDeal) {
                currentParticipants = DealParticipant.getNumParticipantsInDeal(dealId);
                price = DealPricingOption.getPriceForNumParticipants(dealId, currentParticipants);
                endDate = deal.getFormattedProcessedDate();
            }
            // get the lowest pricing option
            else {
                DealPricingOption pricingOption = DealPricingOption.getLowestPriceOption(deal.getDealId());
                currentParticipants = pricingOption.getCumulativeParticipants();
                requiredParticipants = pricingOption.getMinNumParticipants();
                price = pricingOption.getPrice();
                endDate = deal.getFormattedEndDate();
            }

            double originalPrice = deal.getRetailPrice() <= 0.0 ?
                DealPricingOption.getPriceForNumParticipants(dealId, 1) : deal.getRetailPrice();
            String percentSavings = originalPrice > 0 ? percFormatter.format(1 - price/originalPrice) : "";
     
            Product product = Product.getById(deal.getProductId());

            if(product == null)
                continue;

            String productName = product.getProductName();
            if (productName.length() >= 40) {
                productName = StringUtils.overlay(productName, "...", 40, productName.length());
            }

            ProductImage image = ProductImage.getPrimaryImageForProduct(product.getProductId());

            dealItems.add(new DealItem(dealId,
                                       productName,
                                       product.getSoldBy(),
                                       endDate,
                                       image.getResizedImageUrl(),
                                       image.getThumbnailUrl(),
                                       percentSavings,
                                       com.util.DateUtils.daysDiff(deal.getEndDate(), new Date()),
                                       requiredParticipants,
                                       currentParticipants,
                                       price,
                                       originalPrice,
                                       deal.getEndDate()));
        }

        return dealItems;
    }

    public static List<Deal> getDealsSortedByHasteerScore() {
        return getDealsSortedByHasteerScore(true);
    }

    public static List<Deal> getDealsSortedByHasteerScore(boolean excludeInactive) {
        List<Deal> deals = excludeInactive ? getActiveDeals() : getAll();
        if(deals.size() > 1) {
            Collections.sort(deals, new Comparator<Deal>(){
                // Greater the Hasteer score, the less important the deal
                // If score is 0 then deal is NOT important
                public int compare(Deal d1, Deal d2) {
                    double score1 = d1.getHasteerScore();
                    double score2 = d2.getHasteerScore();
                    if(score1 <= 0 && score2 <= 0)
                        return 0;
                    else if(score1 <= 0)
                        return -1;
                    else if(score2 <= 0)
                        return 1;
                    else
                        return score1 < score2 ? -1 : 1;
                }
            });
        }
        return deals;
    }

    public static List<DealItem> getHotDeals() throws HibernateException {
        List<Deal> deals = getDealsSortedByHasteerScore();
        
        if(deals.size() <= 5)
            return getAsDealItems(deals);
        else
           return getAsDealItems(deals.subList(0, 5));
    }

    public static List<DealItem> getRegularDeals() throws HibernateException {
        List<Deal> deals = getDealsSortedByHasteerScore(); //getActiveDeals(true);
        int size = deals.size();
        if(size > 0) {
            // Ensures that there are no rows with less than 5 deals on the homepage.
            //int displaySize = size - (size % 5);
            //return getAsDealItems(deals.subList(0, displaySize));
            return getAsDealItems(deals.subList(0, size));
        } else {
            return new ArrayList<DealItem>();
        }
    }

    public static List<DealItem> getFeaturedDeals() throws HibernateException {
        List<Deal> deals = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_FEATURED);
        query.setInteger("dealType", DealType.FEATURED.getValue());
        deals = query.list();
        tx.commit();
        return getAsDealItems(deals);
    }

    public static List<DealItem> getPastDeals() throws HibernateException {
        List<Deal> deals = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_PAST_DEALS);
        deals = query.list();
        tx.commit();
        return getAsDealItems(deals, true);
    }

    public static Deal getById(long dealId) throws HibernateException {
        Deal deal = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_DEAL_ID);
        query.setLong("dealId", dealId);
        deal = (Deal)query.uniqueResult();
        tx.commit();

        return deal;
    }

    public static Deal getByProductId(long productId) throws HibernateException {
        Deal deal = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_BY_PRODUCT_ID);
        query.setLong("productId", productId);
        deal = (Deal)query.uniqueResult();
        tx.commit();

        return deal;
    }

    public static List<Deal> getBySellerId(long sellerId) throws HibernateException {
        List<Deal> deals = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_DEALS_BY_SELLER);
        query.setLong("sellerId", sellerId);
        deals = query.list();
        tx.commit();

        return deals;
    }

    public static List<Deal> getActiveDeals() throws HibernateException {
        return getActiveDeals(false);
    }

    public static List<Deal> getActiveDeals(boolean orderByEndDateAsc) throws HibernateException {
        List<Deal> deals = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        String sql = HQL_GET_ACTIVE_DEALS;
        if(orderByEndDateAsc)
            sql += " order by d.endDate asc";
        Query query = session.createQuery(sql);
        deals = query.list();
        tx.commit();
        for(Iterator<Deal> dealIter = deals.iterator(); dealIter.hasNext(); ) {
            Deal d = dealIter.next();
            if(d.hasReachedMaxCapacity())
                dealIter.remove();
        }
        return deals;
    }

    public static List<Deal> getExpiredDeals() throws HibernateException {
        List<Deal> deals = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_EXPIRED_DEALS);
        deals = query.list();
        tx.commit();
        return deals;
    }

    public static List<Deal> getUpcomingDeals() throws HibernateException {
        List<Deal> deals = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_UPCOMING_DEALS);
        deals = query.list();
        tx.commit();
        return deals;
    }

    public static int getMaxAllowedParticipants(long dealId) throws HibernateException {
        int numParticipants = 0;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("select max(min_num_participants) from deal_pricing_options where deal_id = ")
                .append(dealId);
        Query query = session.createSQLQuery(queryBuf.toString());
        Integer result = (Integer)query.uniqueResult();
        numParticipants = result.intValue();
        tx.commit();

        return numParticipants;
    }

    public static Map<Deal, List<DealPricingOption>> getDealToPricingOptionsMap(){
        Map<Deal, List<DealPricingOption>> result = new HashMap<Deal, List<DealPricingOption>>();
        List<Deal> allDeals = Deal.getAll();
        if(allDeals != null) {
            for(Deal deal : allDeals){
                List<DealPricingOption> prOptions = DealPricingOption.getByDealId(deal.getDealId());
                if(prOptions != null)
                    result.put(deal, prOptions);
            }
        }
        return result;
    }

    public static Map<Deal, List<DealPricingOption>> getDealToPricingOptionsMap(List<Deal> dealList){
        Map<Deal, List<DealPricingOption>> result = new HashMap<Deal, List<DealPricingOption>>();
        if(dealList != null) {
            for(Deal deal : dealList){
                List<DealPricingOption> prOptions = DealPricingOption.getByDealId(deal.getDealId());
                if(prOptions != null)
                    result.put(deal, prOptions);
            }
        }
        return result;
    }

    public static List<Deal> getDealsByCategory(long categoryId) throws HibernateException {
        List<Deal> result = null;
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(HQL_GET_DEALS_BY_CATEGORY);
        query.setLong("categoryId", categoryId);
        result = query.list();
        tx.commit();

        return result;
    }

    public static List<Deal> searchForDeals(String[] searchTerms) {
        List<Deal> deals = null;
        StringBuilder searchParam = new StringBuilder("%");
        for(String s : searchTerms) {
            searchParam.append(s).append("%");
        }
        String sql = "SELECT DISTINCT d FROM Deal d, Product p WHERE d.productId = p.productId and " +
                "(p.productName like :sparam or p.details like :sparam)";

        /*for(int i = 0; i < searchTerms.length; i++) {
            sql += "p.productName like :term" + i + " or ";
        }
        for(int i = 0; i < searchTerms.length; i++) {
            sql += "p.details like :term" + i + (i+1 == searchTerms.length ? ")" : " or ");
        }*/
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(sql);
        query.setString("sparam", searchParam.toString());
        
        /*for(int k = 0; k < searchTerms.length; k++) {
            query.setString("term" + k, '%' + searchTerms[k] + '%');
        }*/

        deals = query.list();
        for(Iterator<Deal> it = deals.iterator(); it.hasNext(); ) {
            Deal d = it.next();
            if(!d.isRunning())
                it.remove();
        }
        tx.commit();
        return deals;
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
        final Deal other = (Deal) obj;
        if (this.dealId != other.dealId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + (int) (this.dealId ^ (this.dealId >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "com.dao.Deal[dealId=" + dealId + "]";
    }

}
