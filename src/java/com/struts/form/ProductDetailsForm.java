package com.struts.form;

import org.apache.struts.action.ActionForm;
import com.dao.Product;
import com.dao.ProductImage;
import com.dao.DealPricingOption;

import java.util.List;

/**
 *
 * @author Alinur Goksel
 */
public class ProductDetailsForm extends ActionForm {
    private long dealId;
    private int numParticipants;
    private int rewardPoints;
    private boolean isRunning;
    private String endDate;
    private Product product;
    private List<ProductImage> images;
    private List<DealPricingOption> dealPricingOptions;
    private String shippingPolicy;
    private String returnPolicy;
    private String retailPrice;
    private String counterDate;

    public long getDealId() {
        return dealId;
    }

    public void setDealId(long dealId) {
        this.dealId = dealId;
    }

    public List<DealPricingOption> getDealPricingOptions() {
        return dealPricingOptions;
    }

    public void setDealPricingOptions(List<DealPricingOption> dealPricingOptions) {
        this.dealPricingOptions = dealPricingOptions;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getNumParticipants() {
        return numParticipants;
    }

    public void setNumParticipants(int numParticipants) {
        this.numParticipants = numParticipants;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> image) {
        this.images = image;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public String getReturnPolicy() {
        return returnPolicy;
    }

    public void setReturnPolicy(String returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    public String getShippingPolicy() {
        return shippingPolicy;
    }

    public void setShippingPolicy(String shippingPolicy) {
        this.shippingPolicy = shippingPolicy;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getCounterDate() {
        return counterDate;
    }

    public void setCounterDate(String counterDate) {
        this.counterDate = counterDate;
    }

}
