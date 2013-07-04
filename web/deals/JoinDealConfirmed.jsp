<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@page import="com.api.Jsp"%>
<%@page import="com.constants.HasteerConstants"%>

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
    <html:messages id="msg" message="true">
        <span style="color: #cc0000;" class="page-title">
            <bean:write name="msg" filter="false"/>
        </span>
    </html:messages>
            
    <h3>Next Steps:</h3>
    <ul>
        <li>You will shortly receive an email that summarizes your order.</li>
        <li>Visit <a href="<%= Jsp.getUrlBase() + HasteerConstants.MY_DEALS_PAGE %>">My Deals</a> page to track this deal. </li>
    </ul>

    <p style="padding-top: 10px;"> If you have any questions or concerns, please contact us at <a href="mailto:info@hasteer.com">info@hasteer.com</a>.</p>
</logic:messagesPresent>