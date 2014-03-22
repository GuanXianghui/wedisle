package com.gxx.record.listener;

import com.gxx.record.dao.DBSelect;
import com.gxx.record.dao.wedisle.WedisleRemindDao;
import com.gxx.record.entities.wedisle.WedisleRemind;
import com.gxx.record.entities.wedisle.WedisleUser;
import com.gxx.record.utils.javamail.SimpleMailSender;
import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 启动监听器
 * Create by Gxx
 * Time: 2014-03-19 14:58
 */
public class WedisleStartThread extends Thread
{
    /**
     * 线程执行体
     */
    public void run() {
        while(!this.isInterrupted()) {//线程未中断执行循环
            try {
                Thread.sleep(60000); //每隔60s执行一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm00");
            String remindBeginDateStr = format.format(date);
            String remindEndDateStr = format.format(DateUtils.addMinutes(date, 1));
            //------------------ 开始执行 ---------------------------
            System.out.println("本次循环提醒时间段remindBeginDateStr:" + remindBeginDateStr
                    + ",remindEndDateStr:" + remindEndDateStr);
            //根据起止提醒日期查用户提醒
            try {
                List<WedisleRemind> wedisleReminds = WedisleRemindDao.queryWedisleRemindsBetweenRemindDateTime
                        (remindBeginDateStr, remindEndDateStr);
                for(int i=0;i<wedisleReminds.size();i++){
                    WedisleRemind wedisleRemind = wedisleReminds.get(i);
                    WedisleUser wedisleUser = DBSelect.getWedisleUserById(wedisleRemind.getUserId());

                    String mailHost = "smtp.163.com";	//发送邮件服务器地址
                    String mailUser = "419066357@163.com";			//发送邮件服务器的用户帐号
                    String mailPassword = "369880500";	//发送邮件服务器的用户密码

                    SimpleMailSender sendmail = SimpleMailSender.getHtmlMailSender(mailHost, mailUser, mailPassword);
                    try {
                        sendmail.setSubject("婚礼岛提醒-" + wedisleRemind.getEvent());
                        sendmail.setSendDate(new Date());
                        sendmail.setMailContent("婚礼岛提醒-" + wedisleRemind.getEvent() + "!哇嘎嘎,xxxxxxxxx"); //
                        //sendmail.setAttachments("E:\\TOOLS\\pm_sn.txt");
                        sendmail.setMailFrom(mailUser);
                        sendmail.setMailTo(new String[]{wedisleUser.getEmail()}, "to");
                        //sendmail.setMailTo(toAddress, "cc");//设置抄送给...
                        //开始发送邮件
                        System.out.println("正在发送邮件[" + wedisleUser.getEmail() + "]，请稍候.......");
                        sendmail.sendMail();
                        System.out.println("恭喜你，邮件已经成功发送!");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
