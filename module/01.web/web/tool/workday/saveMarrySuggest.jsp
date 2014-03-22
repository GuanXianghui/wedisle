<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.gxx.record.dao.wedisle.WedisleMarrySuggestDao" %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleMarrySuggest" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int chooseYear = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("chooseYear")));
    int chooseMonth = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("chooseMonth")));
    String date1 = StringUtils.trimToEmpty(request.getParameter("date1"));
    String date2 = StringUtils.trimToEmpty(request.getParameter("date2"));
    String date3 = StringUtils.trimToEmpty(request.getParameter("date3"));
    String date4 = StringUtils.trimToEmpty(request.getParameter("date4"));
    System.out.println("chooseYear=" + chooseYear + ";chooseMonth=" + chooseMonth + ";date1=" + date1
            + ";date2=" + date2 + ";date3=" + date3 + ";date4=" + date4
    );
    WedisleMarrySuggest wedisleMarrySuggest = new WedisleMarrySuggest(chooseYear, chooseMonth, date1, date2, date3, date4);
    WedisleMarrySuggestDao.saveOrUpdateWedisleExistMarry(wedisleMarrySuggest);
    response.getWriter().write("OK");
%>