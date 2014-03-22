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
 * ����������
 * Create by Gxx
 * Time: 2014-03-19 14:58
 */
public class WedisleStartThread extends Thread
{
    /**
     * �߳�ִ����
     */
    public void run() {
        while(!this.isInterrupted()) {//�߳�δ�ж�ִ��ѭ��
            try {
                Thread.sleep(60000); //ÿ��60sִ��һ��
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm00");
            String remindBeginDateStr = format.format(date);
            String remindEndDateStr = format.format(DateUtils.addMinutes(date, 1));
            //------------------ ��ʼִ�� ---------------------------
            System.out.println("����ѭ������ʱ���remindBeginDateStr:" + remindBeginDateStr
                    + ",remindEndDateStr:" + remindEndDateStr);
            //������ֹ�������ڲ��û�����
            try {
                List<WedisleRemind> wedisleReminds = WedisleRemindDao.queryWedisleRemindsBetweenRemindDateTime
                        (remindBeginDateStr, remindEndDateStr);
                for(int i=0;i<wedisleReminds.size();i++){
                    WedisleRemind wedisleRemind = wedisleReminds.get(i);
                    WedisleUser wedisleUser = DBSelect.getWedisleUserById(wedisleRemind.getUserId());

                    String mailHost = "smtp.163.com";	//�����ʼ���������ַ
                    String mailUser = "419066357@163.com";			//�����ʼ����������û��ʺ�
                    String mailPassword = "369880500";	//�����ʼ����������û�����

                    SimpleMailSender sendmail = SimpleMailSender.getHtmlMailSender(mailHost, mailUser, mailPassword);
                    try {
                        sendmail.setSubject("��������-" + wedisleRemind.getEvent());
                        sendmail.setSendDate(new Date());
                        sendmail.setMailContent("��������-" + wedisleRemind.getEvent() + "!�۸¸�,xxxxxxxxx"); //
                        //sendmail.setAttachments("E:\\TOOLS\\pm_sn.txt");
                        sendmail.setMailFrom(mailUser);
                        sendmail.setMailTo(new String[]{wedisleUser.getEmail()}, "to");
                        //sendmail.setMailTo(toAddress, "cc");//���ó��͸�...
                        //��ʼ�����ʼ�
                        System.out.println("���ڷ����ʼ�[" + wedisleUser.getEmail() + "]�����Ժ�.......");
                        sendmail.sendMail();
                        System.out.println("��ϲ�㣬�ʼ��Ѿ��ɹ�����!");
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
