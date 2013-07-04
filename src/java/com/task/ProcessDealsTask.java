package com.task;

import com.api.notification.DealStatusNotification;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.ValidationError;
import com.constants.HasteerConstants;
import com.dao.Deal;
import com.dao.DealParticipant;
import com.dao.DealPricingOption;
import com.dao.OrderSummary;
import com.dao.Product;
import com.dao.TransactionsLog;
import com.dao.TransactionsLog.TransactionType;
import com.dao.User;
import com.util.CreditCardUtil;
import com.util.StringUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * Periodical task that handles billing for deals
 * that have expired or have reached maximum capacity
 * @author ecolak
 */
public class ProcessDealsTask extends AbstractTask {

    //private Log log = LogFactory.getLog(ProcessDealsTask.class);
   // public static final long period = ConfigUtil.getLong("Hasteer.ProcessDealsTask.period",
     //       HasteerConstants.DEFAULT_PROCESS_DEALS_TASK_PERIOD);
    //public static final long delay = ConfigUtil.getLong("Hasteer.ProcessDealsTask.delay",
      //      HasteerConstants.DEFAULT_PROCESS_DEALS_TASK_DELAY);

    public ProcessDealsTask() {
        super();
    }

    public ProcessDealsTask(int delay, int period) {
        super(delay, period);
    }

    @Override
    public void run() {
        System.out.println("Processing deals...");

        // process expired deals
        List<Deal> expiredDeals = Deal.getExpiredDeals();
        for (Deal deal : expiredDeals) {
            processExpiredDeal(deal);
        }

        // process deals that reached max capacity
        List<Deal> allDeals = Deal.getAll();
        for (Deal deal : allDeals) {
            if (deal.hasReachedMaxCapacity()) {
                System.out.println("deal " + deal.getDealId() + " has reached max capacity");
                processMaxCapacityDeal(deal);
            }
        }
    }

    // charge the lowest price to every participant
    public static void processMaxCapacityDeal(Deal deal) {
        if(deal == null)
            return;

        if(deal.getStatus() == Deal.Status.PROCESSED.getValue()) {
            System.out.println("Deal " + deal.getDealId() + " is processed");
            return;
        }

        DealPricingOption cheapestOption = DealPricingOption.getLowestPriceOption(deal.getDealId());
        if (cheapestOption != null) {
            List<DealParticipant> participants = DealParticipant.getByDealId(deal.getDealId());
            for (DealParticipant dp : participants) {
                OrderSummary os = OrderSummary.getById(dp.getOrderSummaryId());
                if(os != null) {
                    double totalCharge = calculateSettlementCharge(
                                os.getQuantity(), cheapestOption.getPrice(), 
                                os.getShippingCost(), os.getRedeemedAmount());
                    submitForSettlement(dp.getTransactionId(), 
                            new BigDecimal(StringUtil.getBTFormattedPrice(totalCharge)));
                    sendConfirmationEmail(true, deal, dp, os.getOrderSummaryId());
                }
            }
            deal.setStatus(Deal.Status.PROCESSED.getValue());
            deal.setProcessedDate(new Date());
            deal.save();
        }
    }

    /**
     * Deal has expired.
     * Charge the closing price to those people who agreed to pay the closing price.
     * Void the transactions for those people who did not agree
     */
    public static void processExpiredDeal(Deal deal) {
        if(deal == null)
            return; 

        if(deal.getStatus() == Deal.Status.PROCESSED.getValue()) {
            System.out.println("Deal " + deal.getDealId() + " is processed");
            return;
        }

        double closingPrice = deal.getClosingPrice();
        List<DealParticipant> participants = DealParticipant.getByDealId(deal.getDealId());
        for (DealParticipant dp : participants) {
            DealPricingOption option = DealPricingOption.getById(dp.getPricingOptionId());
            OrderSummary os = OrderSummary.getById(dp.getOrderSummaryId());
            if (closingPrice > 0 && option.getPrice() >= closingPrice) {
                if(os != null) {
                    double totalCharge = calculateSettlementCharge(
                                os.getQuantity(), closingPrice, 
                                os.getShippingCost(), os.getRedeemedAmount());
                    submitForSettlement(dp.getTransactionId(),
                            new BigDecimal(StringUtil.getBTFormattedPrice(totalCharge)));
                    sendConfirmationEmail(true, deal, dp, os.getOrderSummaryId());
                }
            } else {
                voidOneTransaction(dp.getTransactionId());
                sendConfirmationEmail(false, deal, dp, os.getOrderSummaryId());
            }
        }
        deal.setStatus(Deal.Status.PROCESSED.getValue());
        deal.setProcessedDate(new Date());
        deal.save();
    }

    public static void submitForSettlement(String transactionId, BigDecimal amount) {
        Result<Transaction> result = null;
        try {
            result = CreditCardUtil.submitTransactionForSettlement(transactionId, amount);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

        if(result == null)
            return;
        
        if (result.isSuccess()) {
            System.out.println("Transaction " + transactionId + " submitted for settlement");
        } else if (result.getTransaction() != null) {
            System.err.println("Error processing transaction:");
            System.err.println("Message: " + result.getMessage());
            Transaction transaction = result.getTransaction();
            Transaction.Status status = transaction.getStatus();
            System.err.println("Status: " + status);
            System.err.println("Code: " + transaction.getProcessorResponseCode());
            System.err.println("Text: " + transaction.getProcessorResponseText());

            if (Transaction.Status.GATEWAY_REJECTED.equals(status)) {
                System.err.println(transaction.getGatewayRejectionReason().toString());
            }
        } else {
            System.err.println("Message: " + result.getMessage());
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                System.err.println("Attribute: " + error.getAttribute());
                System.err.println("Code: " + error.getCode());
                System.err.println("Message: " + error.getMessage());
            }
        }
    }

    public static void voidOneTransaction(String transactionId) {
        Result<Transaction> result = null;
        try {
            result = CreditCardUtil.voidTransaction(transactionId);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

        if(result == null)
            return;

        if (result.isSuccess()) {
            System.out.println("Transaction " + transactionId + " voided");
        } else{
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                System.err.println("Attribute: " + error.getAttribute());
                System.err.println("Code: " + error.getCode());
                System.err.println("Message: " + error.getMessage());
            }
        }
    }

    public static double calculateSettlementCharge(int quantity, double closingPrice, 
            double shippingCost, double redeemedAmount) {
        double basePrice = quantity * closingPrice - redeemedAmount;
        double tax = basePrice * HasteerConstants.DEFAULT_CA_SALES_TAX;
        return basePrice + tax + shippingCost;
    }

    private static void sendConfirmationEmail(boolean success, Deal deal, DealParticipant dp, long orderSummaryId) {
        Product p = Product.getById(deal.getProductId());
        if(p == null) {
            System.err.println("Product with id " + deal.getProductId() + " does not exist");
            return;
        }

        DealStatusNotification dsn = new DealStatusNotification(success, p.getProductName(),
                User.getUserById(dp.getBuyerId()), orderSummaryId);
        try {
            dsn.dispatchEmail();
        } catch(Exception e) {
            System.err.println("Error while sending email for processed deal: " + e.getMessage());
        }
    }

    /**
     * Don't need this because Braintree already saves all transactions
     */
    private void saveToTransactionsLog(long userId, double amount, TransactionType trType, boolean isSuccessful) {
        TransactionsLog trLog = new TransactionsLog();
        trLog.setUserId(userId);
        trLog.setAmount(amount);
        trLog.setTransactionType(trType.getValue());
        trLog.setIsSuccessful(isSuccessful);
        trLog.setTransactionTime(new Date());
        trLog.save();
    }

}
