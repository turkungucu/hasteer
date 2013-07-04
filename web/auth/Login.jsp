<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglib/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/taglib/struts-logic.tld" prefix="logic" %>


<html:errors property="usernameDoesNotExist" />
<html:errors property="emailNotVerified" />
<html:errors property="invalidUsernamePassword" />

<html:form action="/auth/Login" method="post">
    <table width="100%" >
        <tr>
            <td width="50%">
                
                <logic:messagesPresent message="false">
                    <div class="messageCriticalWide">
                        <ul id="dots">
                            <html:messages id="error">
                                <li><bean:write name="error" filter="false"/></li>
                            </html:messages>
                        </ul>
                    </div>
                </logic:messagesPresent>

                <table cellspacing="5" align="center">
                    <tr>
                        <td colspan="2">
                            <a class="red-link" style="outline: none;" href="/Hasteer/auth/CreateAccount.do" tabindex="5">Not a member yet? Sign up.</a>
                        </td>
                    </tr>
                    <tr>
                        <td id="label">Username:</td>
                        <td>
                            <html:text property="username" style="width: 150px;" tabindex="1" errorStyleClass="error" /><br/>
                        </td>
                    </tr>
                    <tr>
                        <td id="label">Password:</td>
                        <td>
                            <html:password property="password" style="width: 150px;" tabindex="2" errorStyleClass="error" /><br/>
                        </td>
                    </tr>
                    <tr>
                        <td/>
                        <td>
                            <html:submit value="Login" styleClass="red-button" tabindex="3" /> 
                            <a class="red-link" href="/Hasteer/auth/ForgotMyPassword.do" tabindex="4">Forgot Password?</a>
                        </td>
                    </tr>
                </table>
            </td>
            <td width="50%" align="center" style="border-left: 1px solid #e5e5e5;">
                <img src="http://developers.facebook.com/images/devsite/login-button.png"
                         id="fb-login" style="position:relative;cursor:pointer;" />
            </td>
        </tr>
    </table>
    <html:hidden property="cmd" value="submit" />
</html:form>

<script language='JavaScript' type='text/javascript'>
    $(document).ready(function() {
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