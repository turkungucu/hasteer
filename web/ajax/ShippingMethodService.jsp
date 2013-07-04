
<%@page import="com.api.JoinDealSessionObject"%>
<%@page import="com.api.Pair"%>
<%@page import="com.dao.OrderSummary"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.LinkedList"%>

<%
    JoinDealSessionObject jdso = (JoinDealSessionObject)session.getAttribute("jdso");
    OrderSummary os = jdso.getOrderSummary();
    LinkedList<Pair<String, Double>> shippingMethodsAndCost = os.getShippingMethodsAndCost();
    int shippingMethodIndex = Integer.parseInt(request.getParameter("shippingMethodIndex"));

    Pair<String, Double> pair = shippingMethodsAndCost.get(shippingMethodIndex);

    os.setShippingMethod(pair.getFirst());
    os.setShippingCost(pair.getSecond());

    JSONObject jsonObj = new JSONObject();
    jsonObj.put("shippingCost", os.getFormattedShippingCost());
    jsonObj.put("total", os.getFormattedTotal());

    JSONArray jsonResult = new JSONArray();
    jsonResult.add(jsonObj);
%>

<%= jsonResult.toString() %>
