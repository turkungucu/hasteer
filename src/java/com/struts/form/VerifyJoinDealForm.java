
package com.struts.form;

import com.dao.OrderSummary;
import com.dao.Product;
import com.dao.CreditCardDetail;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author ecolak
 */
public class VerifyJoinDealForm extends ValidatorForm {
    private String cmd;
    private CreditCardDetail ccDetails;
    private Product product;
    private OrderSummary orderSummary;
    private String shippingDetails;
    private String contactPhone;
    private String contactEmail;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }

    public CreditCardDetail getCcDetails() {
        return ccDetails;
    }

    public void setCcDetails(CreditCardDetail ccDetails) {
        this.ccDetails = ccDetails;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(String shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderSummary getOrderSummary() {
        return orderSummary;
    }

    public void setOrderSummary(OrderSummary orderSummary) {
        this.orderSummary = orderSummary;
    }
   
}
