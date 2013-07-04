/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api.notification;

import org.apache.velocity.VelocityContext;

import com.dao.User;

/**
 *
 * @author Alinur Goksel
 */
public class PasswordReset extends BaseVelocityNotificationRule {

    private User user;
    private String randomPassword;

    public PasswordReset(User user, String randomPassword) {
        this.user = user;
        this.randomPassword = randomPassword;
    }

    @Override
    protected VelocityContext getVelocityContext() throws Exception {
        VelocityContext context = new VelocityContext();
        context.put("password", randomPassword);

        return context;
    }

    @Override
    protected User getUser() {
        return user;
    }

}
