<%@page import="java.util.List"%>
<%@page import="com.dao.Deal"%>
<%@page import="com.dao.DealParticipant"%>
<%
    String dealIdStr = request.getParameter("did");
    String optionIdStr = request.getParameter("oid");
    long dealId = 0; long optionId = 0;
    try {
        dealId = Long.parseLong(dealIdStr);
        optionId = Long.parseLong(optionIdStr);
    } catch(Exception e){
       // ignore
    }
    if(dealId > 0) {
        int cumParticipants = DealParticipant.getCumulativeParticipants(dealId, optionId);
        //int numParticipants = DealParticipant.getNumParticipantsInDeal(dealId);
        //out.println(numParticipants);
        int maxAllowedParticipants = Deal.getMaxAllowedParticipants(dealId);
        if(cumParticipants < maxAllowedParticipants)
            out.println(cumParticipants);
        else
            out.println("Deal is closed!");
    }
%>

