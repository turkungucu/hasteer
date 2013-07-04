<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.constants.HasteerConstants,
                com.dao.Product,
                com.dao.ProductImage,
                com.dao.DealPricingOption,
                com.dao.Deal,
                com.util.DateUtils,
                com.struts.form.SearchForm,
                java.util.Date,
                java.util.List,
                com.api.Jsp"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:messagesPresent message="false">
    <div class="messageCriticalWide">
        <ul id="dots">
            <html:messages id="error">
                <li><bean:write name="error" filter="false"/></li>
            </html:messages>
        </ul>
    </div>
</logic:messagesPresent>

<html:form action="/Search" method="get">
    <logic:empty property="results" name="SearchForm">
        <p align="center">No results found</p>
    </logic:empty>
    <logic:notEmpty property="results" name="SearchForm">
    <div id="search-results">
        <table border="0" cellpadding="0" cellspacing="10" width="100%">
            <logic:iterate id="result" property="results" name="SearchForm" type="com.dao.Deal">
                <bean:define id="prodId" name="result" property="productId" type="java.lang.Long" />
                <bean:define id="dealId" name="result" property="dealId" type="java.lang.Long" />
                <%
                    Product product = Product.getById(prodId);
                    DealPricingOption pricingOption = DealPricingOption.getLowestPriceOption(dealId);
                    ProductImage image = ProductImage.getPrimaryImageForProduct(product.getProductId());
                    //image.resizeImage(HasteerConstants.MAX_WIDTH_FOR_THUMBNAIL, HasteerConstants.MAX_HEIGHT_FOR_THUMBNAIL);
                %>
                <tr>
                    <td width="1px">
                        <a href="/Hasteer/product/ProductDetails.do?dealId=<%=dealId%>">
                            <img src="<%= Jsp.getThumbnailUrl(image.getImageId()) %>" style="border:1px solid #eeeeee;"/>
                        </a>
                    </td>
                    <td valign="top">
                        <span class="product-title">
                        <a href="/Hasteer/product/ProductDetails.do?dealId=<%=dealId%>"><%= product.getProductName() %></a>
                        </span><br/>
                        by <%= product.getSoldBy() %>
                        <p>
                             <%
                                Deal deal = Deal.getById(dealId);
                                int daysDiff = DateUtils.daysDiff(new Date(), deal.getEndDate());
                             %>
                             Last <%= daysDiff %> <%= daysDiff == 1 ? "Day" : "Days" %><br/>
                             <span style="font-size:11px;"><%= deal.getFormattedEndDate() %></span>
                        </p>
                    </td>
                    <td align="right">
                        <span class="dealbox-numbers"><%= pricingOption.getMinNumParticipants() %></span><br/>
                        <span class="dealbox-numbers">$<%= pricingOption.getFormattedOptionPrice() %></span><br/>
                        <input type="button" value="Join Deal" class="red-button"
                               onclick="javascript:window.location='/Hasteer/deals/JoinDeal.do?cmd=display&dealId=<%= dealId %>'">
                    </td>
                </tr>
                <tr>
                    <td colspan="3"><div style="border-bottom:1px dotted #666666;"/></td>
                </tr>
            </logic:iterate>
        </table>
    </div>
    
    <p align="center">
    <%
        SearchForm form = (SearchForm)request.getAttribute("SearchForm");
        String urlBase = "/Hasteer/Search.do?q=" + form.getQ();
    %>

    <jsp:include page="/common/pagination.jsp" flush="true">
        <jsp:param name="curPage" value="<%= form.getCurPage() %>" />
        <jsp:param name="totalHits" value="<%= form.getTotalHits() %>" />
        <jsp:param name="pageSize" value="<%= String.valueOf(HasteerConstants.PRODUCTS_PER_PAGE) %>" />
        <jsp:param name="urlBase" value="<%= urlBase %>" />
    </jsp:include>
    </p>
    </logic:notEmpty>
</html:form>