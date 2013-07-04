/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api.notification;

import com.util.EmailUtil;
import com.dao.EmailDispatchLog;
import com.dao.User;
import com.api.Jsp;

import java.util.Date;
import java.io.StringWriter;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.VelocityContext;

/**
 *
 * @author Alinur Goksel
 */
public abstract class BaseVelocityNotificationRule {

    // relative to classpath: /WEB-INF/classes/
    private static String TEMPLATES_FOLDER = "/email-templates/";

    /**
     * Sends the email to the recipient
     * @throws Exception
     */
    public void dispatchEmail() throws Exception {
        String to = getUser().getEmail();
        EmailUtil.sendHtmlEmail(to, getSubject(), generateEmail());
        EmailDispatchLog log = new EmailDispatchLog(to, getEmailTypeKey(), new Date());
        log.save();
    }

    /**
     * Convenience method that returns the email body as String. 
     * It does not dispatch the email.
     * @see dispatchEmail()
     * @return merged template, i.e. HTML source of the email
     * @throws Exception
     */
    public String generateEmail() throws Exception {
        VelocityEngine ve = VelocityEngineSingleton.getInstance();
        StringWriter w = new StringWriter();
        
        VelocityContext vc = getVelocityContext();
        vc.put("base-url", Jsp.getUrlBase());
        vc.put("header-msg", getHeaderMessage());
        vc.put("name", getUser().getUsername());
        vc.put("template-name", getTemplateName());

        try {
            ve.mergeTemplate(TEMPLATES_FOLDER + "HasteerMasterEmailTemplate.vm", "UTF-8", vc, w);
        } catch (Exception e) {
            throw e;
        }

        return w.toString();
    }

    protected String getEmailTypeKey() {
        return this.getClass().getSimpleName();
    }

    private String getSubject() {
        return EmailUtil.getEmailText("email." + getEmailTypeKey() + ".subject");
    }

    private String getTemplateName() {
        return TEMPLATES_FOLDER + EmailUtil.getEmailText("email." + getEmailTypeKey() + ".template");
    }

    private String getHeaderMessage() {
        return EmailUtil.getEmailText("email." + getEmailTypeKey() + ".header-msg");
    }

    protected abstract User getUser();

    protected abstract VelocityContext getVelocityContext() throws Exception;

}
