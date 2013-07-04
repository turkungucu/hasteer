/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api.shipping;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import com.api.shipping.usps.USPSWebTools;
import com.api.Pair;
import com.dao.Product;

/**
 *
 * @author Alinur Goksel
 */
public class USPSRateServiceTest {

    public USPSRateServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of sendSimpleEmail method, of class EmailUtil.
     */
    @Test
    public void testGetUSPSRates() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        String shipDate = dateFormatter.format(c.getTime());

        //Create a mock product object
        Product product = new Product();
        product.setWeight(10);
        product.setWidth(10);
        product.setLength(12);
        product.setHeight(2);

        LinkedList<Pair<String, Double>> shippingMethodsAndCost =
                USPSWebTools.getShippingMethodsAndCost("94404", "08201", product, 1, true);

        for (Pair<String, Double> p : shippingMethodsAndCost) {
            System.out.println("Service: " + p.getFirst() + ", Rate: " + p.getSecond());
        }

    }
}