<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.User"%>
<%@page import="com.dao.ServiceContract"%>
<%@page import="com.api.Jsp"%>
<%@page import="org.apache.struts.Globals"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="/auth/CreateAccount" method="post">
    <logic:messagesPresent property="<%= Globals.ERROR_KEY %>">
        <div class="messageCriticalWide">
            <ul id="dots">
                <html:messages id="error">
                    <li><bean:write name="error" filter="false"/></li>
                </html:messages>
            </ul>
        </div>
    </logic:messagesPresent>
    
    <table cellspacing="5">
        <tr>
            <td style="font-style: italic;font-weight: bold;">All fields are required.</td>
        </tr>
        <tr>
            <td>
                Username<br/>
                <html:text name="CreateAccountForm" property="username" size="25" styleId="username" />
                <html:errors property="username"/>
            </td>
        </tr>
        <tr>
            <td>
                Password<br/>
                <html:password name="CreateAccountForm" property="password1" size="25" styleId="password1" />
                <html:errors property="password1"/>
            </td>
        </tr>
        <tr>
            <td>
                Retype Password<br/>
                <html:password name="CreateAccountForm" property="password2" size="25" styleId="password2" />
                <html:errors property="password2"/>
            </td>
        </tr>
        <tr>
            <td>
                Email<br/>
                <html:text name="CreateAccountForm" property="email1" size="25" />
                <html:errors property="email1"/>
            </td>
        </tr>
        <tr>
            <td>
                Retype Email<br/>
                <html:text name="CreateAccountForm" property="email2" size="25" />
                <html:errors property="email2"/>
            </td>
        </tr>
        <tr>
            <td>
                <!--<input type="submit" value="Sign Up" class="red-button">-->
                <html:submit value="Sign Up" styleClass="red-button" />
            </td>
        </tr>
        <tr>
            <td style="font-size: smaller;">
                By registering, you accept our
                <a href="<%= Jsp.getServiceContractUrl(ServiceContract.TERMS_OF_USE) %>" style="color: blue;" target="_blank">Terms of Use</a>
                and <br/><a href="<%= Jsp.getServiceContractUrl(ServiceContract.PRIVACY_POLICY) %>" style="color: blue;" target="_blank">Privacy Policy</a>.
                Membership limited to persons residing in the 50 United States.
            </td>
        </tr>
        <html:hidden property="cmd" value="submit" />
        <html:hidden property="userTypeStr" value="0" />
    </table>
</html:form>
<!--</form>-->
