<%@ page import="org.apache.commons.lang.StringUtils"
        %><%@ page import="com.gxx.record.utils.PropertyUtil"
        %><%@ page import="com.gxx.record.entities.wedisle.WedisleUser"
        %><%@ page import="com.gxx.record.interfaces.BaseInterface"
        %><%@ page import="com.gxx.record.utils.EmailUtils"
        %><%@ page import="com.gxx.record.utils.HttpClientUtils"
        %><%@ page contentType="text/html;charset=UTF-8" language="java"
        %><%
    WedisleUser wedisleUser = (WedisleUser)session.getAttribute(BaseInterface.WEDISLE_USER);
    boolean isLogin = null != wedisleUser;
    String resp;
    if(!isLogin){
        resp = "{isSuccess:false,message:'请先登录！'}";
    } else {
        String title = StringUtils.trimToEmpty(request.getParameter("title"));
        String emails = StringUtils.trimToEmpty(request.getParameter("emails"));
        String htmlUrl = StringUtils.trimToEmpty(request.getParameter("htmlUrl"));
        //获取内容
        String content = HttpClientUtils.postUrl(htmlUrl, HttpClientUtils.ENCODE_UTF8, HttpClientUtils.ENCODE_UTF8);
        System.out.println(content);
        boolean result = EmailUtils.sendEmail(title, emails, content);
        resp = "{isSuccess:" + result + ",message:'" + (result?"邮件发送成功！":"邮件发送失败！") + "'}";
    }
    response.getWriter().write(resp);
%>