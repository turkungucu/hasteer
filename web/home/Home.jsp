<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<%@page import="java.util.Map, 
                java.util.HashMap,
                java.util.List,
                java.util.Date"%>

<%@page import="com.dao.Deal,
                com.dao.Deal.DealType,
                com.dao.DealPricingOption,
                com.dao.DealParticipant,
                com.dao.Product,
                com.dao.ProductImage,
                com.util.DateUtils,
                com.util.ConfigUtil,
                com.constants.HasteerConstants,
                com.api.DealItem,
                com.api.Jsp,
                net.sf.json.JSONArray,
                net.sf.json.JSONObject"%>


<%
List<DealItem> hotDeals = Deal.getHotDeals();
List<DealItem> regularDeals = Deal.getRegularDeals();

pageContext.setAttribute("regularDeals", regularDeals);
pageContext.setAttribute("hotDeals", hotDeals);

JSONArray jsonResult = new JSONArray();
for (DealItem regularDeal : regularDeals) {
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("dealId", regularDeal.getDealId());
    jsonObj.put("rp", regularDeal.getRequiredParticipants());
    jsonResult.add(jsonObj);
}

long regularDealsUpdateFreq = ConfigUtil.getLong(Jsp.getProperty(pageContext, "regularDeals.updateFrequency"), 5000);
%>

<table align="center" border="0" width="800px" cellpadding="0" cellspacing="0">
    <tr>
        <!-- HOT DEALS -->
        <td valign="top" style="padding-left: 40px;">
            <span class="page-title">Hot Deals</span><br/>
            <div class="hotdeals-container">
                <div class="wrapper" style="overflow: hidden;">
                    <ul>
                        <c:forEach items="${hotDeals}" var="hotDeal">
                            <li>
                                <div class="dealbox-bg-big">
                                    <div style="height:15%;background-color:#eeeeee;border-bottom:1px dotted #cccccc;">
                                        <div style="float:left;padding-left:5px;padding-top:5px;">
                                            <span class="product-title">${hotDeal.productName}&nbsp;</span>
                                            <html-el:link action="/product/ProductDetails.do?dealId=${hotDeal.dealId}" styleId="img">
                                                <img src="/Hasteer/images/icons/icon-question.gif" align="bottom" border="0" alt="Details" />
                                            </html-el:link><br/>
                                            by ${hotDeal.soldBy}
                                        </div>
                                        <div style="float:right;padding-left:5px;padding-top:5px;padding-right:5px;">
                                            <span class="product-title">
                                                Last ${hotDeal.daysDiff}
                                                <c:choose>
                                                    <c:when test="${hotDeal.daysDiff == 1}">Day</c:when>
                                                    <c:otherwise>Days</c:otherwise>
                                                </c:choose>
                                            </span><br/>
                                            <span style="font-size:14px;">${hotDeal.endDate}</span>
                                        </div>
                                    </div>
                                    <div style="height:85%;">
                                        <div style="float:left;width:66%;">
                                            <p id="product-container">
                                                <img src="${hotDeal.imgUrl}" />
                                            </p>
                                        </div>
                                        <div style="float:left;width:34%;height:100%;position:relative;">
                                            <div style="position:absolute;bottom:30%;">
                                                <div style="height:50px;border-bottom:1px dotted #666666;padding-bottom:5px;">
                                                    <div style="float:left;"><img src="/Hasteer/images/buyer.jpg"/></div>
                                                    <div style="float:left;padding-top:10px;padding-left:5px;">
                                                        <span class="dealbox-numbers">${hotDeal.requiredParticipants}</span>
                                                    </div>
                                                    <div style="float:left;padding-top:15px;">
                                                        <span class="small-text">&nbsp;Required<br/>&nbsp;Participants</span>
                                                    </div>
                                                </div>
                                                <div style="height:50px;padding-top:5px;">
                                                    <div style="float:left;"><img src="/Hasteer/images/currency.jpg"/></div>
                                                    <div style="float:left;padding-top:8px;padding-left:5px;">
                                                        <span class="dealbox-numbers">${hotDeal.dealPrice}</span>
                                                    </div>
                                                    <div style="float:left;padding-top:14px;">
                                                        <span class="small-text">&nbsp;Unit<br/>&nbsp;Price</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div style="position:absolute;bottom:5px;right:5px;">
                                                <input type="button" value="Join Deal" class="red-button"
                                                       onclick="javascript:window.location='/Hasteer/deals/JoinDeal.do?dealId=${hotDeal.dealId}'">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </td>
        <td valign="top" align="right" style="padding-right: 40px;">          
            <span class="page-title" style="text-align: left;">Activity Feed</span><br/>
            <fb:activity width="380" height="302" font="arial" border_color="#e5e5e5" recommendations="true" header="false"></fb:activity>
        </td>
    </tr>
    <tr>
        <!-- REGULAR DEALS -->
        <td valign="top" colspan="2" id="regular-deals">
            <table cellspacing="40px">
                <tr>
                    <c:forEach items="${regularDeals}" var="regularDeal" varStatus="status">
                        <c:if test="${status.index > 0 && status.index % 5 == 0}">
                        </tr>
                        <tr>
                        </c:if>
                        <td class="page-container-gray" valign="top">
                            <div style="width: auto;">
                                <div>
                                    <html-el:link action="/product/ProductDetails.do?dealId=${regularDeal.dealId}" styleId="img">
                                        <img src="${regularDeal.thumbnailUrl}" align="center" width="140px" height="120px" class="product-image"/>
                                    </html-el:link>
                                </div>

                                <div style="height: 25px;">
                                    <html-el:link action="/product/ProductDetails.do?dealId=${regularDeal.dealId}">
                                        ${regularDeal.productName}
                                    </html-el:link>
                                </div>
                                <p/>
                                <div style="white-space: nowrap;">
                                    Price: <span style="font-weight: bold;"><del>$${regularDeal.originalPrice}</del> $${regularDeal.dealPrice}</span><br/>
                                    Savings: <span style="color:green;font-weight: bold;">$${regularDeal.savings} (${regularDeal.percentSavings})</span>
                                </div>
                                <p/>
                                <div style="width: 140px;padding-bottom: 10px;" id="progress-${regularDeal.dealId}">
                                    <c:set var="w" value="${(regularDeal.currentParticipants*140)/regularDeal.requiredParticipants}" />
                                    <div id="progressbar" class="ui-progressbar ui-widget ui-widget-content ui-corner-all"
                                         style="height: 10px;" role="progressbar" aria-valuemin="0"
                                         aria-valuemax="${regularDeal.requiredParticipants}"
                                         aria-valuenow="${regularDeal.currentParticipants}">
                                        <div class="ui-progressbar-value ui-progressbar-header ui-corner-left" style="width: ${w}px;"></div>
                                    </div>
                                    <table cellpadding="0" cellspacing="0" width="100%" style="font-size: 11px;padding-top: 2px;">
                                        <tr valign="top">
                                            <td width="50%"><b>${regularDeal.currentParticipants}</b><br/>JOINED</td>
                                            <td><b>${regularDeal.requiredParticipants}</b><br/>REQUIRED</td>
                                        </tr>
                                    </table>
                                </div>
                                <div style="padding-bottom: 10px;border-top: 1px solid #e5e5e5;">
                                    <span style="font-weight: bold;color: #cc0000;">
                                        Last ${regularDeal.daysDiff}
                                        <c:choose>
                                            <c:when test="${regularDeal.daysDiff == 1}">Day</c:when>
                                            <c:otherwise>Days</c:otherwise>
                                        </c:choose>
                                    </span><br/>
                                    <span style="font-size: smaller">${regularDeal.endDate}</span>
                                </div>
                                <input type="button" value="Join Deal" class="turq-button"
                                       onclick="javascript:window.location='/Hasteer/deals/JoinDeal.do?dealId=${regularDeal.dealId}'">
                            </div>
                        </td>
                    </c:forEach>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td valign="top" style="padding-left: 40px;padding-bottom: 10%;">
            <span class="page-title" style="text-align: left;">The Hype</span><br/>
            <div class="tagcloud-container">
                <div id="tagCloud" class="wrapper" style="overflow: hidden;">

                </div>
            </div>
        </td>
        <td valign="top" style="padding-left: 40px;padding-right: 40px;">
            <span class="page-title" style="text-align: left;">Weekly Deals</span><br/>
            <div class="page-container-white" id="newsletter-container">
                If you'd like Weekly Deals delivered to your inbox, please enter your email address in the field below:
                <p>
                    <input type="text" id="subscriptionEmail" size="30"/>&nbsp;
                    <input type="button" value="Subscribe" class="red-button" 
                           onclick="updateSubscriptionStatus('subscriptionEmail', 'newsletter-container', 'add')" />
                </p>
            </div>
        </td>
    </tr>
