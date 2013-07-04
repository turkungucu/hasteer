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
    <logic:messagesPresent>
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
            <td id="label">
                Username:&nbsp;
            </td>
            <td>
                <html:text name="CreateAccountForm" property="username" size="25" styleId="username" errorStyleClass="error" />
                <a id="tt">Username can only contain characters and numbers. Minimum required length is 6.</a><br/>
            </td>
        </tr>
        <tr>
            <td id="label">
                Password:&nbsp;
            </td>
            <td valign="middle">
                <html:password name="CreateAccountForm" property="password1" size="25" styleId="password1" errorStyleClass="error" />
                <a id="tt">Please enter a password between 6-15 characters.</a><br/>
            </td>
        </tr>
        <tr>
            <td id="label">
                Verify Password:&nbsp;
            </td>
            <td>
                <html:password name="CreateAccountForm" property="password2" size="25" styleId="password2" errorStyleClass="error" /><br/>
            </td>
        </tr>
        <tr>
            <td id="label">
                Email:&nbsp;
            </td>
            <td>
                <html:text name="CreateAccountForm" property="email1" size="25" errorStyleClass="error" /><br/>
            </td>
        </tr>
        <tr>
            <td id="label">
                Verify Email:&nbsp;
            </td>
            <td>
                <html:text name="CreateAccountForm" property="email2" size="25" errorStyleClass="error" /><br/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <!--<input type="submit" value="Sign Up" class="red-button">-->
                <html:submit value="Sign Up" styleClass="red-button" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td style="font-size: smaller;">
                By registering, you accept our
                <a href="<%= Jsp.getServiceContractUrl(ServiceContract.TERMS_OF_USE) %>" target="_blank">Terms of Use</a>
                and <a href="<%= Jsp.getServiceContractUrl(ServiceContract.PRIVACY_POLICY) %>" target="_blank">Privacy Policy</a>.<br/>
                Membership limited to persons residing in the 50 United States.
            </td>
        </tr>
        <html:hidden property="cmd" value="submit" />
        <html:hidden property="userTypeStr" value="0" />
    </table>
</html:form>
<!--</form>-->
