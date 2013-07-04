
package com.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Alinur Goksel
 */
@Entity
@Table(name = "rate_cards")

public class RateCard implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rate_card_id")
    private long rateCardId;

    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    @Basic(optional = false)
    @Column(name = "rate_type")
    private short rateType;

    public enum RateType {
        REVSHARE(0, 1),
        REWARD_POINTS(1, 1),
        REWARD_POINTS_EXCHANGE_RATE(2, 1);

        int mValue;
        int mDefaultRate;
        RateType(int value, int defaultRate) {
            mValue = value;
            mDefaultRate = defaultRate;
        }

        public int getValue() {
            return mValue;
        }

        public int getDefaultRate() {
            return mDefaultRate;
        }
    }

    public RateCard() {
    }

    public RateCard(long rateCardId) {
        this.rateCardId = rateCardId;
    }

    public RateCard(long rateCardId, String description, short rateType) {
        this.rateCardId = rateCardId;
        this.description = description;
        this.rateType = rateType;
    }

    public long getRateCardId() {
        return rateCardId;
    }

    public void setRateCardId(long rateCardId) {
        this.rateCardId = rateCardId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getRateType() {
        return rateType;
    }

    public void setRateType(short rateType) {
        this.rateType = rateType;
    }

    @Override
    public String toString() {
        return "com.dao.RateCard[rateCardId=" + rateCardId + "]";
    }

}
