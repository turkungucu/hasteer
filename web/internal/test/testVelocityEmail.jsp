<%@page import="com.dao.User"%>
<%@page import="com.dao.OrderSummary"%>
<%@page import="com.dao.CreditCardDetail"%>
<%@page import="com.dao.Product"%>
<%@page import="com.dao.ShippingAddress"%>
<%@page import="com.api.notification.RegistrationConfirmation"%>
<%@page import="com.api.notification.WeeklyDeals"%>
<%@page import="com.api.notification.JoinDealConfirmation"%>
<%@page import="com.api.notification.DealStatusNotification"%>
<%@page import="com.api.notification.ShipmentNotification"%>

<%
    User u = new User();
    u.setUserId(666);
    u.setEmail("test@test.com");
    u.setUsername("email-test");

    OrderSummary os = OrderSummary.getById(1);
    CreditCardDetail ccd = CreditCardDetail.getById(12);
    Product p = Product.getById(5);
    ShippingAddress sa = ShippingAddress.getById(4);


    RegistrationConfirmation rc = new RegistrationConfirmation(u);

    WeeklyDeals wd = new WeeklyDeals(u, null);
    
    JoinDealConfirmation jdc = new JoinDealConfirmation(os, ccd, p.getProductName(), sa.getFullAddress());

    DealStatusNotification dsn = new DealStatusNotification(true, "Awesome Tv", u, 5454554);

    ShipmentNotification sn = new ShipmentNotification(os, ccd, p.getProductName(), sa.getFullAddress());
%>

<p><%= rc.generateEmail() %></p>

<p><%= wd.generateEmail() %></p>

<p><%= jdc.generateEmail() %></p>

<p><%= dsn.generateEmail() %></p>

<p><%= sn.generateEmail() %></p>