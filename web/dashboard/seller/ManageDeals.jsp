<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="com.dao.Deal" %>
<%@page import="com.struts.form.MyDealsForm" %>


<div style="margin-top: 20px;margin-bottom: 20px;">
    <a href="/Hasteer/dashboard/seller/DealSetup.do" class="red-button" style="text-decoration:none;">
        Create A New Deal
    </a>
</div>

<table id="myDeals" class="display" width="100%" cellspacing="1">
    <thead>
        <tr>
            <th>Date Created</th>
            <th>Deal Name</th>
            <th>Deal Status</th>
            <th>Manage</th>
        </tr>
    </thead>
    <tbody>
        <logic:iterate id="deal" name="MyDealsForm" property="deals" type="com.dao.Deal">
            <tr>
                <td>
                    <bean:write name="deal" property="createDate" formatKey="date.format" />
                </td>
                <td>
                    <a href="/Hasteer/product/ProductDetails.do?dealId=<bean:write name="deal" property="dealId" />">
                        <bean:write name="deal" property="dealName" />
                    </a>
                </td>
                <td align="center">
                    <a class="dealPopupTrigger" href="#"
                       rel="${deal.dealId},-1">
                        <bean:write name="deal" property="statusMessage" />
                    </a>
                </td>
                <td align="center" width="1px" nowrap>
                    <a href="/Hasteer/dashboard/seller/DealSetup.do?dealId=<bean:write name="deal" property="dealId" />">
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

<script type="text/javascript" src="/Hasteer/js/ajaxDealTooltip.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $('#myDeals').dataTable({
            "bJQueryUI": true,
            "sPaginationType": "full_numbers",
            "aoColumns": [ null, null, null, { "bSortable": false } ]
        });
    });
</script>