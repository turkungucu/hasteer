<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page session="false" %>
<%
    String curPageStr = request.getParameter("curPage");
    String totalHitsStr = request.getParameter("totalHits");
    String pageSizeStr = request.getParameter("pageSize");
    String urlBase = request.getParameter("urlBase");

    int curPage = 0, totalHits = 0, pageSize = 0, pageCount = 0;
    try {
        curPage = Integer.parseInt(curPageStr);
        totalHits = Integer.parseInt(totalHitsStr);
        pageSize = Integer.parseInt(pageSizeStr);
        pageCount = (int)Math.ceil(totalHits / (double)pageSize);
    } catch(Exception e) {
        // ignore
    }  

    if(curPage > 1) {
%>
        <a href="<%= urlBase + "&curPage=" + (curPage-1) %>">Prev</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
<%
    }
    for(int i = 1; i <= pageCount; i++) {
%>
        <a href="<%= urlBase + "&curPage=" + (i) %>"><%= i == curPage ? ("<b><u>" + i + "</u></b>") : i %></a>&nbsp;&nbsp;&nbsp;
<%  }
    if(curPage < pageCount) {
%>
        |&nbsp;&nbsp;&nbsp;<a href="<%= urlBase + "&curPage=" + (curPage+1) %>">Next</a>
<%
    }
%>





