<%@ page import="java.util.List" %>
<%@ page import="com.gxx.record.utils.WedisleUtils" %>
<%@ page import="com.gxx.record.dao.wedisle.WedisleWorkDayDao" %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleWorkDay" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String yearAndMonth = request.getParameter("yearAndMonth");
    System.out.println(yearAndMonth);
    List<WedisleWorkDay> wedisleWorkDays = WedisleWorkDayDao.queryWedisleWorkDaysByMonth(yearAndMonth);
    String json = WedisleUtils.getJsonFromWedisleWorkDays(wedisleWorkDays);
    response.getWriter().write(json);
%>