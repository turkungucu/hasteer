<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.User,
                com.dao.ProductImage,
                com.dao.Product,
                com.dao.DealPricingOption,
                com.api.Jsp,
                com.struts.form.ProductDetailsForm,
                com.util.ConfigUtil,
                com.util.AuthUtil,
                org.apache.commons.lang.StringEscapeUtils"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
ProductDetailsForm form = (ProductDetailsForm)request.getAttribute("ProductDetailsForm");

String outboundRefId = null;
User loggedInUser = AuthUtil.getLoggedInUser(request);
if (loggedInUser != null) {
    outboundRefId = loggedInUser.getUserId() + "|" + form.getDealId();
} else {
    outboundRefId = 0 + "|" + form.getDealId();
}
outboundRefId = AuthUtil.encodeString(outboundRefId);
request.setAttribute("refId", outboundRefId);

// To populate meta tag for Open Graph Protocol
DealPricingOption dpo = DealPricingOption.getLowestPriceOption(form.getDealId());
Product product = form.getProduct();
String PRODUCT_NAME = product.getProductName();
String BEST_DEAL = dpo.getNumOfParticipantsRemaining() + " more buyers needed to reach $" + dpo.getFormattedOptionPrice() + ".";

String FACEBOOK_APP_ID = AuthUtil.getFacebookAppId(request);
%>

<tiles:importAttribute />

<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:fb="http://www.facebook.com/2008/fbml">
    <head>
        <title><%=PRODUCT_NAME%>: <%=BEST_DEAL%></title>
        <meta property="og:title" content="<%=StringEscapeUtils.escapeJavaScript(PRODUCT_NAME)%>: <%=BEST_DEAL%>"/>
        <meta property="og:site_name" content="Hasteer"/>
        <link rel="stylesheet" href="/Hasteer/css/tabs.css"/>
        <logic:notEmpty name="css">
                <style type="text/css">
                /* <![CDATA[ */
                        <tiles:insert attribute="css" ignore="true" />
                /* ]]> */
                </style>
        </logic:notEmpty>
        <logic:notEmpty name="javascript">
                <script type="text/javascript" src="<tiles:getAsString name="javascript" ignore="true" />"></script>
        </logic:notEmpty>
        <script type="text/javascript" src="/Hasteer/js/jquery.idTabs.min.js"></script>
        <script type="text/javascript" src="/Hasteer/js/jquery.cookie.js"></script>
        <script type="text/javascript" src="/Hasteer/js/jquery.countdown.min.js"></script>
        <script type="text/javascript" src="http://s7.addthis.com/js/250/addthis_widget.js#username=alinurg"></script>
        <link rel="icon" href="<%=Jsp.getFavIconUrl()%>" type="image/x-icon" />
        <link rel="shortcut icon" href="<%=Jsp.getFavIconUrl()%>" type="image/x-icon" />
    </head>
    <body>
        <html:link action="/Home.do"><img src="/Hasteer/images/1x1.png" class="logo" alt="hasteer" border="0" /></html:link>
        <div id="outer-container">
            <div id="header-container">
                <div class="header-upper-container">
                    <tiles:insert attribute="header-upper" />
                </div>
                <div class="header-lower-outer-container">
                    <div class="header-lower-inner-container">
                        <tiles:insert attribute="header" />
                    </div>
                </div>
            </div><p/>
            <div id="content-container">
                <div class="main-body">
                    <div class="page-container-white"> 
                        <table cellpadding="0" cellspacing="0" width="100%" border="0">
                            <tr>
                                <td width="1">
                                    <span class="product-title" style="white-space: nowrap;">
                                        <%=PRODUCT_NAME%>
                                    </span>&nbsp;&nbsp;&nbsp;
                                </td>
                                <td>
                                    <fb:like layout="button_count" show_faces="false" font="arial"
                                             ref="<%=outboundRefId%>" />
                                </td>
                            </tr>
                        </table>
                        <p/>                      
                        <tiles:insert attribute="main-body" ignore="true">
                            <tiles:put beanName="refId" beanScope="tile" name="refId"/>
                        </tiles:insert>
                    </div>
                </div>
            </div>
            <div id="footer-container">
                <div class="footer">
                <br/>
                    <tiles:insert attribute="footer" />
                </div>
            </div>
        </div>
    </body>
</html>

<div id="fb-root"></div>

<script type='text/javascript' src="http://connect.facebook.net/en_US/all.js"></script>

<script type="text/javascript">
    setTooltips();
    var addthis_config = {
        services_compact: 'email, facebook, twitter, delicious, more',
        data_track_clickback: true
    }

    var addthis_share = {
        templates: { twitter: '{{title}} Check it out at {{url}} (from www.hasteer.com)' },
        email_template: "hasteer_template",
        email_vars: {
            productName: "<%=StringEscapeUtils.escapeJavaScript(PRODUCT_NAME)%>",
            bestDeal: "<%=BEST_DEAL%>"
        }
    }
    FB.init({appId: '<%=FACEBOOK_APP_ID%>', status: true, cookie: true, xfbml: true});
</script>