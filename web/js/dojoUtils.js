/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function updateCumulativeParticipantsInDeal(dealId,optionId,divName,timeout) {
    dojoAjax("/Hasteer/ajax/DealParticipantUpdater.jsp?did="+dealId+"&oid="+optionId,"text",5000,divName);
}

function updateClosingPrice(dealId,divName) {
    dojoAjax("/Hasteer/ajax/DealClosingPriceUpdater.jsp?dealId="+dealId,"text",5000,divName);
}

function dojoAjax(url,handleAs,timeout,divName) {
dojo.xhrGet( {
    // The following URL must match that used to test the server.
    url: url,
    handleAs: handleAs,

    timeout: timeout, // Time in milliseconds

    // The LOAD function will be called on a successful response.
    load: function(response, ioArgs) {
        dojo.byId(divName).innerHTML = response;
        return response;
    },

    // The ERROR function will be called in an error case.
    error: function(response, ioArgs) {
        console.error("HTTP status code: ", ioArgs.xhr.status);
        return response;
    }
});
}
