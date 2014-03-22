<%@ page import="com.gxx.record.utils.javamail.SimpleMailSender" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%
        String title = request.getParameter("title");
        String content = (String)session.getAttribute("htmlContent");
        String friends = request.getParameter("friends");

        for(int i=0;i<friends.split(",").length;i++){
            System.out.println(friends.split(",")[i]);
            String mailHost = "smtp.163.com";	//发送邮件服务器地址
            String mailUser = "419066357@163.com";			//发送邮件服务器的用户帐号
            String mailPassword = "369880500";	//发送邮件服务器的用户密码
            String[] toAddress = friends.split(",");

            SimpleMailSender sendmail = SimpleMailSender.getHtmlMailSender(mailHost, mailUser, mailPassword);
            try {
                sendmail.setSubject(title);
                sendmail.setSendDate(new Date());
                sendmail.setMailContent(content); //
                //sendmail.setAttachments("E:\\TOOLS\\pm_sn.txt");
                sendmail.setMailFrom(mailUser);
                sendmail.setMailTo(toAddress, "to");
                //sendmail.setMailTo(toAddress, "cc");//设置抄送给...
                //开始发送邮件
                System.out.println("正在发送邮件，请稍候.......");
                sendmail.sendMail();
                System.out.println("恭喜你，邮件已经成功发送!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    %>
</head>
<body>
发送成功！xxxxxxx
</body>
</html>
