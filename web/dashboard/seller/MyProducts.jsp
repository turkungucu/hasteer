<%-- 
    Document   : MyProducts
    Created on : Jan 15, 2010, 7:46:24 PM
    Author     : ecolak
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el" %>
<%@page import="com.dao.ProductCategory, com.dao.Product, com.dao.ProductImage" %>
<%@page import="com.struts.form.MyProductsForm, com.api.Jsp, com.constants.HasteerConstants" %>


<div style="margin-top: 20px;margin-bottom: 20px;">
    <a href="/Hasteer/dashboard/seller/ProductSetup.do" class="red-button" style="text-decoration:none;">
        Create A New Product
    </a>
</div>

<table id="myProducts" class="display" width="100%" cellspacing="1">
    <thead>
        <tr><th>Date Created</th><th>Product Name</th><th>Category</th><th>Manage</th></tr>
    </thead>
    <tbody>
        <logic:iterate id="product" name="MyProductsForm" property="products" type="com.dao.Product">
            <tr>
                <td>
                    <bean:write name="product" property="createDate" formatKey="date.format" />
                </td>
                <td>
                    <bean:write name="product" property="productName" />
                </td>
                <td><bean:write name="product" property="categoryName" /></td>
                <td align="center" width="1px" nowrap>
                    <a href="/Hasteer/dashboard/seller/ProductSetup.do?pid=<bean:write name="product" property="productId" />">
                        Edit
                    </a><span style="padding-left: 2px;padding-right: 2px;">|</span>
                    <a href="#">
                        Delete
                    </a>
                </td>
            </tr>
        </logic:iterate>
    </tbody>
</table>
<p/>
<!-- hack to put whitespace-->
<table>
    <tr>
        <td height="30px">

        </td>
    </tr>
</table>

<script type="text/javascript">
    $(document).ready(function(){
        $('#myProducts').dataTable({
            "bJQueryUI": true,
            "sPaginationType": "full_numbers",
            "aoColumns": [ null, null, null, { "bSortable": false } ]
        });
    });
</script>