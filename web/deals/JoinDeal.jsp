<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.dao.Deal, com.dao.ProductImage, com.dao.RewardPointsBalance"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<script type="text/javascript">
    function leaveDeal() {
        var ok = confirm('Are you sure that you want to leave this deal?');
        if(ok) {
            window.document.JoinDealForm.cmd.value = 'leave';
            window.document.JoinDealForm.submit();
        }
    }

    function quickJoin() {
        window.document.JoinDealForm.cmd.value = 'quickJoin';
        window.document.JoinDealForm.submit();
    }

</script>

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

<logic:present name="JoinDealForm" property="deal">
    
    <html:form method="get" action="/deals/JoinDeal.do">
        <div class="messageNormal">
            <c:choose>
                <c:when test="${JoinDealForm.userAlreadyInDeal}">
                    You have already joined this deal. You can update your pricing option or <a href="javascript:leaveDeal()">leave</a> the deal.
                </c:when>
                <c:otherwise>
                    Please select the <b>maximum</b> price you are willing to pay and specify the quantity for this deal.
                </c:otherwise>
            </c:choose>
        </div>
        <table align="center" width="100%" cellspacing="10" style="font-size: 13px;">
            <tr>
                <td id="label">
                    Product Name:
                </td>
                <td>
                    <a href="/Hasteer/product/ProductDetails.do?dealId=<bean:write name="JoinDealForm" property="dealId" />">
                        <bean:write name="JoinDealForm" property="product.productName" />
                    </a>
                </td>
            </tr>
            <tr>
                <td id="label">
                    Sold By:
                </td>
                <td>
                    <bean:write name="JoinDealForm" property="product.soldBy" />
                </td>
            </tr>
            <tr>
                <td id="label">
                    Deal Ends:
                </td>
                <td>
                    <bean:write name="JoinDealForm" property="deal.formattedEndDate" />
                </td>
            </tr>
            <tr>
                <td valign="top" nowrap id="label">
                    Select Your Price:
                </td>
                <td width="80%" valign="top">
                    <b>Pricing Options</b><br/>
                    <table border="0" cellspacing="0" cellpadding="3" bgcolor="#f6f6f6"
                           width="45%">
                        <tr>
                            <td colspan="4" style="border-top: 1px dotted #333333;"></td>
                        </tr>
                        <tr align="center">
                            <td></td>
                            <td>
                                Current
                                <a id="tt">This cumulative number (top to bottom) shows how many participants have already joined the deal.</a>
                            </td>
                            <td>
                                Required
                                <a id="tt">Minimum number of required participants to reach the corresponding unit price.</a>
                            </td>
                            <td>
                                Price
                                <a id="tt">Unit price of the item if required count is reached.</a>
                            </td>
                        </tr>
                        <logic:iterate id="po" name="JoinDealForm" property="pricingOptions">
                            <tr id="row-${po.optionId}" align="center">
                                <td>
                                    <html-el:radio name="JoinDealForm" value="${po.optionId}"
                                                   property="prOptionId" onclick="highlight('row-${po.optionId}');"/>
                                </td>
                                <td>
                                    <span id="spParticipants${po.optionId}">
                                        <script type="text/javascript">
                                            updateCumulativeParticipantsInDeal(${po.dealId},${po.optionId},'spParticipants${po.optionId}', 5000);
                                        </script>
                                    </span>
                                </td>
                                <td>
                                    <span><bean:write name="po" property="minNumParticipants" />+</span>
                                </td>
                                <td>
                                    <span>
                                        &nbsp;$ <bean:write name="po" property="formattedOptionPrice" />
                                    </span>
                                </td>
                            </tr>
                        </logic:iterate>
                    </table>
                </td>
            </tr>
            <tr>
                <td id="label" valign="top">
                    Pay with Points:
                </td>
                <td>
                    <b>Points Summary</b><br/>
                    <table border="0" cellspacing="0" cellpadding="3" bgcolor="#f6f6f6"
                           width="45%">
                        <tr>
                            <td style="border-top: 1px dotted #333333;"></td>
                        </tr>
                        <tr>
                            <td>
                                Total: 
                                <b>
                                    <c:if test="${JoinDealForm.rewardPointsBalance.points == null}">0</c:if>
                                    ${JoinDealForm.rewardPointsBalance.points}
                                </b> points
                            </td>
                        </tr>
                        <tr>
                            <c:choose>
                                <c:when test="${JoinDealForm.rewardPointsBalance.redeemablePoints > 0}">
                                    <td>
                                        Redeemable
                                        <a id="tt">Points can be redeemed at <%=RewardPointsBalance.getRewardPointsExchangeRate()%> point increments.</a>:
                                        <b>${JoinDealForm.rewardPointsBalance.redeemablePoints}</b> points
                                        = <b>$${JoinDealForm.rewardPointsBalance.pointsCashValue}</b>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td colspan="2">
                                        You do not have any redeemable 
                                        <a id="tt">You must have at least 10 points to start redeeming.</a> points.
                                    </td>
                                </c:otherwise>
                            </c:choose> 
                        </tr>
                        <c:if test="${JoinDealForm.rewardPointsBalance.redeemablePoints > 0}">
                            <tr>
                                <td nowrap>
                                    Amount to Redeem:
                                    $ <html:text property="redeemedAmount" size="2" /><br/>
                                    <span class="small-text-turq">Leave blank if you do not wish to pay with points.</span>
                                </td>
                            </tr>
                        </c:if>
                    </table>
                </td>
            </tr>
            <tr>
                <td id="label">
                    Quantity:
                </td>
                <td>
                    <html:text property="quantity" size="1" />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="right">
                    <c:if test="${JoinDealForm.quickJoinEnabled}">
                        <input type="button" value="Quick Join&#153;" class="turq-button" onclick="quickJoin();" />
                        <a id="tt">
                            Quick Join&#153; automatically selects your most recent
                            choices for Payment Method and Shipping Address.
                            <div style="padding-top:5px;">
                                You will be taken to the final step where you
                                can review these selections before joining the deal.
                            </div>
                        </a>
                        <span style="font-weight: bold;padding-left: 10px;padding-right: 10px;">OR</span>
                    </c:if>
                    <input type="submit" value="Next" class="red-button" />
                </td>
            </tr>
        </table>
       
        <input type="hidden" name="cmd" value="join">
        <input type="hidden" name="dealId" value="<bean:write name="JoinDealForm" property="dealId"/>">
    </html:form>
</logic:present>

<script language='JavaScript' type='text/javascript'>
    
    function highlight(id) {
        $('tr[id|=row]').css("background-color", "#f6f6f6");
        document.getElementById(id).style.background= "#FFA9A9";
        //$(id).css("background-color", "#FFA9A9");
    }
    
</script>
