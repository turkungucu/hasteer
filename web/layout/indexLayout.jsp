<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.util.AuthUtil"%>
<%@page import="com.api.Jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
    String FACEBOOK_APP_ID = AuthUtil.getFacebookAppId(request);
%>

<tiles:importAttribute />

<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:fb="http://www.facebook.com/2008/fbml">
    <head>
        <title>Hasteer: <tiles:getAsString name="browser-title" ignore="true" /></title>
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
        <script type="text/javascript" src="/Hasteer/js/jquery.anythingslider.js"></script>
        <script type="text/javascript" src="/Hasteer/js/jquery.countdown.min.js"></script>
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
                <tiles:insert attribute="main-body" ignore="true" />
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
    FB.init({appId: '<%=FACEBOOK_APP_ID%>', status: true, cookie: true, xfbml: true});
</script>