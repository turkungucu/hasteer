/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function updateCumulativeParticipantsInDeal(dealId,optionId,divName,timeout) {
   var url = '/Hasteer/ajax/DealParticipantUpdater.jsp?did='+dealId+'&oid='+optionId;
   jQueryAjax(divName, url);
   var functionCall = "jQueryAjax('"+divName+"','"+url+"')";
   setInterval(functionCall, timeout);
}

function jQueryAjax(divName, updaterUrl) {
    var jDiv = "#"+divName;
     $.ajax({
       method: 'get',
       url : updaterUrl,
       dataType : 'text',
       success: function (text) { $(jDiv).html(text); }
    });
}

function updateSubscriptionStatus(emailId, containerId, op) {
    var email = $('#' + emailId).val();

    $.ajax({
        type: 'GET',
        url: '/Hasteer/ajax/EmailSubscriptionService.jsp',
        data: 'email=' + email + '&op=' + op,
        beforeSend: function() {
          $('#' + containerId).empty().append('<img src="/Hasteer/css/images/ui-anim_basic_16x16.gif" />');
        },
        success: function(data)
        {
          $('#' + containerId).empty().append(data);
        }
    });
}





