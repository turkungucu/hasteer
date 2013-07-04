
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.dao.SearchTerm"%>
<%@page import="java.util.List"%>

<%
List<SearchTerm> topTerms = SearchTerm.getTopTerms();
JSONArray jsonResult = new JSONArray();

for (SearchTerm topTerm : topTerms) {
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("searchTerm", topTerm.getSearchTerm());
    jsonObj.put("count", topTerm.getCount());
    jsonResult.add(jsonObj);
}
%>

<%= jsonResult.toString() %>
