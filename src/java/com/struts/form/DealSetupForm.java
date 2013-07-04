/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.form;

import com.dao.Deal;
import com.dao.MerchantPolicy;
import com.dao.User;
import com.util.AuthUtil;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ecolak
 */
public class DealSetupForm extends ValidatorForm {
    private String cmd;
    private long dealId;
    private String dealName;
    private long productId;
    private String startDate;
    private String endDate;
    private String retailPrice;
    private String minNum1;
    private String price1;
    private String minNum2;
    private String price2;
    private String minNum3;
    private String price3;
    private String minNum4;
    private String price4;
    private String minNum5;
    private String price5;
    private long oid1;
    private long oid2;
    private long oid3;
    private long oid4;
    private long oid5;
    private long doid;

    private List<MerchantPolicy> returnPolicies;
    private List<MerchantPolicy> shippingPolicies;
    private long returnPolicyId;
    private long shippingPolicyId;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        User user = AuthUtil.getLoggedInUser(request);
        long userId = user.getUserId();
        returnPolicies = MerchantPolicy.getBySellerAndType(userId, MerchantPolicy.RETURN_POLICY);
        shippingPolicies = MerchantPolicy.getBySellerAndType(userId, MerchantPolicy.SHIPPING_POLICY);
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public long getDealId() {
        return dealId;
    }

    public void setDealId(long dealId) {
        this.dealId = dealId;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getMinNum1() {
        return minNum1;
    }

    public void setMinNum1(String minNum1) {
        this.minNum1 = minNum1;
    }

    public String getMinNum2() {
        return minNum2;
    }

    public void setMinNum2(String minNum2) {
        this.minNum2 = minNum2;
    }

    public String getMinNum3() {
        return minNum3;
    }

    public void setMinNum3(String minNum3) {
        this.minNum3 = minNum3;
    }

    public String getMinNum4() {
        return minNum4;
    }

    public void setMinNum4(String minNum4) {
        this.minNum4 = minNum4;
    }

    public String getMinNum5() {
        return minNum5;
    }

    public void setMinNum5(String minNum5) {
        this.minNum5 = minNum5;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public String getPrice3() {
        return price3;
    }

    public void setPrice3(String price3) {
        this.price3 = price3;
    }

    public String getPrice4() {
        return price4;
    }

    public void setPrice4(String price4) {
        this.price4 = price4;
    }

    public String getPrice5() {
        return price5;
    }

    public void setPrice5(String price5) {
        this.price5 = price5;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public long getOid1() {
        return oid1;
    }

    public void setOid1(long oid1) {
        this.oid1 = oid1;
    }

    public long getOid2() {
        return oid2;
    }

    public void setOid2(long oid2) {
        this.oid2 = oid2;
    }

    public long getOid3() {
        return oid3;
    }

    public void setOid3(long oid3) {
        this.oid3 = oid3;
    }

    public long getOid4() {
        return oid4;
    }

    public void setOid4(long oid4) {
        this.oid4 = oid4;
    }

    public long getOid5() {
        return oid5;
    }

    public void setOid5(long oid5) {
        this.oid5 = oid5;
    }

    public long getDoid() {
        return doid;
    }

    public void setDoid(long doid) {
        this.doid = doid;
    }

    public List<MerchantPolicy> getReturnPolicies() {
        return returnPolicies;
    }

    public void setReturnPolicies(List<MerchantPolicy> returnPolicies) {
        this.returnPolicies = returnPolicies;
    }

    public long getReturnPolicyId() {
        return returnPolicyId;
    }

    public void setReturnPolicyId(long returnPolicyId) {
        this.returnPolicyId = returnPolicyId;
    }

    public List<MerchantPolicy> getShippingPolicies() {
        return shippingPolicies;
    }

    public void setShippingPolicies(List<MerchantPolicy> shippingPolicies) {
        this.shippingPolicies = shippingPolicies;
    }

    public long getShippingPolicyId() {
        return shippingPolicyId;
    }

    public void setShippingPolicyId(long shippingPolicyId) {
        this.shippingPolicyId = shippingPolicyId;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if("create".equalsIgnoreCase(cmd) || "update".equalsIgnoreCase(cmd)) {
            errors = super.validate(mapping, request);
            if(errors.size() > 0)
                return errors;

            try {
                if(Deal.dateFormatter.parse(endDate).before(Deal.dateFormatter.parse(startDate))) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("shared.errors.invalid.dateRange"));
                    return errors;
                }
            } catch(ParseException e) {}

            List<String> minNumList = new ArrayList<String>();
            List<String> priceList = new ArrayList<String>();
            if(!StringUtils.isBlank(getMinNum1()) && !StringUtils.isBlank(getPrice1())) {
                minNumList.add(getMinNum1());
                priceList.add(getPrice1());
            }
            if(!StringUtils.isBlank(getMinNum2()) && !StringUtils.isBlank(getPrice2())) {
                if(minNumList.contains(getMinNum2())) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("DealSetupAction.errors.duplicateMinNum"));
                } else if(priceList.contains(getPrice2())) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("DealSetupAction.errors.duplicatePrice"));
                } else {
                    minNumList.add(getMinNum2());
                    priceList.add(getPrice2());
                }
            }

            if(errors.size() > 0)
                return errors;

            if(!StringUtils.isBlank(getMinNum3()) && !StringUtils.isBlank(getPrice3())) {
                if(minNumList.contains(getMinNum3())) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("DealSetupAction.errors.duplicateMinNum"));
                } else if(priceList.contains(getPrice3())) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("DealSetupAction.errors.duplicatePrice"));
                } else {
                    minNumList.add(getMinNum3());
                    priceList.add(getPrice3());
                }
            }

            if(errors.size() > 0)
                return errors;

            if(!StringUtils.isBlank(getMinNum4()) && !StringUtils.isBlank(getPrice4())) {
                if(minNumList.contains(getMinNum4())) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("DealSetupAction.errors.duplicateMinNum"));
                } else if(priceList.contains(getPrice4())) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("DealSetupAction.errors.duplicatePrice"));
                } else {
                    minNumList.add(getMinNum4());
                    priceList.add(getPrice4());
                }
            }

            if(errors.size() > 0)
                return errors;

            if(!StringUtils.isBlank(getMinNum5()) && !StringUtils.isBlank(getPrice5())) {
                if(minNumList.contains(getMinNum5())) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("DealSetupAction.errors.duplicateMinNum"));
                } else if(priceList.contains(getPrice5())) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("DealSetupAction.errors.duplicatePrice"));
                } else {
                    minNumList.add(getMinNum5());
                    priceList.add(getPrice5());
                }
            }

            try {
                Number rp = DecimalFormat.getInstance().parse(getRetailPrice());
                for (String price : priceList) {
                    if (rp.doubleValue() <= Double.valueOf(price)) {
                        errors.add(Globals.ERROR_KEY, new ActionMessage("DealSetupAction.errors.retailPrice"));
                        break;
                    }
                }
                // convert back to double to be able to save in db
                setRetailPrice(String.valueOf(rp.doubleValue()));
            } catch (ParseException e) {
                errors.add(Globals.ERROR_KEY, new ActionMessage("DealSetupAction.errors.invalidRetailPrice"));
            }
        }
        return errors;
    }
    
}
