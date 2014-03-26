package com.gxx.record.utils;

import com.gxx.record.utils.javamail.SimpleMailSender;

import java.util.Date;

/**
 * 邮件工具类
 * User: Gxx
 * Time: 2014-03-25 16:22
 */
public class EmailUtils
{
    /**
     * 发送邮件
     * @param title
     * @param emails 邮件逗号分隔
     * @param content
     * @return
     * @throws Exception
     */
    public static boolean sendEmail(String title, String emails, String content) throws Exception
    {
        for(int i=0;i<emails.split(",").length;i++){
            System.out.println(emails.split(",")[i]);
            String mailHost = "smtp.163.com";	//发送邮件服务器地址
            String mailUser = "419066357@163.com";			//发送邮件服务器的用户帐号
            String mailPassword = "369880500";	//发送邮件服务器的用户密码
            String[] toAddress = emails.split(",");

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
                return false;
            }
        }
        return true;
    }
}
