<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.User"%>
<%@page import="com.dao.CreditCardDetail"%>
<%@page import="com.dao.CreditCardDetail.CreditCardType"%>
<%@page import="com.struts.form.CreditCardSetupForm"%>
<%@page import="org.apache.struts.Globals"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

<html:form action="/dashboard/CreditCardSetup.do" method="post">
    <html:hidden property="cmd" styleId="cmd" />
    <html:hidden property="deleteId" styleId="deleteId" />
  
    <c:if test="${CreditCardSetupForm.ccDetails ne null and !empty CreditCardSetupForm.ccDetails}">
        <p>Use an existing credit card below or <a href="#" onclick="showCcForm()">add a new credit card</a>:</p>
        <table width="100%" cellpadding="0" cellspacing="0">
            <c:forEach var="ccd" items="${CreditCardSetupForm.ccDetails}" varStatus="status">
                <tr bgcolor="${status.count % 2 == 0 ? "#f6f6f6" : "#eeeeee"}">
                    <td height="30px;">
                        <c:choose>
                            <c:when test="${ccd.expired}">
                                <input type="radio" disabled />
                                <span style="color:red;">[Expired]</span> <b>${ccd.cardType}</b>,
                                ${ccd.maskedCardNumber}, ${ccd.cardholderName},
                                <span style="color:red;">${ccd.formattedExpiryDate}</span>
                            </c:when>
                            <c:otherwise>
                                <html:radio property="wccId" value="${ccd.creditCardDetailsId}"
                                    onclick="hideCcForm()" styleId="existingCards" />
                                <b>${ccd.cardType}</b>, ${ccd.maskedCardNumber}, ${ccd.cardholderName}, ${ccd.formattedExpiryDate}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td align="right" style="padding-right: 5px;">
                        <small><a href="javascript:deleteCc('${ccd.creditCardDetailsId}')">Delete</a></small>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <%
        boolean displayCcForm = false;
        CreditCardSetupForm form = (CreditCardSetupForm)request.getAttribute("CreditCardSetupForm");
        // if address is entered and no radio button is selected OR no existing cards
        displayCcForm = (form.getAddress1() != null && form.getWccId() == 0) || (form.getCcDetails() == null || form.getCcDetails().isEmpty());
    %>
    <div id="formDiv" style="<%= !displayCcForm ? "display:none" : "" %>">
        <table cellspacing="5" width="100%">
            <tr>
                <td colspan="2">
                    <div class="headerNormalWideNoIcon">
                        Billing Address
                    </div>
                </td>
            </tr>
            <tr>
                <td id="label" width="20%">First Name:</td>
                <td><html:text property="firstName" size="25" styleId="firstName" errorStyleClass="error" /></td>
            </tr>
            <tr>
                <td id="label">Last Name:</td>
                <td><html:text property="lastName" size="25" styleId="lastName" errorStyleClass="error" /></td>
            </tr>
            <tr>
                <td id="label">Address 1:</td>
                <td>
                    <html:text property="address1" size="25" styleId="address1" errorStyleClass="error" />
                    <span class="small-text-silver">Street address, P.O. box, company name, c/o.</span>
                </td>
            </tr>
            <tr>
                <td id="label">Address 2:</td>
                <td>
                    <html:text property="address2" size="25" styleId="address2" errorStyleClass="error" />
                    <span class="small-text-silver">Apartment, suite, unit, building, floor, etc.</span>
                </td>
            </tr>
            <tr>
                <td id="label">City:</td>
                <td><html:text property="city" size="25" styleId="city" errorStyleClass="error" /></td>
            </tr>
            <tr>
                <td id="label">State:</td>
                <td>
                    <html:select property="state" styleId="state" errorStyleClass="error" >
                        <html:option value="">Select</html:option>
                        <html:optionsCollection property="states" />
                    </html:select>
                </td>
            </tr>
            <tr>
                <td id="label">Zip Code:</td>
                <td><html:text property="zipCode" size="5" styleId="zipCode" errorStyleClass="error" /></td>
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
                <td align="right"><html:checkbox property="sameAsShippingAddr" /></td>
                <td><strong>This is also my Shipping Address.</strong></td>
            </tr>
            <tr>
                <td colspan="2">
                    <div class="headerNormalWideNoIcon">
                        Credit Card Details
                    </div>
                </td>
            </tr>
            <tr>
                <td id="label">Cardholder's Name:</td>
                <td nowrap>
                    <html:text property="cardholderName" size="25" styleId="cardholderName" errorStyleClass="error" />
                    <span class="small-text-silver">As it appears on the credit card.</span>
                </td>
            </tr>
            <tr>
                <td id="label">Card Type:</td>
                <td nowrap>
                    <html:select property="cardType" styleId="cardType" errorStyleClass="error">
                        <html:optionsCollection property="cardTypes" />
                    </html:select>
                    <img src="/Hasteer/images/creditcards/credit_card_logos.gif" align="absmiddle"
                         alt="We accept Visa, MasterCard, American Express, and Discover" />
                </td>
            </tr>
            <tr>
                <td id="label">Credit Card Number:</td>
                <td>
                    <html:text property="creditCardNumber" size="25" maxlength="16" styleId="creditCardNumber" errorStyleClass="error" />
                    <span class="small-text-silver">Please enter numbers only, ex: 4111111111111111.</span>
                </td>
            </tr>
            <tr>
                <td id="label">Expiration Date:</td>
                <td>
                    <html:select property="expMonth" styleId="expMonth" errorStyleClass="error">
                        <html:option value="01" />
                        <html:option value="02" />
                        <html:option value="03" />
                        <html:option value="04" />
                        <html:option value="05" />
                        <html:option value="06" />
                        <html:option value="07" />
                        <html:option value="08" />
                        <html:option value="09" />
                        <html:option value="10" />
                        <html:option value="11" />
                        <html:option value="12" />
                    </html:select>
                    <html:select property="expYear" styleId="expYear" errorStyleClass="error">
                        <html:option value="2011" />
                        <html:option value="2012" />
                        <html:option value="2013" />
                        <html:option value="2014" />
                        <html:option value="2015" />
                        <html:option value="2016" />
                        <html:option value="2017" />
                    </html:select>
                </td>
            </tr>

            <tr>
                <td id="label">CVV:</td>
                <td>
                    <html:text property="cvv" size="4" maxlength="4" styleId="cvv" errorStyleClass="error" />
                    <span class="small-text-silver">3 or 4 digit security code on your card.</span>
                </td>
            </tr>
            <tr>
                <td align="right"><html:checkbox property="doNotSave" /></td>
                <td>
                    <strong>Do not save my payment method for future use.</strong>
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
    <html:button value="Previous" styleClass="dark-gray-button" property="cmd"
                     onclick="fnSubmit(this.form, 'previous');"/>
    <html:button value="Next" styleClass="red-button" property="cmd"
                     onclick="fnSubmit(this.form, 'next');"/>
    </p>
