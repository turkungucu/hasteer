<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.User"%>
<%@page import="org.apache.struts.Globals"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="/dashboard/ChangeUsernameEmail" method="post">
    <logic:messagesPresent property="<%= Globals.ERROR_KEY %>">
        <div class="messageCriticalWide">
            <ul id="dots">
                <html:messages id="error">
                    <li><bean:write name="error" filter="false"/></li>
                </html:messages>
            </ul>
        </div>
    </logic:messagesPresent>

    <logic:messagesPresent message="true">
        <div class="messageSuccessWide">
            <html:messages id="msg" message="true">
                <bean:write name="msg" filter="false"/><br>
            </html:messages>
        </div>
    </logic:messagesPresent>

    <table cellspacing="5">
        <tr>
            <td id="label">
                Username:&nbsp;
            </td>
            <td>
                <html:text name="ChangeUsernameEmailForm" property="username" size="25" />
                <html:errors property="username"/>
            </td>
        </tr>
        <tr>
            <td id="label">
                Email:&nbsp;
            </td>
            <td>
                <html:text name="ChangeUsernameEmailForm" property="email" size="25" />
                <html:errors property="email"/>
            </td>
        </tr>
        <tr>
            <td id="label">
                Password:&nbsp;
            </td>
            <td>
                <html:password redisplay="false" name="ChangeUsernameEmailForm" property="password" size="25" />
                <html:errors property="password"/>
                &nbsp; We ask you to enter your password again for security reasons.
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <html:submit value="Save" styleClass="red-button" />
            </td>
        </tr>
        <html:hidden property="cmd" value="submit" />
    </table>
</html:form>
