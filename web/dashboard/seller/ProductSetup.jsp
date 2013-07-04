<%--
    Document   : ProductSetup
    Created on : Jan 9, 2010, 3:06:06 PM
    Author     : ecolak
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el" %>
<%@page import="com.dao.Deal, com.dao.ProductCategory, com.dao.ProductImage, com.constants.HasteerConstants,
                com.struts.form.ProductSetupForm, com.api.Jsp"
%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html; charset=UTF-8" %>

<script type="text/javascript">
    function submitForm(cmd) {
        setCmd(cmd);
        document.ProductSetupForm.submit();
    }

    function setCmd(cmd) {
        document.ProductSetupForm.cmd.value = cmd;
    }

    function deleteImage(imageId) {
        var ok = confirm('Are you sure to delete this image?');
        if(ok) {
            setCmd("deleteImage");
            document.ProductSetupForm.did.value = imageId;
            document.ProductSetupForm.submit();
        }
    }

    function showOneMoreDiv() {
        var div2 = document.getElementById("image2Div");
        var div3 = document.getElementById("image3Div");
        var div4 = document.getElementById("image4Div");
        var div5 = document.getElementById("image5Div");

        var nextDiv;
        if(div2.style.display == 'none')
            nextDiv = div2;
        else if(div3.style.display == 'none')
            nextDiv = div3;
        else if(div4.style.display == 'none')
            nextDiv = div4;
        else if(div5.style.display == 'none')
            nextDiv = div5;

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

<html:form method="post" action="/dashboard/seller/ProductSetup.do" enctype="multipart/form-data">
    <table cellspacing="5">
        <tr>
            <td id="label" valign="top" width="25%">Product Name: <br/>
                <span class="small-text-silver">(Be descriptive: Philips 3000 Series 32" 720p 60Hz Flat Panel LCD TV)</span>
            </td>
            <td valign="top">
                <html:text property="productName" size="80" styleId="productName" />
                <br>
                <span class="small-text-red">(<span id="productNameCount"></span> chars remaining)</span>
            </td>
        </tr>
        <tr>
            <td id="label" valign="top">Category: </td>
            <td valign="top">
                <html:select property="categoryId">
                    <html:option value="0">-------------</html:option>
                    <% request.setAttribute("categoryCollection", ProductCategory.getAll()); %>
                    <html:options collection="categoryCollection" property="categoryId" labelProperty="categoryName" />
                </html:select>
            </td>
        </tr>
        <tr>
            <td id="label" valign="top">Details: <br/>
                <span class="small-text-silver">(Enter Product Description and Technical Specs)</span>
            </td>
            <td valign="top">
                <html:textarea property="details" cols="60" rows="8" styleId="details" />
                <br>
                <span class="small-text-red">(<span id="detailsCount"></span> chars remaining)</span>
                <span class="small-text-green">
                    &nbsp;*HTML tags &lt;p&gt;, &lt;br&gt;, and &lt;li&gt; are allowed for proper formatting.
                </span>
            </td>
        </tr>
        <tr>
            <td id="label" valign="top">Weight:</td>
            <td valign="top"><html:text property="weight" size="2" styleId="blankIfZero" /> lbs</td>
        </tr>
        <tr>
            <td id="label" valign="top">Dimensions: <br/>
                <span class="small-text-silver">(Length is the longest dimension)</span>
            </td>
            <td valign="top">
                <table>
                    <tr>
                        <td>Length: <br/><html:text property="length" size="1" styleId="blankIfZero" /> X </td>
                        <td>Height: <br/><html:text property="height" size="1" styleId="blankIfZero" /> X </td>
                        <td>Width: <br/><html:text property="width" size="1" styleId="blankIfZero" /> inches</td>
                    </tr>
                </table>
            </td>
        </tr>
        <logic:greaterThan name="ProductSetupForm" property="productId" value="0">
            <tr>
                <td id="label" valign="top">Existing Images:</td>
                <td valign="top">
                    <table border="1" style="border:1px;">
                        <tr>
                            <% int i = 1; %>
                            <logic:iterate id="im" name="ProductSetupForm" property="images">
                                <%
                                    ProductImage image = (ProductImage)pageContext.getAttribute("im");
                                    //image.resizeImage(HasteerConstants.MAX_WIDTH_FOR_THUMBNAIL, HasteerConstants.MAX_HEIGHT_FOR_THUMBNAIL);
                                    if(i % HasteerConstants.PRODUCT_SETUP_IMAGES_PER_ROW == 1)
                                        out.println("<tr>");
                                %>
                                <td align="center">
                                    <img src="<%= image.getThumbnailUrl() %>" /><br/>
                                    <a href="#" onclick="window.open('<%= image.getImageUrl() %>',
                                        'imagewindow',
                                        'scrollbars=yes,resizable=yes,top=200,left=200')">
                                        Expand
                                    </a>
                                    &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
                                    <a href="#" onclick="deleteImage('<%= image.getImageId() %>')">Delete</a>
                                </td>
                                <% i++; %>
                            </logic:iterate>
                        </tr>
                    </table>
                </td>
            </tr>
        </logic:greaterThan>
        <tr>
            <td id="label" valign="top">Images: <br/>
                <span class="small-text-silver">(only jpeg, gif or png)</span>
            </td>
            <td valign="top">
                <div id="image1Div">
                    <html:file property="image1" size="50" />
                    <html:radio property="primaryImageNumber" value="1">Primary Image</html:radio>
                </div>
                <div id="image2Div" style="display:none">
                    <html:file property="image2" size="50" />
                    <html:radio property="primaryImageNumber" value="2">Primary Image</html:radio>
                </div>
                <div id="image3Div" style="display:none">
                    <html:file property="image3" size="50" />
                    <html:radio property="primaryImageNumber" value="3">Primary Image</html:radio>
                </div>
                <div id="image4Div" style="display:none">
                    <html:file property="image4" size="50" />
                    <html:radio property="primaryImageNumber" value="4">Primary Image</html:radio>
                </div>
                <div id="image5Div" style="display:none">
                    <html:file property="image5" size="50" />
                    <html:radio property="primaryImageNumber" value="5">Primary Image</html:radio>
                </div>
                <br/>
                <a href="#" onclick="showOneMoreDiv()">Add more</a>
            </td>
        </tr>
    </table>
    <html:hidden property="cmd"  />
    <html:hidden property="did" />

    <p align="center">
        <html:button property="backButton" value="Back to My Products" styleClass="dark-gray-button" onclick="submitForm('back')" />
        <logic:equal name="ProductSetupForm" property="productId" value="0">
            <html:button property="createButton" styleClass="red-button" value="Create Product" onclick="submitForm('create')" />
        </logic:equal>
        <logic:greaterThan name="ProductSetupForm" property="productId" value="0">
            <html:hidden property="pid" />
            <html:button property="updateButton" styleClass="red-button" value="Update Product" onclick="submitForm('update')" />
        </logic:greaterThan>
    </p>
    
</html:form>

<script type="text/javascript">
    $(document).ready(function(){
        $('#productName').NobleCount("#productNameCount", {max_chars:100});
        $('#details').NobleCount("#detailsCount", {max_chars:3000});
        Galleria.loadTheme('/Hasteer/js/galleria/themes/classic/galleria.classic.js');
        $('.gallery').galleria({image_crop:true});

        $('input#blankIfZero').each(function() {
            var val = $(this).val();
            if (val == 0.0) {
                $(this).val("");
            }
	});        
    });
</script>



