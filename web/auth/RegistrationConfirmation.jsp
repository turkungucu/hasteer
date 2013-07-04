<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el" %>
<%@page import="com.struts.form.RegistrationConfirmationForm"%>
<%@page import="org.apache.struts.Globals"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<logic:messagesPresent message="false">
    <div class="messageCriticalWide">
        <html:messages id="error">
            - <bean:write name="error" filter="false"/><br>
        </html:messages>
    </div>
</logic:messagesPresent>

<logic:messagesPresent message="true">
    <div class="messageSuccessWide">
        <html:messages id="msg" message="true">
            <bean:write name="msg" filter="false"/><br>
        </html:messages>
    </div>
</logic:messagesPresent>