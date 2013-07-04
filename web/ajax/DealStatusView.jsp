<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page import="com.dao.Deal"%>
<%@page import="com.dao.DealPricingOption"%>
<%@page import="com.dao.ProductImage"%>
<%@page import="com.constants.HasteerConstants"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<%
long dealId = Long.valueOf(request.getParameter("dealId"));
long optionId = Long.valueOf(request.getParameter("optionId"));

Deal deal = Deal.getById(dealId);
List<DealPricingOption> prOptions = DealPricingOption.getByDealId(dealId);
ProductImage image = ProductImage.getPrimaryImageForProduct(deal.getProductId());

String style = "";

pageContext.setAttribute("deal", deal);
pageContext.setAttribute("prOptions", prOptions);
pageContext.setAttribute("prOptionId", optionId);
pageContext.setAttribute("image", image);
%>

<table cellpadding="0" cellspacing="0" border="0">
    <tr>
        <td valign="top">
            <img src="${image.thumbnailUrl}" style="border: 1px solid #000000;margin-right: 5px;" />
        </td>
        <td valign="top">
            <table border="0">
                <tr>
                    <td style="color:#cccccc;font-weight:bold;">Start Date:</td>
                    <td><bean:write name="deal" property="startDate" formatKey="date.format" /></td>
                </tr>
                <tr>
                    <td style="color:#cccccc;font-weight:bold;">End Date:</td>
                    <td><bean:write name="deal" property="endDate" formatKey="date.format" /></td>
                </tr>
            </table>
            <table cellpadding="0" cellspacing="0" border="0" width="100%" style="margin-top:5px;">
                <c:forEach var="prOption" items="${prOptions}">
                    <c:choose>
                        <c:when test="${prOptionId == prOption.optionId}" >
                            <% style = "background-color: #333333;";%>
                        </c:when>
                        <c:otherwise>
                            <% style = "";%>
                        </c:otherwise>
                    </c:choose>

                    <tr style="<%=style%>">
                        <td style="padding-right: 10px;text-align: right;">
                            <font color="red">
                                <c:out value="${prOption.cumulativeParticipants}" />
                            </font> / <c:out value="${prOption.minNumParticipants}" />
                        </td>
                        <td style="padding-left: 10px;text-align: left;">
                            <c:out value="$ ${prOption.formattedOptionPrice}" />
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>