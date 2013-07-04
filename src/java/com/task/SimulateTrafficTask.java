/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.task;

import com.dao.CreditCardDetail;
import com.dao.Deal;
import com.dao.DealParticipant;
import com.dao.DealPricingOption;
import com.dao.OrderSummary;
import com.dao.ShippingAddress;
import com.dao.User;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Simulates random traffic to deals
 * @author ecolak
 */
public class SimulateTrafficTask extends AbstractTask {

    public SimulateTrafficTask() {
        super();
    }

    public SimulateTrafficTask(int delay, int period) {
        super(delay, period);
    }

    /**
     * Pick a random active deal
     * Insert a random user to that deal
     */
    @Override
    public void run() {
        System.out.println("Simulating traffic...");

        try {
            List<Deal> activeDeals = Deal.getActiveDeals();
            if(activeDeals == null || activeDeals.isEmpty()) {
                System.err.println("No active deals");
                return;
            }

            Random random = new Random();
            int randomDealIdx = random.nextInt(activeDeals.size());
            Deal rDeal = activeDeals.get(randomDealIdx);
            if(rDeal == null) {
                System.err.println("Deal at " + randomDealIdx + " is null");
                return;
            }

            System.out.println("Deal selected: " + rDeal.getDealId());

            List<DealPricingOption> prOptions = DealPricingOption.getByDealId(rDeal.getDealId());
            if(prOptions == null || prOptions.isEmpty()) {
                System.err.println("Deal has no pricing options");
                return;
            }

            int randomDpoIdx = random.nextInt(prOptions.size());
            DealPricingOption rDpo = prOptions.get(randomDpoIdx);
            if(rDpo == null) {
                System.err.println("Deal pricing option at " + randomDpoIdx + " is null");
                return;
            }

            System.out.println("Price selected: " + rDpo.getPrice());

            int randomUserIdx = random.nextInt(10) + 67;
            User rUser = User.getUserById(randomUserIdx);
            if(rUser == null) {
                System.err.println("User " + randomUserIdx + " does not exist");
                return;
            }

            System.out.println("User selected: " + rUser.getUserId());

            DealParticipant existing = DealParticipant.getByDealIdBuyerId(rDeal.getDealId(), rUser.getUserId());
            if(existing != null) {
                System.err.println("User " + rUser.getUserId() + " has already joined deal " + rDeal.getDealId());
                return;
            }

            List<CreditCardDetail> ccds = CreditCardDetail.getByUserId(rUser.getUserId());
            if(ccds == null || ccds.isEmpty()) {
                System.err.println("User has no credit cards");
                return;
            }

            CreditCardDetail rccd = ccds.get(0);

            List<ShippingAddress> addrs = ShippingAddress.getByUserId(rUser.getUserId());
            if(addrs == null || addrs.isEmpty()) {
                System.err.println("User has no shipping addresses");
                return;
            }
            ShippingAddress raddr = addrs.get(0);

            OrderSummary os = new OrderSummary();
            os.setCourier(OrderSummary.COURIER_USPS);
            os.setQuantity(1);
            os.setUnitPrice(rDpo.getPrice());
            os.setShippingMethod("ground");
            os.setShippingCost(6.99);
            os.setTax(os.getQuantity() * os.getUnitPrice() * 0.0925);
            os.save();

            DealParticipant dp = new DealParticipant();
            dp.setBuyerId(rUser.getUserId());
            dp.setCreditCardId(rccd.getCreditCardDetailsId());
            dp.setDealId(rDeal.getDealId());
            dp.setIp("127.0.0.1");
            dp.setJoinDate(new Date());
            dp.setOrderSummaryId(os.getOrderSummaryId());
            dp.setPricingOptionId(rDpo.getOptionId());
            dp.setShippingAddressId(raddr.getShippingAddressId());
            dp.save();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
