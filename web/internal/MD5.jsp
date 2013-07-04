<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.util.AuthUtil"%>

<%
    System.out.println("url: " + request.getRequestURL().toString());
    //System.out.println("<br/>");
    System.out.println(request.isSecure() ? "request is secure" : "request is NOT secure");
    //System.out.println("<br/>");
    System.out.println("scheme: " + request.getScheme());
    //System.out.println("<br/>");

    String cmd = request.getParameter("cmd");
    String encPhrase = null;
    if("submit".equals(cmd)){
        String phrase = request.getParameter("phrase");
        if(phrase != null && phrase.length() > 0) {
            encPhrase = AuthUtil.encryptWithMD5(phrase);
        }
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MD5 page</title>
    </head>
    <body>
        <p><strong>Enter a phrase to get its MD5 code</strong></p>
        <%= (encPhrase != null ? "MD5 phrase: " + encPhrase + "<br/>" : "") %>
        <form>
            <input type="text" name="phrase" /> <br/>
            <input type="submit" value="Get MD5">
            <input type="hidden" name="cmd" value="submit">
        </form>
    </body>
</html>
