/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;

import com.util.AuthUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ecolak
 */
public class AuthTest {

    public AuthTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @Test
    public void testEncryption() throws Exception {
        String password = "colemre@gmail.com";
        System.out.println("password: " + password);
        String encPassword = AuthUtil.encryptWithDES(password);
        System.out.println("cipher text: " + encPassword + ", length: " + encPassword.length());
        String decPassword = AuthUtil.decryptWithDES(encPassword);
        System.out.println("plain text: " + decPassword);
        assertEquals(password, decPassword);
        System.out.println("plain text 2: " + AuthUtil.decryptWithDES(encPassword));
        assertEquals(password, decPassword);
    }

    @Test
    public void testHexToBytes(){

    }
}