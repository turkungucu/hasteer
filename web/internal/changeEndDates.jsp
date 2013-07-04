<%@page import="java.util.*"%>
<%@page import="com.dao.*"%>
<%@page import="org.apache.commons.lang.time.DateUtils"%>

<%
    String cmd = request.getParameter("cmd");
    if("change".equals(cmd)) {
        try {
            List<Deal> activeDeals = Deal.getActiveDeals();
            Random random = new Random();
            for(Deal d : activeDeals) {
                int days = random.nextInt(20);
                d.setEndDate(DateUtils.addDays(d.getEndDate(), -days));
                d.save();
            }
        } catch(Exception e) {
            out.println(e.getMessage());
        }
    }
%>