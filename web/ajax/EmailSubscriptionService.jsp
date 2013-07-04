<%@page import="com.dao.EmailSubscriber"%>
<%@page import="com.dao.EmailDispatchLog"%>
<%@page import="com.api.notification.WeeklyDeals"%>
<%@page import="java.util.Date"%>

<%
 String op = request.getParameter("op");
 String email = request.getParameter("email");
 String emailTypeKey = WeeklyDeals.class.getSimpleName();

 EmailSubscriber subscriber =
         EmailSubscriber.getByEmailAndTypeKey(email, emailTypeKey);

 String message = null;
 if ("remove".equals(op) && subscriber != null) {
     subscriber.delete();
     message = "Sorry to see you leave. Your email address <b>" + email + "</b> "
             + "has been successfully removed from the mailing list.";
 } else if ("add".equals(op) && subscriber == null) {
    subscriber = new EmailSubscriber(email, emailTypeKey, new Date());
    subscriber.save();
    message = "Thank you! Your email address <b>" + email + "</b> has been "
            + "successfully added to the mailing list.";
 } else {
     message = "remove".equals(op) ? "Email address <b>" + email + "</b> does not exist in the mailing list." :
         "add".equals(op) ? "Email address <b>" + email + "</b> already exists in the mailing list." :
             "Invalid Request.";
 }
%>

<%=message%>
