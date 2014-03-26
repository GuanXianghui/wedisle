package com.gxx.record.utils;

import com.gxx.record.utils.javamail.SimpleMailSender;

import java.util.Date;

/**
 * �ʼ�������
 * User: Gxx
 * Time: 2014-03-25 16:22
 */
public class EmailUtils
{
    /**
     * �����ʼ�
     * @param title
     * @param emails �ʼ����ŷָ�
     * @param content
     * @return
     * @throws Exception
     */
    public static boolean sendEmail(String title, String emails, String content) throws Exception
    {
        for(int i=0;i<emails.split(",").length;i++){
            System.out.println(emails.split(",")[i]);
            String mailHost = "smtp.163.com";	//�����ʼ���������ַ
            String mailUser = "419066357@163.com";			//�����ʼ����������û��ʺ�
            String mailPassword = "369880500";	//�����ʼ����������û�����
            String[] toAddress = emails.split(",");

            SimpleMailSender sendmail = SimpleMailSender.getHtmlMailSender(mailHost, mailUser, mailPassword);
            try {
                sendmail.setSubject(title);
                sendmail.setSendDate(new Date());
                sendmail.setMailContent(content); //
                //sendmail.setAttachments("E:\\TOOLS\\pm_sn.txt");
                sendmail.setMailFrom(mailUser);
                sendmail.setMailTo(toAddress, "to");
                //sendmail.setMailTo(toAddress, "cc");//���ó��͸�...
                //��ʼ�����ʼ�
                System.out.println("���ڷ����ʼ������Ժ�.......");
                sendmail.sendMail();
                System.out.println("��ϲ�㣬�ʼ��Ѿ��ɹ�����!");
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
