<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    session.setAttribute(BaseInterface.WEDISLE_USER, null);
    response.sendRedirect(baseUrl + "login.jsp");
%>

