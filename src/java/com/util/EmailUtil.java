/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;

import com.constants.HasteerConstants;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author ecolak
 */
public class EmailUtil {
    private static final Properties properties;
    private static final String EMAIL_MESSAGES_PROPERTIES = "properties.EmailMessages_en_US";
    private static ResourceBundle bundle;
    private static final boolean bccToSupport;

    static {
        properties = new Properties();
        properties.put("mail.smtp.host", ConfigUtil.getString("mail.smtp.host", HasteerConstants.DEFAULT_MAIL_STMP_HOST));
        properties.put("mail.smtp.port", ConfigUtil.getString("mail.smtp.port", HasteerConstants.DEFAULT_MAIL_STMP_PORT));
        properties.put("mail.smtp.auth", "true");
        
        bundle = ResourceBundle.getBundle(EMAIL_MESSAGES_PROPERTIES);
        bccToSupport = ConfigUtil.getBoolean("mail.bcc.enabled", false);
    }

    public static void sendHtmlEmail(String to, String subject, String body) {
        sendHtmlEmail("info@hasteer.com",
                ConfigUtil.getString("mail.info.password", HasteerConstants.DEFAULT_MAIL_PASSWORD),
                to, subject, body);
    }

    public static void sendHtmlEmail(final String from, final String fromPwd, String to, String subject, String body) {
        sendEmail(from, fromPwd, to, subject, body, "text/html");
    }

    public static void sendPlainTextEmail(final String from, final String fromPwd, String to, String subject, String body) {
        sendEmail(from, fromPwd, to, subject, body, "text/plain");
    }

    public static void sendEmail(final String from, final String fromPwd, String to, String subject, String body, String contentType)
    {
        Session mailSession = Session.getInstance(properties, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, fromPwd);
            }
        });

        Message simpleMessage = new MimeMessage(mailSession);

        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
        InternetAddress bccAddress = null;
        try {
            fromAddress = new InternetAddress(from, "Hasteer");
            toAddress = new InternetAddress(to);
            if (bccToSupport) {
                bccAddress = new InternetAddress("support@hasteer.com");
            }
        } catch (AddressException e) {
            System.out.println(e);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }

        try {
            simpleMessage.setFrom(fromAddress);
            simpleMessage.setRecipient(RecipientType.TO, toAddress);
            if (bccToSupport) {
                simpleMessage.setRecipient(RecipientType.BCC, bccAddress);
            }
            simpleMessage.setSubject(subject);
            simpleMessage.setContent(body, contentType);

            Transport.send(simpleMessage);
        } catch (MessagingException e) {
            System.out.println(e);
        }
    }

    public static String getEmailText(String key) {
        String result = "";
        if(bundle != null) {
            result = bundle.getString(key);
        }
        return result;
    }
}
