/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.api.notification;

import org.apache.velocity.VelocityContext;

import com.dao.User;
import com.constants.HasteerConstants;

import com.util.AuthUtil;
import com.util.ConfigUtil;
import com.api.Jsp;

/**
 *
 * @author Alinur Goksel
 */
public class RegistrationConfirmation extends BaseVelocityNotificationRule {

    private User user;

    public RegistrationConfirmation(User user) {
        this.user = user;
    }

    @Override
    protected VelocityContext getVelocityContext() throws Exception {
        String confCode = AuthUtil.encryptWithMD5andHex(user.getEmail());

        String urlBase = ConfigUtil.getString("Hasteer.urlBase", HasteerConstants.DEFAULT_HASTEER_URLBASE);
        StringBuffer urlBuf = new StringBuffer(urlBase);

        String userConfirmationPage = HasteerConstants.DEFAULT_USER_CONFIRMATION_PAGE;
        urlBuf.append(userConfirmationPage).append("?uid=").append(user.getUserId()).append("&confCode=").append(confCode);

        VelocityContext context = new VelocityContext();
        context.put("url", urlBuf.toString());

        return context;
    }

    @Override
    protected User getUser() {
        return user;
    }

}
