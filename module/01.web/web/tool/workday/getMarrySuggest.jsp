<%@ page import="org.apache.commons.lang.StringUtils" %><%@ page import="com.gxx.record.dao.wedisle.WedisleMarrySuggestDao" %><%@ page import="com.gxx.record.entities.wedisle.WedisleMarrySuggest" %><%@ page contentType="text/html;charset=UTF-8" language="java" %><%
    int chooseYear = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("chooseYear")));
    int chooseMonth = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("chooseMonth")));
    System.out.println("chooseYear=" + chooseYear + ";chooseMonth=" + chooseMonth);

    if(!WedisleMarrySuggestDao.isWedisleExistMarrySuggest(chooseYear, chooseMonth)){
        response.getWriter().write("");
    } else
    {
        WedisleMarrySuggest suggest = WedisleMarrySuggestDao.getWedisleMarrySuggestByYearAndMonth(chooseYear, chooseMonth);
        response.getWriter().write(suggest.getLegalFrom() + "," + suggest.getLegalEnd() + "," + suggest.getLateFrom() + "," + suggest.getLateEnd());
    }
%>