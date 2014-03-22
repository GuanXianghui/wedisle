<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    String log_tag = "WEIXIN_RECORD_FORUM_LOG";
    out.print(application.getAttribute(log_tag));
%>
</body>
</html>