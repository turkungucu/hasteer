/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.struts.form;

import com.dao.Deal;
import com.dao.DealParticipant;
import com.dao.Product;
import java.util.List;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author ecolak
 */
public class MyDealsForm extends ValidatorForm {
    private List<Deal> deals;
    private List<MyDealsRow> myDealsRows;

    // This class represents a single row in Buyer's My Deals page
    public static class MyDealsRow {
        Deal deal;
        DealParticipant dealParticipant;
        Product product;

        public MyDealsRow(Deal deal, DealParticipant dealParticipant, Product product) {
            this.deal = deal;
            this.dealParticipant = dealParticipant;
            this.product = product;
        }

        public Deal getDeal() {
            return deal;
        }

        public void setDeal(Deal deal) {
            this.deal = deal;
        }

        public DealParticipant getDealParticipant() {
            return dealParticipant;
        }

        public void setDealParticipant(DealParticipant dealParticipant) {
            this.dealParticipant = dealParticipant;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

    public List<MyDealsRow> getMyDealsRows() {
        return myDealsRows;
    }

    public void setMyDealsRows(List<MyDealsRow> myDealsRows) {
        this.myDealsRows = myDealsRows;
    }
}
