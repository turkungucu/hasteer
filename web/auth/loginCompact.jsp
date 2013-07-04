<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglib/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/taglib/struts-logic.tld" prefix="logic" %>

<a class="red-link" style="outline: none;" href="/Hasteer/auth/CreateAccount.do" tabindex="5">Not a member yet? Sign up.</a><br/>
<html:form action="/auth/Login.do" method="post">
    <table cellspacing="5">
        <tr>
            <td align="right">Username: </td>
            <td>
                <html:text property="username" size="25" tabindex="1" styleId="username"/>
                <br/><html:errors property="username"/>
            </td>
        </tr>
        <tr>
            <td align="right">Password: </td>
            <td>
                <html:password property="password" size="25" tabindex="2"/>
                <br/><html:errors property="password"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <html:submit value="Login" styleClass="red-button" tabindex="3"/>
                <a class="red-link" href="/Hasteer/auth/ForgotMyPassword.do" tabindex="4">Forgot Password?</a>
            </td>
        </tr>
    </table>
    <html:hidden property="cmd" value="submit" />
</html:form>