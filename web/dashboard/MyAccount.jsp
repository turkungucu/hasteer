<%@ taglib uri="/WEB-INF/taglib/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglib/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/taglib/struts-logic.tld" prefix="logic" %>
<%@ page import="com.dao.User" %>
<%@ page import="com.dao.User.UserType" %>
<%
    User user = User.getUserFromSession(request);
    boolean isSeller = (user.getType() == UserType.SELLER.getValue());
%>
<br/>
<table cellpadding="25" cellspacing="0" align="center" border="0">
    <tr>
        <td>
            <span class="myaccount-box-title">Account Settings</span><br/>
            <div class="myaccount-box">
                <p><html:link action="/dashboard/ChangeUsernameEmail.do">Change Username & Email</html:link></p>
                <p><html:link action="/dashboard/ChangePassword.do">Change Password</html:link></p>
            </div>
        </td>
        <td>
            <span class="myaccount-box-title">Deals</span><br/>
            <div class="myaccount-box">
                <% if (isSeller) { %>
                    <p><html:link action="/dashboard/seller/ManageDeals.do">Manage Deals</html:link></p>
                    <p><html:link action="/dashboard/seller/MyProducts.do">My Products</html:link></p>
                <% } else { %>
                    <p><html:link action="/dashboard/buyer/MyDeals.do">My Deals</html:link></p>
                <% } %>
                <p><html:link action="/dashboard/RewardsSummary.do">Referral Rewards Summary</html:link></p>
            </div>
        </td>
    </tr>
</table>