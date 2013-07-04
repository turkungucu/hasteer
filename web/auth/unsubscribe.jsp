<%@page import="com.util.AuthUtil"%>
<%@page import="org.apache.commons.lang.StringUtils"%>

<%
String email = request.getParameter("e");
String name = request.getParameter("n");

name = AuthUtil.decodeString(name);
%>

<html>
    <head>
        <title>Unsubscribe</title>
        <script type="text/javascript" src="/Hasteer/js/hasteer.jsp" ></script>
        <link rel="stylesheet" href="/Hasteer/css/hasteer.css" type="text/css">
    </head>
    <body>
        <table align="center" style="margin-top: 50px;" width="500px">
            <tr bgcolor="#eeeeee">
                <td><img src="/Hasteer/images/hasteer_grey.jpg" alt="hasteer" border="0" /></td>
            </tr>
            <tr bgcolor="#f6f6f6">
                <td>
                    <div id="unsubscribe-container" style="padding: 10px;">
                        <% if (StringUtils.isBlank(email) || StringUtils.isBlank(name)) {%>
                        We are unable to recognize the request. Please contact <b>info@hasteer.com</b> with your
                        inquiry to unsubscribe.
                        <% } else {%>
                            <input type="hidden" value="<%=email%>" id="subscriptionEmail" />
                            Email Subscriber: <b><%=email%></b>
                            <p>
                               You've indicated that you would like to unsubscribe from <b>"<%=name%>"</b>. Please
                               click unsubscribe button to continue with your selection.
                            </p>
                            <input type="button" value="Unsubscribe" class="red-button"
                                   onclick="updateSubscriptionStatus('subscriptionEmail', 'unsubscribe-container', 'remove')" />
                        <% }%>
                    </div>
                </td>
            </tr>
        </table>
    </body>
</html>

