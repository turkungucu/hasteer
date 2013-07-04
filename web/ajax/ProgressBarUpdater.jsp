<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page import="com.dao.DealParticipant"%>

<%
long dealId = Long.parseLong(request.getParameter("dealId"));
int requiredParticipants = Integer.parseInt(request.getParameter("rp"));

int currentParticipants = DealParticipant.getNumParticipantsInDeal(dealId);
int w = currentParticipants*145/requiredParticipants;
%>

<div id="progressbar" class="ui-progressbar ui-widget ui-widget-content ui-corner-all"
     style="height: 10px;" role="progressbar" aria-valuemin="0"
     aria-valuemax="<%=requiredParticipants%>"
     aria-valuenow="<%=currentParticipants%>">
    <div class="ui-progressbar-value ui-progressbar-header ui-corner-left" style="width: <%=w%>px;"></div>
</div>
<table cellpadding="0" cellspacing="0" width="100%" style="font-size: 11px;padding-top: 2px;">
    <tr valign="top">
        <td width="50%"><b><%=currentParticipants%></b><br/>JOINED</td>
        <td><b><%=requiredParticipants%></b><br/>REQUIRED</td>
    </tr>
</table>
                  