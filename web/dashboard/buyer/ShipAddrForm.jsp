<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List"%>
<%@page import="com.dao.User"%>
<%@page import="com.dao.ShippingAddress"%>
<%@page import="com.struts.form.ManageShipAddrsForm"%>
<%@page import="org.apache.struts.Globals"%>

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

<html:form action="/dashboard/buyer/ManageShippingAddresses.do" method="post">
    <html:hidden property="cmd" styleId="cmd" />
    <html:hidden property="deleteId" styleId="deleteId" />

    <c:if test="${ManageShipAddrsForm.addresses ne null and !empty ManageShipAddrsForm.addresses}">
        <p>Use an existing shipping address or <a href="#" onclick="showAddrForm()">add a new shipping address</a>:</p>
        <table width="100%" cellpadding="3" cellspacing="0">
            <c:forEach var="addr" items="${ManageShipAddrsForm.addresses}" varStatus="status">
                <tr bgcolor="${status.count % 2 == 0 ? "#f6f6f6" : "#eeeeee"}" valign="top">
                    <td width="1px;">
                        <html:radio property="wAddrId" styleId="existingAddresses"
                                    value="${addr.shippingAddressId}" onclick="hideAddrForm()" /></td>
                    <td height="30px;" style="white-space: nowrap;width: 1px;">
                        ${addr.recipient}
                    </td>
                    <td style="padding-left: 20px;">
                        <small>${addr.address}</small>
                    </td>
                    <td align="right" style="padding-right: 5px;white-space: nowrap;width: 1px;">
                        <small><a href="javascript:deleteAddress('${addr.shippingAddressId}')">Delete</a></small>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <%
        boolean displayAddrForm = false;
        ManageShipAddrsForm form = (ManageShipAddrsForm)request.getAttribute("ManageShipAddrsForm");
        displayAddrForm = (form.getAddress1() != null && form.getwAddrId() == 0) || (form.getAddresses() == null || form.getAddresses().isEmpty());
    %>
    <div id="formDiv" style="<%= !displayAddrForm ? "display:none;" : "" %>padding-top:20px;">
    <table width="100%" cellspacing="5">
        <tr>
            <td id="label">Company:</td>
            <td>
                <html:text property="company" styleId="company" />
                <span class="small-text-silver">Optional.</span>
            </td>
        </tr>
        <tr>
            <td id="label" width="20%">First Name:</td>
            <td>
                <html:text property="firstName" size="16" styleId="firstName" errorStyleClass="error" />
            </td>
        </tr>
        <tr>
            <td id="label">Last Name:</td>
            <td>
                <html:text property="lastName" size="16" styleId="lastName" errorStyleClass="error" />
            </td>
        </tr>
        <tr>
            <td id="label">Address 1:</td>
            <td>
                <html:text property="address1" size="20" styleId="address1" errorStyleClass="error" />
                <span class="small-text-silver">Street address, P.O. box, company name, c/o.</span>
            </td>
        </tr>
        <tr>
            <td id="label">Address 2:</td>
            <td>
                <html:text property="address2" size="20" styleId="address2" />
                <span class="small-text-silver">Apartment, suite, unit, building, floor, etc.</span>
            </td>
        </tr>
        <tr>
            <td id="label">City:</td>
            <td>
                <html:text property="city" size="20" styleId="city" errorStyleClass="error" />
            </td>
        </tr>
        <tr>
            <td id="label">State:</td>
            <td>
                <html:select property="state" styleId="state" errorStyleClass="error">
                    <html:option value="">Select</html:option>
                    <html:optionsCollection property="states" />
                </html:select>
            </td>
        </tr>
        <tr>
            <td id="label">Zip Code:</td>
            <td>
                <html:text property="zipCode" size="5" styleId="zipCode" errorStyleClass="error" />
            </td>
        </tr>
        <tr>
            <td id="label">Country:</td>
            <td>
                <html:select property="country" styleId="country" errorStyleClass="error">
                    <html:option value="US">United States</html:option>
                </html:select>
            </td>
        </tr>
        <tr>
            <td id="label">Contact Number:</td>
            <td>
                (<html:text property="phone1" styleId="phone1" size="3" maxlength="3" />) -
                <html:text property="phone2" styleId="phone2" size="3" maxlength="3" /> -
                <html:text property="phone3" styleId="phone3" size="4" maxlength="4" />
                <span class="small-text-silver">Optional. We will only contact you if there is a problem with your shipment.</span>
            </td>
        </tr>
        <tr>
            <td align="right"><html:checkbox property="doNotSave" /></td>
            <td>
                <strong>Do not save my shipping address for future use.</strong>
                <a id="tt">
                    It is recommended that you allow us to save your data so
                    you can benefit from Quick Join&#153; : One-click checkout
                    that automatically selects your most recently used Payment
                    Method and Shipping Address.
                </a>
            </td>
        </tr>
    </table>
    </div>
    <p align="right">
        <html:button property="cmd" value="Previous" styleClass="dark-gray-button"
                     onclick="fnSubmit(this.form, 'previous');"/>
        <html:button property="cmd" value="Next" styleClass="red-button"
                     onclick="fnSubmit(this.form, 'next');"/>
    </p>
  
</html:form>
<script type="text/javascript">
    function showAddrForm() {
        clearAddrForm();
        var formDiv = document.getElementById('formDiv');
        formDiv.style.display = 'block';

        $("input#existingAddresses").each(function () {
            $(this).attr('checked', false);
        });
    }

    function hideAddrForm() {
        var formDiv = document.getElementById('formDiv');
        formDiv.style.display = 'none';
    }

    function clearAddrForm(){
        document.getElementById('company').value = "";
        document.getElementById('firstName').value = "";
        document.getElementById('lastName').value = "";
        document.getElementById('address1').value = "";
        document.getElementById('address2').value = "";
        document.getElementById('city').value = "";
        clearDropDown('state');
        document.getElementById('zipCode').value = "";
        document.getElementById('country').value = "";
        document.getElementById('phone1').value = "";
        document.getElementById('phone2').value = "";
        document.getElementById('phone3').value = "";
    }

    function fnSubmit(form, cmd) {
        document.getElementById("cmd").value = cmd;
        form.submit();
    }

    function deleteAddress(addrId) {
        var ok = confirm('Are you sure to delete this address?');
        if(ok) {
            document.getElementById("deleteId").value = addrId;
            fnSubmit(document.ManageShipAddrsForm, 'delete');
        }
    }
    
</script>