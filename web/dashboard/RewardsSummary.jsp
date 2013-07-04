<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="com.dao.RewardPointsLog"%>
<%@page import="com.struts.form.RewardsSummaryForm"%>


<div class="messageNormal" style="width: 30%;margin-top: 30px;">
    <div class=" page-sub-title">
       Total Points: ${RewardsSummaryForm.points}<br/>
       Last Updated: <bean:write name="RewardsSummaryForm" property="lastUpdated" formatKey="date.format" />
    </div>
</div>
<p/>
<table id="rewardsSummary" class="display" width="100%" cellspacing="1">
    <thead>
        <tr>
            <th>Date</th>
            <th>Activity Type</th>
            <th>Deal</th>
            <th>Points</th>
        </tr>
    </thead>
    <tbody>
        <logic:iterate id="rewardsSummaryRow" name="RewardsSummaryForm" property="activities"
                       type="com.dao.RewardPointsLog">
            <tr>
                <td align="center">
                    <bean:write name="rewardsSummaryRow" property="dateAdded" formatKey="date.format" />
                </td>
                <td>
                    <c:choose>
                        <c:when test="${rewardsSummaryRow.points > 0}">
                            Earned
                        </c:when>
                        <c:otherwise>
                            Redeemed
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    ${rewardsSummaryRow.dealId}
                </td>
                <td>
                    ${rewardsSummaryRow.points}
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
        $('#rewardsSummary').dataTable({
            "bJQueryUI": true,
            "sPaginationType": "full_numbers",
            "aoColumns": [ null, null, null, { "bSortable": false } ]
        });
    });
</script>