</html:form>

<script type="text/javascript">
    function showCcForm() {
        clearCcForm();
        var formDiv = document.getElementById('formDiv');
        formDiv.style.display = 'block';

        $("input#existingCards").each(function () {
            $(this).attr('checked', false);
        });
    }

    function hideCcForm() {
        var formDiv = document.getElementById('formDiv');
        formDiv.style.display = 'none';
    }
    function clearCcForm(){
        document.getElementById('firstName').value = "";
        document.getElementById('lastName').value = "";
        document.getElementById('address1').value = "";
        document.getElementById('address2').value = "";
        document.getElementById('city').value = "";
        clearDropDown('state');
        document.getElementById('zipCode').value = "";
        document.getElementById('country').value = "";
        document.getElementById('cardholderName').value = "";
        clearDropDown('cardType');
        document.getElementById('creditCardNumber').value = "";
        clearDropDown('expMonth');
        clearDropDown('expYear');
    }
    
    function fnSubmit(form, cmd) {
        document.getElementById("cmd").value = cmd;
        form.submit();
    }

    function deleteCc(ccId) {
        var ok = confirm('Are you sure to delete this credit card?');
        if(ok) {
            document.getElementById("deleteId").value = ccId;
            fnSubmit(document.CreditCardSetupForm, 'delete');
        }
    }
</script>