
package com.dao;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Alinur Goksel
 */
@Entity
@Table(name = "rate_card_subscribers")

public class RateCardSubscriber implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "rate_card_id")
    private long rateCardId;

    @Column(name = "seller_id")
    private long sellerId;

    @Column(name = "deal_id")
    private long dealId;

    @Column(name = "effective_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;
    
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    public RateCardSubscriber() {
    }

    public RateCardSubscriber(long rateCardId) {
        this.rateCardId = rateCardId;
    }

    public long getRateCardId() {
        return rateCardId;
    }

    public void setRateCardId(long rateCardId) {
        this.rateCardId = rateCardId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getDealId() {
        return dealId;
    }

    public void setDealId(long dealId) {
        this.dealId = dealId;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "com.dao.RateCardSubscriber[rateCardId=" + rateCardId + "]";
    }

}
