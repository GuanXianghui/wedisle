<%@ page import="com.gxx.record.dao.wedisle.WedisleWorkDayDao" %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleWorkDay" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String workDays = StringUtils.trimToEmpty(request.getParameter("workDays"));
    String holidays = StringUtils.trimToEmpty(request.getParameter("holidays"));
    System.out.println("workDays=" + workDays + ";holidays=" + holidays);
    for(String date : workDays.split(",")){
        if(StringUtils.isEmpty(date)){
            continue;
        }
        WedisleWorkDay workDay = new WedisleWorkDay(date, true);
        if(WedisleWorkDayDao.isExistWorkDay(date)){
            WedisleWorkDayDao.updateWedisleWorkDay(workDay);
        } else {
            WedisleWorkDayDao.insertWedisleWorkDay(workDay);
        }
    }
    for(String date : holidays.split(",")){
        if(StringUtils.isEmpty(date)){
            continue;
        }
        WedisleWorkDay workDay = new WedisleWorkDay(date, false);
        if(WedisleWorkDayDao.isExistWorkDay(date)){
            WedisleWorkDayDao.updateWedisleWorkDay(workDay);
        } else {
            WedisleWorkDayDao.insertWedisleWorkDay(workDay);
        }
    }
    response.getWriter().write("OK");
%>