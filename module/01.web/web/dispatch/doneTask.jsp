<%@ page import="org.apache.commons.lang.StringUtils"
        %><%@ page import="com.gxx.record.entities.wedisle.WedisleUser"
        %><%@ page import="com.gxx.record.interfaces.BaseInterface"
        %><%@ page import="com.gxx.record.entities.wedisle.WedisleUserStep"
        %><%@ page import="com.gxx.record.dao.wedisle.WedisleUserStepDao"
        %><%@ page contentType="text/html;charset=UTF-8" language="java"
        %><%
    WedisleUser wedisleUser = (WedisleUser)session.getAttribute(BaseInterface.WEDISLE_USER);
    boolean isLogin = null != wedisleUser;
    String resp;
    if(!isLogin){
        resp = "{isSuccess:false,message:'请先登录！'}";
    } else {
        int stepId = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("stepId")));
        WedisleUserStep step = WedisleUserStepDao.getWedisleUserStepsByUserIdAndStepId(wedisleUser.getId(), stepId);
        step.setIsDone(true);
        WedisleUserStepDao.updateWedisleUserStep(step);
        resp = "{isSuccess:true,message:'修改任务状态成功！'}";
    }
    response.getWriter().write(resp);
%>