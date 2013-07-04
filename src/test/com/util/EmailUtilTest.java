/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ecolak
 */
public class EmailUtilTest {

    public EmailUtilTest() {
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
    public void testSendSimpleEmail() {
        System.out.println("sendSimpleEmail");
        String from = "info@hasteer.com";
        String fromPwd = "Hasteer66";
        String to = "emre.colak@yahoo.com";
        String subject = "Testing Hasteer emails";
        String body = "Testing Hasteer emails with JUnit";
        EmailUtil.sendPlainTextEmail(from, fromPwd, to, subject, body);
    }

}