/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ecolak
 */
public class LogUtilTest {
    static Logger log = Logger.getLogger(LogUtilTest.class);

    public LogUtilTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testLogging() {
        log.info("testing logging");
        log.debug("testing debugging");
        String s = null;
        int[] i = new int[1];
        try {
            System.out.println(i[1]);
            //System.out.println(s.length());
        } catch(Exception e) {
            log.error("exception caught", e);
        }
    }

}