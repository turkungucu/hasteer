
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.dao.SearchTerm"%>

<%
JSONArray jsonResult = JSONArray.fromObject(SearchTerm.getAllTerms());
%>

<%= jsonResult.toString() %>
