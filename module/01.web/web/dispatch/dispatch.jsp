<%@ page import="org.apache.commons.lang.StringUtils"
        %><%@ page import="com.gxx.record.interfaces.WedisleBaseInterface"
        %><%@ page import="com.gxx.record.utils.WedisleUtils"
        %><%@ page import="java.util.Date"
        %><%@ page import="org.apache.struts2.ServletActionContext"
        %><%@ page import="java.io.OutputStream"
        %><%@ page import="java.io.BufferedOutputStream"
        %><%@ page import="java.io.FileOutputStream"
        %><%@ page import="java.io.File"
        %><%@ page import="com.gxx.record.utils.PropertyUtil"
        %><%@ page import="com.gxx.record.entities.wedisle.WedisleUser"
        %><%@ page import="com.gxx.record.interfaces.BaseInterface"
        %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleUserStep" %>
<%@ page import="com.gxx.record.dao.wedisle.WedisleUserStepDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
        %><%
    String baseUrl = PropertyUtil.getInstance().getProperty(BaseInterface.WED_ISLE_BASE_URL);
    WedisleUser wedisleUser = (WedisleUser)session.getAttribute(BaseInterface.WEDISLE_USER);
    boolean isLogin = null != wedisleUser;
    String resp;
    if(!isLogin){
        resp = "{isSuccess:false,message:'请先登录！'}";
    } else {
        String dispatchTitle = StringUtils.trimToEmpty(request.getParameter("dispatchTitle"));
        String dispatchBeginDate = StringUtils.trimToEmpty(request.getParameter("dispatchBeginDate"));
        String dispatchEndDate = StringUtils.trimToEmpty(request.getParameter("dispatchEndDate"));
        String dispatchContent = StringUtils.trimToEmpty(request.getParameter("dispatchContent"));
        String dispatchFriends = StringUtils.trimToEmpty(request.getParameter("dispatchFriends"));
        String stepId = StringUtils.trimToEmpty(request.getParameter("stepId"));

        String email = WedisleBaseInterface.DISPATCH_EMAIL_MODEL;
        email = WedisleUtils.replaceEmailModelWithValue(email, "dispatchTitle", dispatchTitle);
        email = WedisleUtils.replaceEmailModelWithValue(email, "dispatchBeginDate", dispatchBeginDate);
        email = WedisleUtils.replaceEmailModelWithValue(email, "dispatchEndDate", dispatchEndDate);
        email = WedisleUtils.replaceEmailModelWithValue(email, "dispatchContent", dispatchContent);
        email = WedisleUtils.replaceEmailModelWithValue(email, "dispatchFriends", dispatchFriends);

        int userId = wedisleUser.getId();
        long nowTime = new Date().getTime();
        String htmlRealPath = ServletActionContext.getServletContext().getRealPath("/") + "dispatch/html/"
                + userId + "_" + nowTime + ".html";//服务器上文件路径
        String htmlUrl = baseUrl + "dispatch/html/" + userId + "_" + nowTime + ".html";
        OutputStream out2 = new BufferedOutputStream(new FileOutputStream(new File(htmlRealPath)));
        out2.write(email.getBytes("UTF-8"));
        out2.flush();

        WedisleUserStep userStep = WedisleUserStepDao.getWedisleUserStepsByUserIdAndStepId(userId, Integer.parseInt(stepId));
        userStep.setDispatchTitle(dispatchTitle);
        userStep.setDispatchFriends(dispatchFriends);
        userStep.setDispatchBeginDate(dispatchBeginDate.replaceAll("-", ""));
        userStep.setDispatchEndDate(dispatchEndDate.replaceAll("-", ""));
        userStep.setDispatchContent(dispatchContent);
        userStep.setDispatchHtml(htmlUrl);
        WedisleUserStepDao.updateWedisleUserStep(userStep);

        resp = "{isSuccess:true,message:'邮件模板创建成功！',htmlUrl:'" + htmlUrl + "'}";
    }
    response.getWriter().write(resp);
%>