</table>

<script type="text/javascript">
    $(document).ready(function(){
        $('.hotdeals-container').anythingSlider({
            easing: "swing",            // Anything other than "linear" or "swing" requires the easing plugin
            autoPlay: true,             // This turns off the entire FUNCTIONALY, not just if it starts running or not
            startStopped: true,         // If autoPlay is on, this can force it to start stopped
            delay: 5000,                // How long between slide transitions in AutoPlay mode
            animationTime: 2000,        // How long the slide transition takes
            hashTags: true,             // Should links change the hashtag in the URL?
            buildNavigation: true,      // If true, builds and list of anchor links to link to each slide
            pauseOnHover: true,         // If true, and autoPlay is enabled, the show will pause on hover
            startText: "Start",         // Start text
            stopText: "Pause",           // Stop text
            navigationFormatter: null,  // Details at the top of the file on this use (advanced use)
            buildArrows: false          // If true, builds the forwards and backwards buttons
        });

        $('.hotdeals-container').data('AnythingSlider').startStop(true);  // start the slideshow

        $.getJSON("/Hasteer/ajax/TagCloudService.jsp", function(data) {
            //create list for tag links
            $("<ul>").attr("id", "tagList").appendTo("#tagCloud");

            //create tags
            $.each(data, function(i, val) {
              //create item
              var li = $("<li>");
              //create link
              $("<a>").text(val.searchTerm).attr({title:"See search results with " + val.searchTerm, href:"/Hasteer/Search.do?q=" + val.searchTerm}).appendTo(li);
              //set tag size
              li.children().css("fontSize", (val.count < 10 ? "1.5em" : val.count < 50 ? "2em" : val.count < 100 ? "2.5em" : val.count < 200 ? "3em" : "3.5em"));
              //add to list
              li.appendTo("#tagList");
            });
        });

        // set timer to run every 5 secs by default
        setInterval("updateAllProgressBars()", <%=regularDealsUpdateFreq%>);
    });

    // executed once, get all deals displayed on home page
    var deals = $.parseJSON('<%= jsonResult.toString() %>');

    function updateAllProgressBars() {
        $.each(deals, function(i, val) {
            $.ajax({
                type: 'GET',
                url: '/Hasteer/ajax/ProgressBarUpdater.jsp',
                data: 'dealId=' + val.dealId + "&rp=" + val.rp,
                beforeSend: function() {
                    $('#progress-' + val.dealId).css("background-color", "#FFA9A9");
                },
                success: function(data)
                {
                    $('#progress-' + val.dealId).css("background-color", "");
                    $('#progress-' + val.dealId).empty().append(data);
                }
            });
        });
    }
</script>