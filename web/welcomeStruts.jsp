<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.User"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html:html lang="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="welcome.title"/></title>
        <html:base/>
    </head>
    <body style="background-color: white">
        
        <logic:notPresent name="org.apache.struts.action.MESSAGE" scope="application">
            <div  style="color: red">
                ERROR:  Application resources not loaded -- check servlet container
                logs for error messages.
            </div>
        </logic:notPresent>
        
        <h3><bean:message key="welcome.heading"/></h3>
        <p><bean:message key="welcome.message"/></p>
        <p><html:link page="/com/login/login.jsp">Login</html:link></p>
        <p><html:link page="/com/login/userRegistration.jsp">Register</html:link></p>
        <p><html:link page="/com/login/forgotMyPassword.jsp">Forgot my password</html:link></p>
    </body>
</html:html>
