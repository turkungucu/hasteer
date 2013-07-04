<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglib/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/taglib/struts-logic.tld" prefix="logic" %>

<html:form action="/auth/ForgotMyPassword" method="post">
    <logic:messagesPresent message="false">
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

    <table>
        <tr>
            <td id="label">
                Email address:
            </td>
            <td>
                <html:text property="emailAddr" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <html:submit value="Resend Password" styleClass="red-button" />
            </td>
        </tr>
    </table>
    <html:hidden property="cmd" value="submit" />
</html:form>
