
package com.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author ecolak
 */
public class StringUtil {
    public static final NumberFormat twoDeciFormat;
    public static final NumberFormat intFormat;
    public static final NumberFormat btFormat;
    static {
        twoDeciFormat = DecimalFormat.getInstance();
        twoDeciFormat.setMaximumFractionDigits(2);
        twoDeciFormat.setMinimumFractionDigits(2);
        intFormat = DecimalFormat.getInstance();
        intFormat.setMaximumFractionDigits(0);
        btFormat = NumberFormat.getInstance();
        btFormat.setMaximumFractionDigits(2);
        btFormat.setMinimumIntegerDigits(2);
        btFormat.setGroupingUsed(false);
    }

    public static String getFormattedPrice(double price) {
        String result = "";
        if(!Double.isNaN(price)) {
            double tenTimes = price * 10;
            double remainder = tenTimes % 10;
            if(remainder > 0)
                result = twoDeciFormat.format(price);
            else
                result = intFormat.format(price);
        }
        return result;
    }

    /**
     * Formats a price as the Braintree gateway expects it
     * @param price - price
     * @return - price formatted as the Braintree gateway expects it
     *         - empty string if any exception occurs
     */
    public static String getBTFormattedPrice(double price) {
        String result = "";
        if(!Double.isNaN(price)) {
            result = btFormat.format(price); 
        }
        return result;
    }
}
