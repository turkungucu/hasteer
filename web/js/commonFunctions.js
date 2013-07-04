/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function clearDropDown(ddName) {
    var dd = document.getElementById(ddName);
    if(dd) {
        for(var i=0; i<dd.options.length; i++) {
            if(dd.options[i].selected)
                dd.options[i].selected = false;
        } 
    }
}

function setTooltips() {
    $('a#tt').each(function() {
        var $link = $(this);
        var text = $link.html();
        $link.html('<font class="tooltip-icon">(?)</font><span class="side">' + text + '<small></small></span>');
        $link.addClass("tooltip");
    });
}


