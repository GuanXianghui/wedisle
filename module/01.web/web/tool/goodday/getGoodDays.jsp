<%@ page import="java.util.List" %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleGoodDay" %>
<%@ page import="com.gxx.record.dao.wedisle.WedisleGoodDayDao" %>
<%@ page import="com.gxx.record.utils.WedisleUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String yearAndMonth = request.getParameter("yearAndMonth");
    System.out.println(yearAndMonth);
    List<WedisleGoodDay> wedisleGoodDays = WedisleGoodDayDao.queryWedisleGoodDays(yearAndMonth);
    String json = WedisleUtils.getJsonFromWedisleGoodDays(wedisleGoodDays);
    response.getWriter().write(json);
%>