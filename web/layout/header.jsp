<%@ taglib uri="/WEB-INF/taglib/struts-html.tld" prefix="html" %>
<div class="header-core">
    <html:form action="/Search.do" method="get">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
            <tr valign="bottom" align="center">
                <td align="center" width="1px">
                    <div style="padding-bottom:4px;padding-left: 10px;">
                        <table cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <div class="ui-helper-clearfix">
                                        <input id="q" type="text" size="40" name="q" class="q" style="font-size:22px;height:28px;border-right: none;" />
                                    </div>
                                </td>
                                <td style="border-right:1px solid #dddddd;border-top:1px solid #dddddd;border-bottom:1px solid #dddddd;">
                                    <html:submit value="Search Hasteer" styleClass="red-button" style="border:0px;height: 34px;" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
        </table>
    </html:form>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        var cache = {};
        $("#q").bind("keyup", function() {
            $(this).autocomplete({
                minLength: 2,
                source: function(request, response) {
                        if ( request.term in cache ) {
                            response( cache[ request.term ] );
                            return;
                        }

                        $.ajax({
                            url: "/Hasteer/ajax/AutoCompleteService.jsp",
                            dataType: "json",
                            data: request,
                            success: function( data ) {
                                cache[ request.term ] = $.ui.autocomplete.filter(data, request.term);
                                response(cache[ request.term ]);
                            }
                        });
                }
            });

        });
	
    });
</script>
