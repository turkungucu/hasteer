<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.User"%>
<%@page import="org.apache.struts.Globals"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:form action="/dashboard/ChangePassword" method="post">
    
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
                Old Password:&nbsp;
            </td>
            <td>
                <html:password redisplay="false" name="ChangePasswordForm" property="oldPassword" size="25" />
                <html:errors property="oldPassword"/>
            </td>
        </tr>
        <tr>
            <td id="label">
                New Password:&nbsp;
            </td>
            <td>
                <html:password redisplay="false" name="ChangePasswordForm" property="newPassword1" size="25" />
                <html:errors property="newPassword1"/>
            </td>
        </tr>
        <tr>
            <td id="label">
                New Password:&nbsp;
            </td>
            <td>
                <html:password redisplay="false" name="ChangePasswordForm" property="newPassword2" size="25" />
                <html:errors property="newPassword2"/>
                &nbsp; Please enter your new password again.
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <html:submit value="Change Password" styleClass="red-button" />
            </td>
        </tr>
        <html:hidden property="cmd" value="submit" />
    </table>
</html:form>