<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="javax.servlet.http.Cookie"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="com.dao.User,
                com.dao.User.UserType,
                com.dao.User.UserStatus,
                com.util.AuthUtil,
                com.api.HasteerRESTClient"%>
<%@page import="java.util.Date,
                java.util.Map"%>

<%
Cookie[] cookies = request.getCookies();
String fbCookie = null;

for(Cookie c : cookies) {
    if(c.getName().startsWith("fbs")) {
        fbCookie = c.getValue();
    }
}

Map<String, String> fbParamsMap = AuthUtil.getParameterMap(fbCookie);
long fbUserId = Long.parseLong(fbParamsMap.get("uid"));
String accessToken = URLDecoder.decode(fbParamsMap.get("access_token"));

HasteerRESTClient rest = new HasteerRESTClient();
JSONObject fbJsonObj = rest.getJSON("https://graph.facebook.com/me?access_token=" + accessToken);

User u = User.getUserByFbId(fbUserId);

if (u != null) {
    // login
    session.setAttribute("user", u);
} else {
    // create account
    User newUser = new User();
    newUser.setFbUserId(fbUserId);
    newUser.setType(UserType.BUYER.getValue());
    newUser.setRegistrationTime(new Date());
    newUser.setStatus(UserStatus.ACTIVE.getValue());
    newUser.setEmailVerified(true);
    newUser.setEmail((String) fbJsonObj.get("email"));
    newUser.setUsername((String) fbJsonObj.get("name"));
    newUser.setFirstName((String) fbJsonObj.get("first_name"));
    newUser.setLastName((String) fbJsonObj.get("last_name"));
    newUser.setPassword("no-password-needed");
    newUser.save();
    session.setAttribute("user", newUser);
}

//redirect
response.sendRedirect("/Hasteer/dashboard/MyAccount.do");
%>
