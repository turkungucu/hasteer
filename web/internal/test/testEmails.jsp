<%@page import="javax.mail.*, java.util.*, javax.mail.internet.*"%>
<%@page import="com.constants.HasteerConstants, com.util.ConfigUtil"%>
<%!
Properties getProperties() {
    Properties properties = new Properties();
    properties.put("mail.smtp.host", ConfigUtil.getString("mail.smtp.host", HasteerConstants.DEFAULT_MAIL_STMP_HOST));
    properties.put("mail.smtp.port", ConfigUtil.getString("mail.smtp.port", HasteerConstants.DEFAULT_MAIL_STMP_PORT));
    properties.put("mail.smtp.auth", "true");
    return properties;
}

void sendEmail(final Properties properties, final String from, final String fromPwd, String to,
        String subject, String body, String contentType, boolean bccToSupport) throws Exception
{
        Session mailSession = Session.getInstance(properties, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, fromPwd);
            }
        });

        Message simpleMessage = new MimeMessage(mailSession);

        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
        InternetAddress bccAddress = null;
        //try {
            fromAddress = new InternetAddress(from);
            toAddress = new InternetAddress(to);
            if (bccToSupport) {
                bccAddress = new InternetAddress("support@hasteer.com");
            }
        //} catch (AddressException e) {
          //  System.out.println(e);
        //}

        //try {
            simpleMessage.setFrom(fromAddress);
            simpleMessage.setRecipient(Message.RecipientType.TO, toAddress);
            if (bccToSupport) {
                simpleMessage.setRecipient(Message.RecipientType.BCC, bccAddress);
            }
            simpleMessage.setSubject(subject);
            simpleMessage.setContent(body, contentType);

            Transport.send(simpleMessage);
        //} catch (MessagingException e) {
          //  System.out.println(e);
        //}
    }
%>
<%
String cmd = request.getParameter("cmd");
if("send".equals(cmd)) {
  //System.out.println("send email");
  String from = "info@hasteer.com";
  String fromPwd = "Hasteer66";
  String to = "colemre@gmail.com";
  String subject = "Testing Hasteer emails";
  String body = "Testing Hasteer emails with JUnit";
  //EmailUtil.sendPlainTextEmail(from, fromPwd, to, subject, body);
  try {
    sendEmail(getProperties(), from, fromPwd, to, subject, body, "text/plain", false);
  } catch(Exception e) {
    out.println(e);
  }
  out.println("Sent email to " + to);
}
%>

<html>
<head><title>Test Emails</title></head>
<body>
<form method="GET">
<input type="hidden" name="cmd" value="send" />
<input type="submit" value="Send" />
</form>
</body>
</html>