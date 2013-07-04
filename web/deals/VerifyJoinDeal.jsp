<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.util.StringUtil, com.api.Pair" %>

<logic:messagesPresent message="false">
    <div class="messageCriticalWide">
        <ul id="dots">
            <html:messages id="error">
                <li><bean:write name="error" filter="false"/></li>
            </html:messages>
        </ul>
    </div>
</logic:messagesPresent>

<logic:messagesPresent message="true">
    <div class="messageSuccessWide">
        <html:messages id="msg" message="true">
            <bean:write name="msg" filter="false"/><br>
        </html:messages>
    </div>
</logic:messagesPresent>

<table cellspacing="5" width="100%">
    <tr>
        <td width="60%" valign="top">
            <div class="summary-table-header">
                Your Order:
                <html:link action="/deals/JoinDeal.do" style="font-weight: normal;">
                    [Edit]
                </html:link>
            </div>
            <div class="summary-table-body">
                <table>
                    <tr>
                        <td valign="top"><img src="${VerifyJoinDealForm.product.primaryImage.thumbnailUrl}" width="100px" height="100px;" class="product-image"/></td>
                        <td valign="top">
                            ${VerifyJoinDealForm.product.productName}<p/>
                            <table style="text-align: center;font-size: 11px;" width="300px">
                                <tr>
                                    <th>Quantity</th>
                                    <th>Unit Price</th>
                                    <th>Subtotal</th>
                                </tr>
                                <tr>
                                    <td>${VerifyJoinDealForm.orderSummary.quantity}</td>
                                    <td>$${VerifyJoinDealForm.orderSummary.formattedUnitPrice}</td>
                                    <td>$${VerifyJoinDealForm.orderSummary.formattedSubtotal}</td>
                                </tr>
                            </table>                   
                        </td>
                    </tr>
                </table>
            </div>
        </td>
        <td width="40%" valign="top">
            <div class="summary-table-header">Order Summary:</div>
            <div class="summary-table-body">
                <table width="100%">
                    <tr>
                        <td width="70%"><b>Subtotal</b></td>
                        <td width="30%" align="right">
                            $${VerifyJoinDealForm.orderSummary.formattedSubtotal}
                        </td>
                    </tr>
                    <tr>
                        <td><b>Tax</b></td>
                        <td align="right">$${VerifyJoinDealForm.orderSummary.formattedTax}</td>
                    </tr>
                    <tr>
                        <td><b>Shipping</b></td>
                        <td align="right">
                            $<span id="shippingCost">${VerifyJoinDealForm.orderSummary.formattedShippingCost}</span>
                        </td>
                    </tr>
                    <c:if test="${VerifyJoinDealForm.orderSummary.redeemedAmount > 0}">
                        <tr>
                            <td><b>Redeemed Amount</b></td>
                            <td align="right">- $${VerifyJoinDealForm.orderSummary.formattedRedeemedAmount}</td>
                        </tr>
                    </c:if>
                    <tr>
                        <td></td>
                        <td style="border-top: 1px solid #555555;" />
                    </tr>
                    <tr style="color: green;font-size: 13px;font-weight: bold">
                        <td>Total</td>
                        <td align="right">
                            $<span id="total">${VerifyJoinDealForm.orderSummary.formattedTotal}</span>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <span class="small-text-silver">
                                Note: Your credit card will only be charged when the deal goes through.
                            </span>
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <div class="summary-table-header">
                Shipping Address:
                <html:link action="/dashboard/buyer/ManageShippingAddresses.do" style="font-weight: normal;">
                    [Edit]
                </html:link>
            </div>
            <div class="summary-table-body">
                <c:out value="${VerifyJoinDealForm.shippingDetails}" escapeXml="false" /><br/>
                ${VerifyJoinDealForm.contactPhone}
            </div>
        </td>
        <td valign="top">
            <div class="summary-table-header">
                Shipping Method:
                <a id="tt">
                    Express Mail: Overnight/Next-Day<br/>
                    Priority Mail: 2 to 3 days<br/>
                    Parcel Post/Ground: 7 days<br/>
                </a>
            </div>
            <div class="summary-table-body">
                <c:forEach items="${VerifyJoinDealForm.orderSummary.shippingMethodsAndCost}" var="shippingMethod" varStatus="shippingMethodIndex">
                    <c:choose>
                        <c:when test="${shippingMethod.first == VerifyJoinDealForm.orderSummary.shippingMethod}">
                            <input type="radio" name="shipping-method" value="${shippingMethodIndex.index}" checked />
                                ${shippingMethod.first} -
                                $<%= StringUtil.twoDeciFormat.format(((Pair)pageContext.getAttribute("shippingMethod")).getSecond()) %>
                        </c:when>
                        <c:otherwise>
                            <input type="radio" name="shipping-method" value="${shippingMethodIndex.index}" />
                                ${shippingMethod.first} -
                                $<%= StringUtil.twoDeciFormat.format(((Pair)pageContext.getAttribute("shippingMethod")).getSecond()) %>
                        </c:otherwise>
                    </c:choose>
                    <br/>
                </c:forEach>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <div class="summary-table-header">
                Billing Address:
                <html:link action="/dashboard/CreditCardSetup.do" style="font-weight: normal;">
                    [Edit]
                </html:link>
            </div>
            <div class="summary-table-body">
                <c:out value="${VerifyJoinDealForm.ccDetails.fullAddress}" escapeXml="false" />
            </div>
        </td>
        <td valign="top">
            <div class="summary-table-header">
                Payment Method:
                <html:link action="/dashboard/CreditCardSetup.do" style="font-weight: normal;">
                    [Edit]
                </html:link>
            </div>
            <div class="summary-table-body">
                <b>${VerifyJoinDealForm.ccDetails.cardType}</b><br/>
                ${VerifyJoinDealForm.ccDetails.cardholderName},
                ${VerifyJoinDealForm.ccDetails.maskedCardNumber},
                ${VerifyJoinDealForm.ccDetails.formattedExpiryDate}
            </div>
        </td>
    </tr>
    <tr align="right">
        <td colspan="2">
            <html:form method="POST" action="/deals/VerifyJoinDeal.do" styleId="myform">
                <input type="hidden" name="cmd" value="">
                <input type="button" value="Previous" class="dark-gray-button" id="prev-btn" />
                <input type="button" value="Confirm" class="red-button" id="confirm-btn" />
            </html:form>
        </td>
    </tr>
</table>

<!-- Modal Window -->
<div id="basic-modal-content">
    <div style="float: left;">
        <img src="/Hasteer/images/ajax-loader-big.gif" />
    </div>
    <div style="float: right;padding-top: 9px;">
        Please do not refresh the page while we submit your order.
    </div>
</div>

<script language='JavaScript' type='text/javascript'>
    $('#confirm-btn').click(function() {
        $("#basic-modal-content").modal({
            minHeight: 45,
            minWidth: 380,
            close: false
        });
        $("input[name=cmd]").val('next');
        $("#myform").submit();
    });

    $('#prev-btn').click(function() {
        $("input[name=cmd]").val('previous');
        $("#myform").submit();
    });

    $('input:radio').change(function() {
        var shippingMethodIndex = $(this).val();
        $.getJSON('/Hasteer/ajax/ShippingMethodService.jsp', 'shippingMethodIndex=' + shippingMethodIndex, function(data) {
            $("#shippingCost").html(data[0].shippingCost);
            $("#total").html(data[0].total);
        });
    });
</script>