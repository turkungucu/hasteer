
package com.api;

import com.util.StringUtil;
import com.util.DateUtils;
import java.util.Date;

/**
 *
 * @author agoksel
 * Helper pojo that represents a deal on the home page.
 */
public class DealItem {

    long dealId;
    String productName;
    String soldBy;
    String endDate;
    String imgUrl;
    String thumbnailUrl;
    String percentSavings;
    int daysDiff;
    int requiredParticipants;
    int currentParticipants;
    double dPrice;
    double oPrice;
    Date endDateObj;

    public DealItem(long dealId, String productName, String soldBy,
            String endDate, String imgUrl, String thumbnailUrl, String percentSavings,
            int daysDiff, int requiredParticipants, int currentParticipants,
            double dealPrice, double originalPrice, Date endDateObj) {
        this.dealId = dealId;
        this.productName = productName;
        this.soldBy = soldBy;
        this.endDate = endDate;
        this.imgUrl = imgUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.percentSavings = percentSavings;
        this.daysDiff = daysDiff;
        this.requiredParticipants = requiredParticipants;
        this.currentParticipants = currentParticipants;
        this.dPrice = dealPrice;
        this.oPrice = originalPrice;
        this.endDateObj = endDateObj;
    }

    public int getDaysDiff() {
        return daysDiff;
    }

    public void setDaysDiff(int daysDiff) {
        this.daysDiff = daysDiff;
    }

    public long getDealId() {
        return dealId;
    }

    public void setDealId(long dealId) {
        this.dealId = dealId;
    }

    public String getDealPrice() {
        return StringUtil.getFormattedPrice(dPrice);
        //return dealPrice;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getRequiredParticipants() {
        return requiredParticipants;
    }

    public void setRequiredParticipants(int requiredParticipants) {
        this.requiredParticipants = requiredParticipants;
    }

    public String getOriginalPrice() {
        return StringUtil.getFormattedPrice(oPrice);
    }

    public String getPercentSavings() {
        return percentSavings;
    }

    public void setPercentSavings(String percentSavings) {
        this.percentSavings = percentSavings;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCurrentParticipants() {
        return currentParticipants;
    }

    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = currentParticipants;
    }

    public String getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }

    public double getdPrice() {
        return dPrice;
    }

    public void setdPrice(double dPrice) {
        this.dPrice = dPrice;
    }

    public double getoPrice() {
        return oPrice;
    }

    public void setoPrice(double oPrice) {
        this.oPrice = oPrice;
    }

    public String getSavings() {
        return StringUtil.getFormattedPrice(oPrice - dPrice);
    }

    public String getCounterDate() {
        return DateUtils.getTimeRemainingInCounterFormat(endDateObj);
    }

}
