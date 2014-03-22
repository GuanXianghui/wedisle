<%@ page import="org.apache.commons.lang.StringUtils"
        %><%@ page import="com.gxx.record.entities.wedisle.WedisleUser"
        %><%@ page import="com.gxx.record.interfaces.BaseInterface"
        %><%@ page import="com.gxx.record.entities.wedisle.WedisleRemind"
        %><%@ page import="com.gxx.record.dao.wedisle.WedisleRemindDao"
        %>
<%@ page import="org.apache.commons.lang.time.DateUtils" %>
<%@ page import="com.gxx.record.utils.ServiceDataUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
        %><%
    WedisleUser wedisleUser = (WedisleUser)session.getAttribute(BaseInterface.WEDISLE_USER);
    boolean isLogin = null != wedisleUser;
    String resp = "";
    if(!isLogin){
        resp = "{isSuccess:false,message:'请先登录！'}";
    } else {
        int userId = wedisleUser.getId();
        String operateType = StringUtils.trimToEmpty(request.getParameter("operateType"));
        String event = StringUtils.trimToEmpty(request.getParameter("event"));
        String date = StringUtils.trimToEmpty(request.getParameter("date"));
        String remindType = StringUtils.trimToEmpty(request.getParameter("remindType"));
        String remindDate = StringUtils.trimToEmpty(request.getParameter("remindDate"));
        String remindTime = StringUtils.trimToEmpty(request.getParameter("remindTime"));
        if("no".equals(remindType)){
            remindDate = "";
            remindTime = "";
        } else if("before".equals(remindType)){
            remindDate = ServiceDataUtil.getDate(DateUtils.addDays(ServiceDataUtil.getDate(date), -1));
            remindTime = "000000";
        } else if("same".equals(remindType)){
            remindDate = date;
            remindTime = "000000";
        }
        if("save".equals(operateType)){
            WedisleRemind wedisleRemind = new WedisleRemind(userId, event, date, remindType, remindDate, remindTime);
            WedisleRemindDao.insertWedisleRemind(wedisleRemind);
            resp = "{isSuccess:true,message:'新增提醒成功！',id:" + WedisleRemindDao.getMaxIdByUserId(wedisleUser.getId()) + "}";
        } else if("delete".equals(operateType)){
            int id = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("id")));
            WedisleRemind remind = WedisleRemindDao.getWedisleRemindByIdAndUserId(id, userId);
            WedisleRemindDao.deleteWedisleRemind(remind);
            resp = "{isSuccess:true,message:'删除提醒成功！'}";
        } else if("update".equals(operateType)){
            int updateId = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("updateId")));
            WedisleRemind wedisleRemind = new WedisleRemind(updateId, userId, event, date, remindType, remindDate, remindTime);
            WedisleRemindDao.updateWedisleRemind(wedisleRemind);
            resp = "{isSuccess:true,message:'修改提醒成功！'}";
        }
    }
    response.getWriter().write(resp);
%>