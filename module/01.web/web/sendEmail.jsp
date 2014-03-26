<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%
        String title = request.getParameter("title");
        String content = (String)session.getAttribute("htmlContent");
        String friends = request.getParameter("friends");
        EmailUtils.sendEmail(title, friends, content);
    %>
</head>
<body>
发送成功！xxxxxxx
</body>
</html>
