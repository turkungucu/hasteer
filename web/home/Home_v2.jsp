<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
List<DealItem> pastDeals = Deal.getPastDeals();
List<DealItem> regularDeals = Deal.getRegularDeals();

pageContext.setAttribute("regularDeals", regularDeals);
pageContext.setAttribute("pastDeals", pastDeals);

JSONArray jsonResult = new JSONArray();
for (DealItem regularDeal : regularDeals) {
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("dealId", regularDeal.getDealId());
    jsonObj.put("rp", regularDeal.getRequiredParticipants());
    jsonResult.add(jsonObj);
}

long regularDealsUpdateFreq = ConfigUtil.getLong(Jsp.getProperty(pageContext, "regularDeals.updateFrequency"), 5000);

Cookie[] cookies = request.getCookies();

boolean showHowItWorks = true;
if (cookies != null) {
    for(Cookie c : cookies) {
        if(c.getName().startsWith("hiw")) {
            showHowItWorks = Boolean.parseBoolean(c.getValue());
        }
    }
}
String toggleText = showHowItWorks ? "Hide on next visit" : "Show on next visit";
%>

<table align="center" border="0" width="800px" cellpadding="0" cellspacing="40px">
    <tr>
        <td colspan="2" style="padding-left: 40px;padding-right: 40px;">
            <span class="page-title">How It Works</span> [<a href="#" id="hiw-toggle"><%=toggleText%></a>]<br/>
            <table cellpadding="0" cellspacing="0" style="<%=!showHowItWorks ? "display:none;" : "" %>" id="how-it-works">
                <tr>
                    <td class="hiw-number" style="background-color: #43C6DB;">1</td>
                    <td class="hiw-header"><span class="blue">get</span><span class="white">together</span></td>
                    <td class="hiw-number" style="background-color: #cc0000;">2</td>
                    <td class="hiw-header"><span class="red">buy</span><span class="white">together</span></td>
                    <td class="hiw-number" style="background-color: green;">3</td>
                    <td class="hiw-header"><span class="green">save</span><span class="white">together</span></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="hiw-body">
                        <p class="blue">Hasteer.com utilizes group buying power to bring you discounts on consumer products.</p>
                        <p class="blue">The idea is simple. More people join a deal, cheaper the price gets.</p>
                    </td>
                    <td></td>
                    <td class="hiw-body">
                        <p class="red">Sharing is giving! Remember to invite your friends and family to participate in deals.</p>
                        <p class="red">You will be rewarded with referral points that can be redeemed towards purchases.</p>
                    </td>
                    <td></td>
                    <td class="hiw-body">
                        <p class="green">When a deal meets its quota, all participants get the item at the discounted price.</p>
                        <p class="green">Hasteer.com is the place to get together and save together! <a href="/Hasteer/common/faq.do">Learn More.</a></p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    
    <!-- PAST DEALS -->
    <c:if test="${fn:length(pastDeals) > 0}">
        <tr>
            <td valign="top" style="padding-left: 40px;">
                <span class="page-title">Past Deals</span><br/>
                <div class="pastdeals-container">
                    <div class="wrapper" style="overflow: hidden;">
                        <ul>
                            <c:forEach items="${pastDeals}" var="pastDeal">
                                <li>
                                    <div class="dealbox-bg-big">
                                        <div style="height:15%;background-color:#eeeeee;border-bottom:1px dotted #cccccc;">
                                            <div style="float:left;padding-left:5px;padding-top:5px;">
                                                <span class="product-title">${pastDeal.productName}&nbsp;</span><br/>
                                                by ${pastDeal.soldBy}
                                            </div>
                                            <div style="float:right;padding-left:5px;padding-top:5px;padding-right:5px;">
                                                <span class="product-title">
                                                    Deal Ended On
                                                </span><br/>
                                                <span style="font-size:14px;">${pastDeal.endDate}</span>
                                            </div>
                                        </div>
                                        <div style="height:85%;">
                                            <div style="float:left;width:63%;">
                                                <p id="product-container">
                                                    <html-el:link action="/product/ProductDetails.do?dealId=${pastDeal.dealId}" styleId="img">
                                                        <img src="${pastDeal.imgUrl}" alt="${pastDeal.productName}" />
                                                    </html-el:link><br/>
                                                </p>
                                            </div>
                                            <div style="float:left;width:37%;height:100%;position:relative;">
                                                <div style="position: absolute;padding-top: 40px;">
                                                    <div style="height:45px;">
                                                        <div class="label-container">
                                                            <span class="small-text">&nbsp;Buyers<br/>&nbsp;Joined</span>
                                                        </div>
                                                        <div style="float:left;">
                                                            <span class="dealbox-numbers">${pastDeal.currentParticipants}</span>
                                                        </div>
                                                    </div>
                                                    <div style="height:45px;">
                                                        <div class="label-container">
                                                            <span class="small-text">&nbsp;Deal<br/>&nbsp;Price</span>
                                                        </div>
                                                        <div style="float:left;color: #cc0000;">
                                                            <span class="dealbox-numbers">$${pastDeal.dealPrice}</span>
                                                        </div>
                                                    </div>
                                                    <div style="height:45px;">
                                                        <div class="label-container">
                                                            <span class="small-text">&nbsp;Retail<br/>&nbsp;Price</span>
                                                        </div>
                                                        <div style="float:left;">
                                                            <span class="dealbox-numbers"><s>$${pastDeal.originalPrice}</s></span>
                                                        </div>
                                                    </div>
                                                    <div style="height:45px;border-top:1px dotted #666666;">
                                                        <div class="label-container">
                                                            <span class="small-text">&nbsp;Total<br/>&nbsp;Savings</span>
                                                        </div>
                                                        <div style="float:left;color: green;">
                                                            <span class="dealbox-numbers">${pastDeal.percentSavings}</span>
                                                        </div>
                                                    </div>
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
                <fb:activity width="378" height="302" font="arial" border_color="#e5e5e5" recommendations="true" header="false"></fb:activity>
            </td>
        </tr>
    </c:if>
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
                                        <img src="${regularDeal.thumbnailUrl}" align="center" width="145px" height="120px" class="product-image"/>
                                    </html-el:link>
                                </div>

                                <div style="height: 25px;width: 145px;">
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
                                <div style="width: 145px;padding-bottom: 10px;" id="progress-${regularDeal.dealId}">
                                    <c:set var="w" value="${(regularDeal.currentParticipants*145)/regularDeal.requiredParticipants}" />
                                    <div id="progressbar" class="ui-progressbar ui-widget ui-widget-content ui-corner-all"
                                         style="height: 10px;" role="progressbar" aria-valuemin="0"
                                         aria-valuemax="${regularDeal.requiredParticipants}"
                                         aria-valuenow="${regularDeal.currentParticipants}">
                                        <div class="ui-progressbar-value ui-progressbar-header ui-corner-left" style="width: ${w}px;"></div>
                                    </div>
                                    <table cellpadding="0" cellspacing="0" width="100%" style="font-size: 11px;padding-top: 2px;">
                                        <tr valign="top">
                                            <td width="50%" id="cp-${regularDeal.dealId}"><b>${regularDeal.currentParticipants}</b><br/>JOINED</td>
                                            <td><b>${regularDeal.requiredParticipants}</b><br/>REQUIRED</td>
                                        </tr>
                                    </table>
                                </div>
                                <div style="border-top: 1px solid #e5e5e5;padding-top: 3px;">
                                    Time Remaining:<br/>
                                    <span id="ctr-${regularDeal.dealId}"></span>
                                    <script type="text/javascript">
                                        $('#ctr-<c:out value="${regularDeal.dealId}"/>').countdown({until: '<c:out value="${regularDeal.counterDate}"></c:out>', format: 'dHM'});
                                    </script>
                                </div>
                                <input type="button" value="Join Deal" class="turq-button" style="margin-top:10px;"
                                       onclick="javascript:window.location='/Hasteer/deals/JoinDeal.do?dealId=${regularDeal.dealId}'">
                            </div>
                        </td>
                    </c:forEach>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td valign="top" colspan="2" style="padding: 0px 40px 5%">
            <div style="float:left;" id="hype">
                <span class="page-title" style="text-align: left;">The Hype</span><br/>
                <div class="tagcloud-container">
                    <div id="tagCloud" class="wrapper" style="overflow: hidden;">

                    </div>
                </div>
            </div>
            <div style="float:right;width: 378px;">
                <span class="page-title" style="text-align: left;">Weekly Deals</span><br/>
                <div class="page-container-white" id="newsletter-container" style="text-align: left;">
                    If you'd like Weekly Deals delivered to your inbox, please enter your email address in the field below:
                    <p>
                        <input type="text" id="subscriptionEmail" size="30" style="height: 19px;"/>&nbsp;
                        <input type="button" value="Subscribe" class="red-button" style="padding-top: 2px;"
                               onclick="updateSubscriptionStatus('subscriptionEmail', 'newsletter-container', 'add')" />
                    </p>
                </div>
            </div>
        </td>
    </tr>
</table>

<script type="text/javascript">
    $(document).ready(function(){
        $('.pastdeals-container').anythingSlider({
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

        $('.pastdeals-container').data('AnythingSlider').startStop(true);  // start the slideshow

        $.getJSON("/Hasteer/ajax/TagCloudService.jsp", function(data) {
            if($.isEmptyObject(data)) {
                $("#hype").hide();
            } else {
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
            }
        });

        // set timer to run every 5 secs by default
        setInterval("updateAllProgressBars()", <%=regularDealsUpdateFreq%>);

        $("#hiw-toggle").click(function () {
            $("#how-it-works").toggle();
            var text = $(this).html();
            if (text.substring(0, 4) == "Show") {
                $(this).html("Hide on next visit");
                $.cookie('hiw','true',{expires: 365});
            } else {
                $(this).html("Show on next visit");
                $.cookie('hiw','false',{expires: 365});
            }
        });
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
                    $('#cp-' + val.dealId).css("background-color", "#FFA9A9");
                },
                success: function(data)
                {
                    $('#cp-' + val.dealId).css("background-color", "");
                    $('#progress-' + val.dealId).empty().append(data);
                }
            });
        });
    }
</script>