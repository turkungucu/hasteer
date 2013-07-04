/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.form;

import org.apache.struts.validator.ValidatorForm;
import com.dao.Deal;
import com.dao.DealPricingOption;
import com.dao.Product;
import com.dao.ProductImage;
import com.dao.RewardPointsBalance;

import java.util.List;

/**
 *
 * @author ecolak
 */
public class JoinDealForm extends ValidatorForm {
    private long dealId;
    private String cmd;
    private Deal deal;
    private Product product;
    private ProductImage productImage;
    private List<DealPricingOption> pricingOptions;
    private long prOptionId;
    private boolean userAlreadyInDeal;
    private int quantity = 1;
    private int redeemedAmount;
    private RewardPointsBalance rewardPointsBalance;
    private boolean quickJoinEnabled;

    public long getDealId(){
        return dealId;
    }

    public void setDealId(long dealId){
        this.dealId = dealId;
    }

    public String getCmd(){
        return cmd;
    }

    public void setCmd(String cmd){
        this.cmd = cmd;
    }

    public Deal getDeal(){
        return deal;
    }

    public void setDeal(Deal deal){
        this.deal = deal;
    }

    public List<DealPricingOption> getPricingOptions(){
        return pricingOptions;
    }

    public void setPricingOptions(List<DealPricingOption> pricingOptions){
        this.pricingOptions = pricingOptions;
    }

    public long getPrOptionId() {
        return prOptionId;
    }

    public void setPrOptionId(long prOptionId) {
        this.prOptionId = prOptionId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }

    public boolean isUserAlreadyInDeal() {
        return userAlreadyInDeal;
    }

    public void setUserAlreadyInDeal(boolean userAlreadyInDeal) {
        this.userAlreadyInDeal = userAlreadyInDeal;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRedeemedAmount() {
        return redeemedAmount;
    }

    public void setRedeemedAmount(int redeemedAmount) {
        this.redeemedAmount = redeemedAmount;
    }

    public RewardPointsBalance getRewardPointsBalance() {
        return rewardPointsBalance;
    }

    public void setRewardPointsBalance(RewardPointsBalance rewardPointsBalance) {
        this.rewardPointsBalance = rewardPointsBalance;
    }

    public boolean isQuickJoinEnabled() {
        return quickJoinEnabled;
    }

    public void setQuickJoinEnabled(boolean quickJoinEnabled) {
        this.quickJoinEnabled = quickJoinEnabled;
    }

}
