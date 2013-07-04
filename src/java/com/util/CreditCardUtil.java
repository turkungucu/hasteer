/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;

import com.api.billing.braintree.BTGatewaySingleton;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.dao.CreditCardDetail.CreditCardType;
import java.math.BigDecimal;

/**
 *
 * @author ecolak
 */
public class CreditCardUtil {

    public static Result<Transaction> submitTransactionForSettlement(String transactionId, BigDecimal amount) {
        BraintreeGateway gateway = BTGatewaySingleton.getInstance();
        Transaction transaction = gateway.transaction().find(transactionId);
        if(transaction != null && Transaction.Status.AUTHORIZED.equals(transaction.getStatus()))
           return gateway.transaction().submitForSettlement(transactionId, amount);

        return null;
    }

    public static Result<Transaction> voidTransaction (String transactionId) {
        BraintreeGateway gateway = BTGatewaySingleton.getInstance();
        Transaction transaction = gateway.transaction().find(transactionId);
        if(transaction != null && 
          (Transaction.Status.AUTHORIZED.equals(transaction.getStatus()) ||
           Transaction.Status.SUBMITTED_FOR_SETTLEMENT.equals(transaction.getStatus()))) {
           return gateway.transaction().voidTransaction(transactionId);
        }

        return null;
    }

    public static Result<Transaction> refund(String transactionId) {
        BraintreeGateway gateway = BTGatewaySingleton.getInstance();
        Transaction transaction = gateway.transaction().find(transactionId);
        if(transaction != null && Transaction.Status.SETTLED.equals(transaction.getStatus()))
           return gateway.transaction().refund(transactionId);

        return null;
    }

    public static String getEncodedLastFourDigits(String creditCardNumber) {
        return creditCardNumber.substring(creditCardNumber.length() - 4);
    }

    public static String decodeLastFourDigits(String lastFour) {
        return lastFour;
    }

    public static String maskCCNumberByType(String lastFour, String cardType) {
        for (CreditCardType ccType : CreditCardType.values()) {
            if (ccType.getValue().equals(cardType)) {
                return ccType.getMask() + lastFour;
            }
        }

        // default
        return "XXXX-XXXX-XXXX-" + lastFour;
    }
}
