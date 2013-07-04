<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="com.api.Jsp"%>

The page you requested could not be displayed.
<p style="font-weight: bold;text-indent: 15px;font-size: 13px;">${PageNotFoundForm.badUrl}</p>

<p>There could be couple of reasons:</p>

<ul>
    <li>The page has generated an error, in which case this incident is logged, and we'll work on fixing it asap!</li>
    <li>The page has expired, or it does not exist on our servers. Please make sure that the url you entered is correct.</li>
</ul>

<p>
We apologize for this inconvenience. If you have any questions, feel free to
contact us at <a href="mailto:support@hasteer.com">support@hasteer.com</a>
or you can go back to <a href="<%= Jsp.getUrlBase() %>">Hasteer.com</a>
</p>
