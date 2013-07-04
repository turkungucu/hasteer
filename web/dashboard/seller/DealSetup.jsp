<%-- 
    Document   : DealSetup
    Created on : Jan 31, 2010, 11:49:29 AM
    Author     : ecolak
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="com.dao.Deal, com.dao.DealPricingOption, com.dao.Product, 
                com.constants.HasteerConstants, com.dao.User,
                com.struts.form.DealSetupForm, com.api.Jsp, org.apache.commons.lang.StringUtils"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html; charset=UTF-8" %>

<script type="text/javascript">
    function submitForm(cmd) {
        setCmd(cmd);
        document.DealSetupForm.submit();
    }

    function setCmd(cmd) {
        document.DealSetupForm.cmd.value = cmd;
    }

    function deleteImage(imageId) {
        var ok = confirm('Are you sure to delete this image?');
        if(ok) {
            setCmd("deleteImage");
            document.DealSetupForm.dealId.value = imageId;
            document.DealSetupForm.submit();
        }
    }

    function showOneMoreDiv() {
        var row2 = document.getElementById("row2");
        var row3 = document.getElementById("row3");
        var row4 = document.getElementById("row4");
        var row5 = document.getElementById("row5");

        var nextDiv;
        if(row2.style.display == 'none')
            nextDiv = row2;
        else if(row3.style.display == 'none')
            nextDiv = row3;
        else if(row4.style.display == 'none')
            nextDiv = row4;
        else if(row5.style.display == 'none')
            nextDiv = row5;

        if(nextDiv) {
            nextDiv.style.display = '';
        }
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
<%
    DealSetupForm form = (DealSetupForm)request.getAttribute("DealSetupForm");
%>

<html:form method="post" action="/dashboard/seller/DealSetup.do">
    <table cellspacing="5px">
        <tr>
            <td id="label" width="25%">Deal Name:</td>
            <td>
                <html:text property="dealName" size="50" />
                <span class="small-text-silver">
                    For your record keeping.
                </span>
            </td>
        </tr>
        <tr>
            <td id="label">
                Product:
            </td>
            <td>
                <html:select property="productId">
                    <%
                    List<Product> products = Product.getBySellerId(User.getUserFromSession(request).getUserId());
                    for (Product p : products) {
                    %>
                        <option value="<%= p.getProductId()%>" <%= form.getProductId() == p.getProductId() ? "selected" : ""%> >
                            <%= p.getProductName()%>
                        </option>
                    <% }%>
                </html:select>
            </td>
        </tr>
        <tr>
            <td id="label">
                Shipping Policy:
            </td>
            <td>
                <html:select property="shippingPolicyId">
                    <html:option value="">Select</html:option>
                    <html:optionsCollection property="shippingPolicies" label="description" value="merchantPolicyId" />
                </html:select>
            </td>
        </tr>
        <tr>
            <td id="label">
                Return Policy:
            </td>
            <td>
                <html:select property="returnPolicyId">
                    <html:option value="">Select</html:option>
                    <html:optionsCollection property="returnPolicies" label="description" value="merchantPolicyId" />
                </html:select>
            </td>
        </tr>
        <tr>
            <td id="label">Start Date: </td>
            <td><html:text property="startDate" size="17" styleId="startdate" /></td>
        </tr>
        <tr>
            <td id="label">End Date:</td>
            <td><html:text property="endDate" size="17" styleId="enddate" /></td>
        </tr>
        <tr>
            <td id="label">Retail Price:</td>
            <td>
                $ <html:text property="retailPrice" size="4" />
                <span class="small-text-silver">
                    Pre-discount price of the product. It is used to calculate savings.
                </span>
            </td>
        </tr>
        <tr>
            <td id="label" valign="top">Pricing Options:</td>
            <td>
                <table>
                    <tr id="row1">
                        <td>Minimum Participants: </td>
                        <td>
                            <html:text property="minNum1" size="4" />
                            Base Price: <html:text property="price1" size="4" />
                            <a href="#" onclick="showOneMoreDiv()">Add more</a>
                        </td>
                    </tr>

                    <tr id="row2" style="<%= !StringUtils.isBlank(form.getMinNum2()) && !StringUtils.isBlank(form.getPrice2()) ? "" : "display:none" %>">
                        <td>Minimum Participants: </td>
                        <td><html:text property="minNum2" size="4" />
                            Base Price: <html:text property="price2" size="4" />
                            <a href="/Hasteer/dashboard/seller/DealSetup.do?cmd=delete&dealId=<%= form.getDealId()%>&doid=<%= form.getOid2()%>">Delete</a>
                        </td>
                    </tr>

                    <tr id="row3" style="<%= !StringUtils.isBlank(form.getMinNum3()) && !StringUtils.isBlank(form.getPrice3()) ? "" : "display:none" %>">
                        <td>Minimum Participants: </td>
                        <td><html:text property="minNum3" size="4" />
                            Base Price: <html:text property="price3" size="4" />
                            <a href="/Hasteer/dashboard/seller/DealSetup.do?cmd=delete&dealId=<%= form.getDealId()%>&doid=<%= form.getOid3()%>">Delete</a>
                        </td>
                    </tr>

                    <tr id="row4" style="<%= !StringUtils.isBlank(form.getMinNum4()) && !StringUtils.isBlank(form.getPrice4()) ? "" : "display:none" %>">
                        <td>Minimum Participants: </td>
                        <td><html:text property="minNum4" size="4" />
                            Base Price: <html:text property="price4" size="4" />
                            <a href="/Hasteer/dashboard/seller/DealSetup.do?cmd=delete&dealId=<%= form.getDealId()%>&doid=<%= form.getOid4()%>">Delete</a>
                        </td>
                    </tr>

                    <tr id="row5" style="<%= !StringUtils.isBlank(form.getMinNum5()) && !StringUtils.isBlank(form.getPrice5()) ? "" : "display:none" %>">
                        <td>Minimum Participants: </td>
                        <td><html:text property="minNum5" size="4" />
                            Base Price: <html:text property="price5" size="4" />
                            <a href="/Hasteer/dashboard/seller/DealSetup.do?cmd=delete&dealId=<%= form.getDealId()%>&doid=<%= form.getOid5()%>">Delete</a>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <html:hidden property="cmd"  />

    <p align="center">
        <logic:equal name="DealSetupForm" property="dealId" value="0">
            <html:button property="createButton" value="Create Deal" styleClass="red-button" onclick="submitForm('create')" />
        </logic:equal>
        <logic:greaterThan name="DealSetupForm" property="dealId" value="0">
            <html:hidden property="dealId" />
            <html:button property="updateButton" value="Update Deal" styleClass="red-button" onclick="submitForm('update')" />
        </logic:greaterThan>
        <html:button property="backButton" value="Back to Manage Deals" styleClass="dark-gray-button" onclick="submitForm('back')" />
    </p>
</html:form>


<script type="text/javascript">
    $(document).ready(function(){
        // Datepicker
        $('#startdate').datetimepicker({
            ampm: true,
            timeFormat: 'hh:mm TT'
        });

        $('#enddate').datetimepicker({
            ampm: true,
            timeFormat: 'hh:mm TT'
        });
    });
</script>