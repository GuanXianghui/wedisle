<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String url = request.getParameter("url");
    String width = request.getParameter("width");
    String height = request.getParameter("height");
%>
<html>
<head>
    <title></title>
</head>
<body>
<embed src="<%=url%>"
       allowFullScreen="true"
       quality="high"
       width="<%=width%>"
       height="<%=height%>"
       allowScriptAccess="always"
       type="application/x-shockwave-flash"><!--align="middle"-->
</embed>
</body>
</html>