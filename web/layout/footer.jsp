<%@page import="com.api.Jsp"%>
<%@page import="com.dao.ServiceContract"%>

<a href="<%=Jsp.getUrlBase()%>">Home</a> |
<a href="<%=Jsp.getServiceContractUrl(ServiceContract.TERMS_OF_SERVICE)%>">Terms Of Service</a> |
<a href="<%=Jsp.getServiceContractUrl(ServiceContract.PRIVACY_POLICY)%>">Privacy Policy</a> |
<a href="#">About Us</a> |
<a href="/Hasteer/common/faq.do">Frequently Asked Questions</a> |
<a href="#">Contact Us</a>
