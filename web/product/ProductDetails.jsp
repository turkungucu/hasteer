<%@page import="com.dao.ServiceContract"%>
<%@page import="com.api.Jsp"%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/product/ProductDetails" method="post">
    <table cellpadding="5" width="100%">
        <tr>
            <td width="45%" align="left" valign="top">
                <div class="product-image-container">
                    <div id="active-image-container">
                        <img src="${ProductDetailsForm.images[0].resizedImageUrl}" id="active-image">
                    </div>
                
                    <div id="thumbnail-container">
                        <logic:iterate id="image" name="ProductDetailsForm" property="images" type="com.dao.ProductImage">
                            <img src="<bean:write name="image" property="thumbnailUrl"/>" width="50" height="40"
                                 class="product-image" onmouseover="selectImage(this);" onmouseout="unSelectImage(this);">
                        </logic:iterate>
                    </div>
                </div>
            </td>
            <td width="55%" valign="top">
                <div class="messageNormal">
                    <div style="line-height: 20px;">
                        <table width="100%">
                            <tr>
                                <td colspan="2">
                                    <img src="/Hasteer/images/icons/icon-clock.gif" alt="" style="vertical-align: text-bottom;"/>
                                    <c:choose>
                                        <c:when test="${ProductDetailsForm.running}">
                                            This deal ends in <span id="counter" style="color:#cc0000;font-weight: bold;font-size: 110%"></span>
                                            <script type="text/javascript">
                                                $('#counter').countdown({until: '<c:out value="${ProductDetailsForm.counterDate}"></c:out>', format: 'dHM', layout:'{dn} {dl}, {hn} {hl} and {mn} {ml}'});
                                            </script>
                                        </c:when>
                                        <c:otherwise>
                                            This deal has ended on <span style="color: #cc0000;font-weight: bold;">${ProductDetailsForm.endDate}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <strong>Quick Stats:</strong>
                                </td>
                                <td align="right">
                                    <strong>Pricing Options:</strong>
                                </td>
                            </tr>
                            <tr>
                                <td width="55%" valign="top">
                                    Retail Price: $<bean:write name="ProductDetailsForm" property="retailPrice"/><br/>
                                    Sold by: <bean:write name="ProductDetailsForm" property="product.soldBy"/><br/>
                                    Buyers in deal: <span style="color: #cc0000;font-weight: bold;">${ProductDetailsForm.numParticipants}</span>
                                </td>
                                <td width="45%" align="right" valign="top" nowrap>
                                    <logic:iterate id="pr" name="ProductDetailsForm" property="dealPricingOptions" type="com.dao.DealPricingOption">
                                        <c:choose>
                                            <c:when test="${ ProductDetailsForm.numParticipants >= pr.minNumParticipants }">
                                                <span style="text-decoration: line-through;">
                                                    <bean:write name="pr" property="minNumParticipants" /> buyers for $<bean:write name="pr" property="formattedOptionPrice" />
                                                </span><br/>
                                            </c:when>
                                            <c:otherwise>
                                                <span>
                                                    <bean:write name="pr" property="minNumParticipants" /> buyers for $<bean:write name="pr" property="formattedOptionPrice" />
                                                </span><br/>
                                            </c:otherwise>
                                        </c:choose>
                                    </logic:iterate>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <c:if test="${ProductDetailsForm.running}">
                        <div style="padding-top:10px;">
                            <div style="padding-top:5px;border-top: 1px dotted #cccccc;">
                                <a href="/Hasteer/deals/JoinDeal.do?cmd=display&dealId=<bean:write name="ProductDetailsForm" property="dealId"/>"
                                   class="tooltip" id="img">
                                    <img src="/Hasteer/images/buttons/btn-join.gif" />
                                    <span>Join this deal<small></small></span>
                                </a>
                                <a href="http://www.addthis.com/bookmark.php?v=250&amp;username=alinurg" class="addthis_button" id="img"
                                   addthis:url="http://<%=request.getServerName()%>/Hasteer/product/ProductDetails.do?dealId=${ProductDetailsForm.dealId}&refId=${refId}">
                                    <img src="/Hasteer/images/buttons/btn-share.gif" />
                                </a>
                            </div>
                        </div>
                    </c:if>
                </div>
                <div class="messageRewards" style="margin-top: 10px;">
                    <strong>Referral Rewards Summary</strong> [<a href="#" id="learn-more">Learn More</a>]<br/>
                    <div style="padding: 10px 0px 10px 10px;font-size: 15px;">
                        Earn <span class="red-button" style="cursor: default;">${ProductDetailsForm.rewardPoints}x</span> points per qualified* referral.
                    </div>
                    <small>
                        <span style="line-height: 15px;">*Referred accounts must purchase the product.</span><br/>
                        By sharing this page, you accept our <a href="<%= Jsp.getServiceContractUrl(ServiceContract.REWARDS_TERMS_OF_SERVICE) %>" target="_blank">Rewards Terms Of Service</a>.
                    </small>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2" valign="top">
                <div class="tabs">
                    <ul class="idTabs">
                        <li><a href="#desc">Description</a></li>
                        <li><a href="#policy">Shipping & Returns</a></li>
                    </ul>

                    <div id="desc"><bean:write name="ProductDetailsForm" property="product.details" filter="false" /></div>

                    <div id="policy">
                        <bean:write name="ProductDetailsForm" property="shippingPolicy" filter="false" />
                        <p><bean:write name="ProductDetailsForm" property="returnPolicy" filter="false" /></p>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</html:form>

<div id="expand-icon"></div>

<div id="basic-modal-content" style="text-align: center;">

</div>

<div id="referral-rewards-content">
    <%@include file="/common/ReferralRewards.jsp" %>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        $("#active-image").click(function() {
            // create an image object to get original height, width w/o loading it
            var enlargedImg = new Image();
            enlargedImg.onload = function () {
                var h = this.height;
                var w = this.width;

                var win_h = $(window).height()/1.5;
                var aspectRatio = win_h / h;

                $("#basic-modal-content").css("height", win_h);
                $("#basic-modal-content").css("width", w * aspectRatio);
                $("#basic-modal-content").modal();
            }
            enlargedImg.src = $(this).attr("src").replace("resized_prod_img", "product");
            $("#basic-modal-content").html($('<img src="' + $(this).attr("src").replace("resized_prod_img", "product") + '" style="height: 99%;">'));
        });

        $("#learn-more").click(function() {
            $("#referral-rewards-content").modal();
        });
    });

    function selectImage(img) {
        img.className = "product-image-hover";
        $("#active-image-container img").attr("src", img.src.replace("prod_thumb", "resized_prod_img"));
    }

    function unSelectImage(img) {
        img.className = "product-image";
    }
</script>