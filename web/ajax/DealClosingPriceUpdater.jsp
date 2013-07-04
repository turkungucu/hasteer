<%@page import="java.util.List"%>
<%@page import="com.dao.Deal"%>
<%@page import="com.dao.DealParticipant"%>
<%
    String dealIdStr = request.getParameter("dealId");
    long dealId = 0;
    try {
        dealId = Long.parseLong(dealIdStr);
    } catch(Exception e){
       // ignore
    }
    if(dealId > 0) {
        Deal deal = Deal.getById(dealId);
        if(deal != null)
            out.println(deal.getClosingPrice());
    }
%>

