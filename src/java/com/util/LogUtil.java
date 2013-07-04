/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;

import java.io.IOException;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 *
 * @author ecolak
 */
public class LogUtil {
    private static Logger log = Logger.getLogger(LogUtil.class);

    // private constructor so that this class cannot be instantiated
    private LogUtil() {}

    public static Logger getLoggerWithUniqueName(Class clazz) {
        Logger logger = Logger.getLogger(clazz);
        String logFilename = clazz.getSimpleName() + "." + System.currentTimeMillis() + ".log";
        Layout layout = new PatternLayout("%d{dd MMM yyyy HH:mm:ss} [%t] %-5p %c - %m%n");

        try {
            FileAppender appender = new FileAppender(layout, logFilename);
            logger.addAppender(appender);
        } catch(IOException e) {
            log.error(e.getMessage(), e);
        }
        return logger;
    }

    public static Logger getLogger(Class clazz) {
        return Logger.getLogger(clazz);
    }
}
