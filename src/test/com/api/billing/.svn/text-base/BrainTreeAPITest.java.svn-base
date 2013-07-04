/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api.billing;

import com.api.billing.braintree.BTGatewaySingleton;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.CreditCardVerification;
import com.braintreegateway.Customer;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import java.math.BigDecimal;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 * @author ecolak
 */
public class BrainTreeAPITest {

    private static BraintreeGateway gateway;
    public BrainTreeAPITest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        gateway = new BraintreeGateway(
            Environment.SANDBOX,
            "jp8gzb22792vvx96",
            "hxyw4j2kccw9gvbc",
            "jmy63w8jk5sbj5nr"
        );
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void searchCustomer() throws Exception {
        BraintreeGateway gw = BTGatewaySingleton.getInstance();
        Customer cust = gw.customer().find("69");
        System.out.println(cust.getId());
        System.out.println(cust.getFirstName());
        System.out.println(cust.getLastName());
    }

    @Test
    public void performFullSale() throws Exception {
        BraintreeGateway gw = BTGatewaySingleton.getInstance();

        TransactionRequest request = new TransactionRequest().
            amount(new BigDecimal("10.87")).
            //orderId("order id").
            //merchantAccountId("a_merchant_account_id").
            creditCard().
                number("4012000077777777").
                expirationDate("07/2012").
                cardholderName("Fenasi Kerim").
                cvv("975").
                done().
            customerId("69").
            //customer().
                //customerId(gw.customer().find("69").getId()).
                //id("69").
                //firstName("Cin").
                //lastName("Ali").
                //company("Hasteer").
                //phone("312-555-1234").
                //fax("312-555-1235").
                //website("http://www.example.com").
                //email("drew@example.com").
                //done().
            billingAddress().
                firstName("Cin").
                lastName("Ali").
                //company("Braintree").
                streetAddress("1 E Main St").
                extendedAddress("Suite 403").
                locality("Chicago").
                region("Illinois").
                postalCode("60622").
                countryCodeAlpha2("US").
                done().
            options().
                storeInVault(true).
                done();

        Result<Transaction> result = gw.transaction().sale(request);

        if (result.isSuccess()) {
            Transaction transaction = result.getTarget();
            System.out.println("processor auth code: " + transaction.getProcessorAuthorizationCode());
            System.out.println("processor response code: " + transaction.getProcessorResponseCode());
            System.out.println("processor auth text: " + transaction.getProcessorResponseText());
            System.out.println("Success!: " + transaction.getId());
        } else if (result.getTransaction() != null) {
            System.out.println("Message: " + result.getMessage());
            Transaction transaction = result.getTransaction();
            System.out.println("Error processing transaction:");
            System.out.println("  Status: " + transaction.getStatus());
            System.out.println("  Code: " + transaction.getProcessorResponseCode());
            System.out.println("  Text: " + transaction.getProcessorResponseText());
        } else {
            System.out.println("Message: " + result.getMessage());
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                System.out.println("Attribute: " + error.getAttribute());
                System.out.println("  Code: " + error.getCode());
                System.out.println("  Message: " + error.getMessage());
            }
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     //@Test
     public void performSale() {
        TransactionRequest request = new TransactionRequest().
            amount(new BigDecimal("1000.00")).
            creditCard().
                number("4111111111111111").
                expirationDate("05/2009").
                cvv("726").
                done().options().storeInVault(Boolean.TRUE).done();
            //billingAddress().
              //  streetAddress("123 Walnut St.").
                //locality("Walnut Creek").
                //region("CA").
                //postalCode("94401").
                //done();

        Result<Transaction> result = gateway.transaction().sale(request);

        if (result.isSuccess()) {
            Transaction transaction = result.getTarget();
            System.out.println("Success!: " + transaction.getId());
        } else if (result.getTransaction() != null) {
            System.out.println("Message: " + result.getMessage());
            Transaction transaction = result.getTransaction();
            System.out.println("Error processing transaction:");
            System.out.println("  Status: " + transaction.getStatus());
            System.out.println("  Code: " + transaction.getProcessorResponseCode());
            System.out.println("  Text: " + transaction.getProcessorResponseText());
        } else {
            System.out.println("Message: " + result.getMessage());
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                System.out.println("Attribute: " + error.getAttribute());
                System.out.println("  Code: " + error.getCode());
                System.out.println("  Message: " + error.getMessage());
            }
        }
     }

}