<%@page import="com.util.ConfigUtil"%>

<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglib/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/taglib/struts-logic.tld" prefix="logic" %>

<table align="right" cellpadding="0" cellspacing="0">
    <tr valign="middle">
        <td height="36px;">
            <logic:present name="user" scope="session">
                <html:link action="/dashboard/MyAccount.do">
                    <bean:write name="user" property="username" scope="session"/>'s Account
                </html:link> |
                <html:link action="/auth/Logout.do">Logout</html:link>
            </logic:present>
            <logic:notPresent name="user" scope="session">
                    <a id="forms" href="/Hasteer/auth/loginCompact.jsp" title="Login">
                        Login
                    </a> |
                    <a id="forms" href="/Hasteer/auth/createAccountCompact.jsp" title="Create Account">
                        Create an Account
                    </a> |
                </td>
                <td>
                   <img src="/Hasteer/images/buttons/fb-login-btn.png"
                         id="fb-login"
                         style="position:relative;cursor:pointer;padding-left: 5px;"/>
            </logic:notPresent>
        </td>
    </tr>
</table>

<script language='JavaScript' type='text/javascript'>
    $(document).ready(function() {
	$('a#forms').each(function() {
            var $link = $(this);
            var $dialog = $('<div></div>')
            .load($link.attr('href'))
            .dialog({
                autoOpen: false,
                title: $link.attr('title'),
                modal: true,
                resizable: false
            });

            $link.click(function() {
                $dialog.dialog('open');

                return false;
            });
	});

        FB.Event.subscribe('auth.sessionChange', function(response) {
            if (response.session) {
                // A user has logged in, and a new cookie has been saved
            } else {
                // The user has logged out, and the cookie has been cleared
            }
        });

       if (document.getElementById("fb-login")) {
           document.getElementById("fb-login").onclick = function() {
                FB.login(function(response) {
                    if (response.session) {
                        if (response.perms) {
                            window.location="/Hasteer/auth/fbLogin.jsp";
                        } else {
                            // user is logged in, but did not grant any permissions
                        }
                    } else {
                        // user is not logged in
                    }
                }, {perms:'publish_stream,email,user_about_me'});
            };
       }
        
    });
</script>