<%@page import="java.text.SimpleDateFormat" %>
<%
try {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z");
out.println("Current jvm time: " + sdf.format(System.currentTimeMillis()) + "<br/>");
} catch(Exception e) {
out.println(e.getMessage());
}
%>