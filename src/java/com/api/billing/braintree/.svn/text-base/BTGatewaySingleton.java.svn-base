/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api.billing.braintree;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.constants.HasteerConstants;
import com.util.ConfigUtil;

/**
 *
 * @author ecolak
 */
public class BTGatewaySingleton {
    private static BraintreeGateway instance;

    private static void constructGateway(){
        instance = new BraintreeGateway(
            Environment.SANDBOX,
            ConfigUtil.getString(HasteerConstants.BRAINTREE_MERCHANT_ID_PROP, HasteerConstants.DEFAULT_BRAINTREE_MERCHANT_ID),
            ConfigUtil.getString(HasteerConstants.BRAINTREE_PUBLIC_KEY_PROP, HasteerConstants.DEFAULT_BRAINTREE_PUBLIC_KEY),
            ConfigUtil.getString(HasteerConstants.BRAINTREE_PRIVATE_KEY_PROP, HasteerConstants.DEFAULT_BRAINTREE_PRIVATE_KEY)
        );
    }

    public static synchronized BraintreeGateway getInstance() {
        if(instance == null)
            constructGateway();

        return instance;
    }
}
