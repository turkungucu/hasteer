/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.lang.StringUtils;

import com.struts.form.ProductDetailsForm;
import com.dao.Product;
import com.dao.ProductImage;
import com.dao.Deal;
import com.dao.DealPricingOption;
import com.dao.DealParticipant;
import com.dao.RateCardRate;
import com.dao.MerchantPolicy;
import com.constants.HasteerConstants;
import com.util.AuthUtil;
import com.util.StringUtil;
import com.util.DateUtils;

import java.util.List;
/**
 *
 * @author Alinur Goksel
 */
public class ProductDetailsAction extends Action {
    
    private static final String SUCCESS = "success";
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // saves data in the session
        saveReferrerDataAndSource(request);

        ProductDetailsForm productDetailsForm = (ProductDetailsForm)form;
        long dealId = productDetailsForm.getDealId();
        Deal deal = Deal.getById(dealId);
        Product product = Product.getById(deal.getProductId());
        List<ProductImage> productImages = product.getImages();
        for(ProductImage pi : productImages) {
            pi.resizeImage(HasteerConstants.MAX_WIDTH_FOR_HOT_DEALS, HasteerConstants.MAX_HEIGHT_FOR_HOT_DEALS);
        }
        
        MerchantPolicy shippingPolicy = MerchantPolicy.getById(deal.getShippingPolicyId());
        MerchantPolicy returnPolicy = MerchantPolicy.getById(deal.getReturnPolicyId());

        productDetailsForm.setImages(productImages);
        productDetailsForm.setProduct(product);
        productDetailsForm.setDealPricingOptions(DealPricingOption.getByDealIdOrderAsc(dealId));
        productDetailsForm.setNumParticipants(DealParticipant.getNumParticipantsInDeal(dealId));
        productDetailsForm.setRewardPoints(RateCardRate.getRewardPoints(deal.getSellerId(), dealId, product.getCategoryId()));
        productDetailsForm.setIsRunning(deal.isRunning());
        productDetailsForm.setShippingPolicy(new String(shippingPolicy.getContent()));
        productDetailsForm.setReturnPolicy(new String(returnPolicy.getContent()));
        productDetailsForm.setRetailPrice(StringUtil.getFormattedPrice(deal.getRetailPrice()));

        if (deal.isRunning()) {
            productDetailsForm.setEndDate(deal.getFormattedEndDate());
            productDetailsForm.setCounterDate(DateUtils.getTimeRemainingInCounterFormat(deal.getEndDate()));
        } else {
            productDetailsForm.setEndDate(deal.getFormattedProcessedDate());
        }
        
        return mapping.findForward(SUCCESS);
    }

    private void saveReferrerDataAndSource(HttpServletRequest request) {
        String fbRef = request.getParameter("fb_ref");
        String fbSource = request.getParameter("fb_source");

        String refId = request.getParameter("refId");
        String src = request.getParameter("src");

        // coming from FB
        if (StringUtils.isNotBlank(fbRef)) {
            setSessionAttrs(request, fbRef, fbSource);
        } else if (StringUtils.isNotBlank(refId)) {
            if (StringUtils.isBlank(src)) {
                src = "other";
            }
            setSessionAttrs(request, refId, src);
        }
    }

    private void setSessionAttrs(HttpServletRequest request, String encryptedRefString, String source) {
        HttpSession session = request.getSession(false);
        try {
            String decryptedRefString = AuthUtil.decodeString(encryptedRefString);
            String[] refData = StringUtils.split(decryptedRefString, "|");
            session.setAttribute("referrer_id", refData[0]);
            session.setAttribute("referred_deal_id", refData[1]);
            session.setAttribute("referrer_source", source);
        } catch (Exception e) {}
    }
}
