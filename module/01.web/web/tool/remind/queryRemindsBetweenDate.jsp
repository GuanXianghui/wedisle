<%@ page import="org.apache.commons.lang.StringUtils"
        %><%@ page import="com.gxx.record.entities.wedisle.WedisleUser"
        %><%@ page import="com.gxx.record.interfaces.BaseInterface"
        %><%@ page import="com.gxx.record.entities.wedisle.WedisleRemind"
        %><%@ page import="com.gxx.record.dao.wedisle.WedisleRemindDao"
        %><%@ page import="java.util.List"
        %><%@ page import="com.gxx.record.utils.WedisleUtils"
        %><%@ page contentType="text/html;charset=UTF-8" language="java"
        %><%
    WedisleUser wedisleUser = (WedisleUser)session.getAttribute(BaseInterface.WEDISLE_USER);
    boolean isLogin = null != wedisleUser;
    String resp;
    if(!isLogin){
        resp = "{isSuccess:false,message:'请先登录！'}";
    } else {
        int userId = wedisleUser.getId();
        String beginDate = StringUtils.trimToEmpty(request.getParameter("beginDate"));
        String endDate = StringUtils.trimToEmpty(request.getParameter("endDate"));
        List<WedisleRemind> wedisleReminds = WedisleRemindDao.queryWedisleRemindsBetweenDate(userId, beginDate, endDate);
        resp = "{isSuccess:true,message:'根据起止日期查用户提醒成功！',reminds:" + WedisleUtils.getJsonFromWedisleReminds(wedisleReminds) + "}";
    }
    response.getWriter().write(resp);
%>