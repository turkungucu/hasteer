/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api.notification;

import com.dao.User;
import com.util.ConfigUtil;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ecolak
 */
public class EmailNotificationTest {

    public EmailNotificationTest() {
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
    public void testSendUserRegEmail() {
        try {
            ConfigUtil.runStaticInitializers();
            User u = new User();
            u.setUserId(666);
            u.setEmail("test@test.com");
            u.setUsername("email-test");

            RegistrationConfirmation urn = new RegistrationConfirmation(u);
            urn.generateEmail();  
        } catch (Exception e) {
            
        }
    }

}