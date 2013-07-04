/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.action;

import com.dao.Deal;
import com.dao.User;
import com.dao.User.UserType;
import com.dao.DealParticipant;
import com.dao.DealPricingOption;
import com.dao.Product;
import com.struts.form.MyDealsForm;
import com.struts.form.MyDealsForm.MyDealsRow;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author ecolak
 */
public class MyDealsAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {

        MyDealsForm jForm = (MyDealsForm) form;
        ActionErrors errors = new ActionErrors();

        User user = User.getUserFromSession(request);
        if (user == null) {
            errors.add(Globals.ERROR_KEY, new ActionMessage("userDoesNotExist"));
            saveErrors(request, errors);
            return (mapping.findForward("failure"));
        }

        if (user.getType() == UserType.SELLER.getValue()) {
            List<Deal> deals = Deal.getBySellerId(user.getUserId());
            jForm.setDeals(deals);
        } else {
            List<MyDealsRow> myDealsRows = new ArrayList<MyDealsRow>();
            List<DealParticipant> dealParticipants = DealParticipant.getByBuyerId(user.getUserId());
            for (DealParticipant dealParticipant : dealParticipants) {
                Deal deal = Deal.getById(dealParticipant.getDealId());
                Product product = Product.getById(Deal.getById(dealParticipant.getDealId()).getProductId());
                myDealsRows.add(new MyDealsRow(deal, dealParticipant, product));
            }
            jForm.setMyDealsRows(myDealsRows);
        }

        return (mapping.findForward("success"));
    }
}
