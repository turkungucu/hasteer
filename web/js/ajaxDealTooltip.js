// Code taken from http://rndnext.blogspot.com/2009/02/jquery-ajax-tooltip.html
$(function()
{
  var hideDelay = 500;  
  var optionID;
  var hideTimer = null;

  // One instance that's reused to show info for the current deal
  var container = $('<div id="dealPopupContainer">'
      + '<table width="" border="0" cellspacing="0" cellpadding="0" align="center" class="dealPopupPopup">'
      + '<tr>'
      + '   <td class="corner topLeft"></td>'
      + '   <td class="top">&nbsp;</td>'
      + '   <td class="corner topRight"></td>'
      + '</tr>'
      + '<tr>'
      + '   <td class="left">&nbsp;</td>'
      + '   <td><div id="dealPopupContent"></div></td>'
      + '   <td class="right">&nbsp;</td>'
      + '</tr>'
      + '<tr>'
      + '   <td class="corner bottomLeft">&nbsp;</td>'
      + '   <td class="bottom">&nbsp;</td>'
      + '   <td class="corner bottomRight"></td>'
      + '</tr>'
      + '</table>'
      + '</div>');

  $('body').append(container);

  $('.dealPopupTrigger').live('mouseover', function()
  {
      // format of 'rel' tag: pageid,personguid
      var settings = $(this).attr('rel').split(',');
      var dealID = settings[0];
      optionID = settings[1];

      // If no dealID in url rel tag, don't popup blank
      if (dealID == '')
          return;

      if (hideTimer)
          clearTimeout(hideTimer);

      var pos = $(this).offset();
      var width = $(this).width();
      container.css({
          left: (pos.left + width) + 'px',
          top: pos.top - 33 + 'px'
      });

      $('#dealPopupContent').html('&nbsp;');

      $.ajax({
          type: 'GET',
          url: '/Hasteer/ajax/DealStatusView.jsp',
          data: 'dealId=' + dealID + '&optionId=' + optionID,
          beforeSend: function() {
              $('#dealPopupContent').empty().append('<img src="/Hasteer/images/tooltip/ajax-loader.gif" />');
          },
          success: function(data)
          {
              // Verify requested deal is this deal since we could have multiple ajax
              // requests out if the server is taking a while.
              //if (data.indexOf(dealID) > 0)
              //{
                  //var text = $(data).find('.dealPopupResult').html();
                  //$('#dealPopupContent').html(data.trim());
                  $('#dealPopupContent').empty().append(data);
              //}
          }
      });

      container.css('display', 'block');
  });

  $('.dealPopupTrigger').live('mouseout', function()
  {
      if (hideTimer)
          clearTimeout(hideTimer);
      hideTimer = setTimeout(function()
      {
          container.css('display', 'none');
      }, hideDelay);
  });

  // Allow mouse over of details without hiding details
  $('#dealPopupContainer').mouseover(function()
  {
      if (hideTimer)
          clearTimeout(hideTimer);
  });

  // Hide after mouseout
  $('#dealPopupContainer').mouseout(function()
  {
      if (hideTimer)
          clearTimeout(hideTimer);
      hideTimer = setTimeout(function()
      {
          container.css('display', 'none');
      }, hideDelay);
  });
});