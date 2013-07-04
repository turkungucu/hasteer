<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="com.dao.Deal" %>
<%@page import="com.dao.Product" %>
<%@page import="com.struts.form.MyDealsForm" %>

<table id="myDeals" class="display" width="100%" cellspacing="1">
    <thead>
        <tr>
            <th>Date Joined</th>
            <th>Product Name</th>
            <th>Sold By</th>
            <th>Deal Status</th>
            <th>Manage</th>
        </tr>
    </thead>
    <tbody>
        <logic:iterate id="myDealsRow" name="MyDealsForm" property="myDealsRows" type="com.struts.form.MyDealsForm.MyDealsRow">
            <tr>
                <td align="center">
                    <bean:write name="myDealsRow" property="dealParticipant.joinDate" formatKey="date.format" />
                </td>
                <td>
                    <a href="/Hasteer/product/ProductDetails.do?dealId=<bean:write name="myDealsRow" property="dealParticipant.dealId" />">
                        <bean:write name="myDealsRow" property="product.productName" />
                    </a>
                </td>
                <td>
                    <bean:write name="myDealsRow" property="product.soldBy" />
                </td>
                <td align="center">
                    <a class="dealPopupTrigger" href="#"
                       rel="${myDealsRow.dealParticipant.dealId},${myDealsRow.dealParticipant.pricingOptionId}">
                        <bean:write name="myDealsRow" property="deal.statusMessage" />
                    </a>
                </td>
                <td align="center" width="1px" nowrap>
                    <c:if test="${myDealsRow.deal.running}">
                        <a href="/Hasteer/deals/JoinDeal.do?dealId=<bean:write name="myDealsRow" property="dealParticipant.dealId" />">
                            Manage
                        </a>
                    </c:if>
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
            "aoColumns": [ null, null, null, null, { "bSortable": false } ]
        });
    });
</script>