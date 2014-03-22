<%@ page import="com.gxx.record.utils.HttpClientUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String date = request.getParameter("date");
    System.out.println(date);
    String url = "http://www.daoxila.com/event/jsonp?act=getDateInfo&selectDate=" + date + "&callback=";
    String data = HttpClientUtils.post(url, "", "UTF-8", "UTF-8");
    response.getWriter().write(data);
%>