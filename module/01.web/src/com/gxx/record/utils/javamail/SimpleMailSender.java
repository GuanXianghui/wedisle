/**
 * �ʼ����������
 * ������Ҫ����jar�ļ�: mail.jar,activation.jar,htmlparser.jar
 * @author Winter Lau (winter.lau[at]163.com)
 * @version 1.0
 */
package com.gxx.record.utils.javamail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.ParserException;

/**
 * �ʼ��������,�����ʹ�÷������ո����main����
 * @author Winter Lau
 */
public abstract class SimpleMailSender extends Authenticator{

    private String username = null;		//�ʼ������ʺ��û���
    private String userpasswd = null;	//�ʼ������ʺ��û�����
    protected BodyPart messageBodyPart = null;
    protected Multipart multipart = new MimeMultipart("related");
    protected MimeMessage mailMessage = null;
    protected Session mailSession = null;
    protected Properties mailProperties = System.getProperties();
    protected InternetAddress mailFromAddress = null;
    protected InternetAddress mailToAddress = null;
    protected Authenticator authenticator = null;
    protected String mailSubject = "";
    protected Date mailSendDate = null;

    /**
     * ���캯��
     * @param smtpHost
     * @param username
     * @param password
     */
    protected SimpleMailSender(String smtpHost, String username, String password) {
        this.username = username;
        this.userpasswd = password;
        mailProperties.put("mail.smtp.host", smtpHost);
        mailProperties.put("mail.smtp.auth", "true"); //����smtp��֤���ܹؼ���һ��
        mailSession = Session.getDefaultInstance(mailProperties, this);
        mailMessage = new MimeMessage(mailSession);
        messageBodyPart = new MimeBodyPart();
    }
    /**
     * ����һ�����ı��ʼ�����ʵ��
     * @param smtpHost
     * @param username
     * @param password
     * @return
     */
    public static SimpleMailSender getTextMailSender(String smtpHost, String username, String password) {
        return new SimpleMailSender(smtpHost,username,password) {
            public void setMailContent(String mailContent) throws MessagingException {
                messageBodyPart.setText(mailContent);
                multipart.addBodyPart(messageBodyPart);
            }
        };
    }
    /**
     * ����һ�����ı��ʼ�����ʵ��
     * @param smtpHost
     * @param username
     * @param password
     * @return
     */
    public static SimpleMailSender getHtmlMailSender(String smtpHost, String username, String password) {
        return new SimpleMailSender(smtpHost,username,password) {
            private ArrayList arrayList1 = new ArrayList();
            private ArrayList arrayList2 = new ArrayList();

            public void setMailContent(String mailContent) throws MessagingException {
                String htmlContent = getContent("<img src=", mailContent);
                messageBodyPart.setContent(htmlContent, "text/html;charset=GB2312");
                multipart.addBodyPart(messageBodyPart);
                //���ô���html�ļ��е�ͼƬ����
                processHtmlImage(mailContent);
            }

            //����htmlҳ���ϵ�ͼƬ�������£�
            private void processHtmlImage(String mailContent) throws MessagingException {
                for (int i = 0; i < arrayList1.size(); i++) {
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource((String) arrayList1.get(i));
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    String contentId = "<" + (String) arrayList2.get(i) + ">";
                    messageBodyPart.setHeader("Content-ID", contentId);
                    messageBodyPart.setFileName((String) arrayList1.get(i));
                    multipart.addBodyPart(messageBodyPart);
                }
            }

            //����Ҫ���͵�html�ļ�����Ҫ�����html�ļ��е�ͼƬ
            private String getContent(String searchString, String mailContent) {
                try {
                    Parser parser = Parser.createParser(new String(mailContent.getBytes(), ISO8859_1), ISO8859_1);
                    Node[] images = parser.extractAllNodesThatAre(ImageTag.class);
                    for(int i=0;i<images.length;i++) {
                        ImageTag imgTag = (ImageTag) images[i];
                        if(!imgTag.getImageURL().toLowerCase().startsWith("http://"))
                            arrayList1.add(imgTag.getImageURL());
                    }
                } catch (UnsupportedEncodingException e1) {
                } catch (ParserException e) {}
                String afterReplaceStr = mailContent;
                //��html�ļ�����"cid:"+Content-ID���滻ԭ����ͼƬ����
                for (int m = 0; m < arrayList1.size(); m++) {
                    arrayList2.add(createRandomStr());
                    String addString = "cid:" + (String) arrayList2.get(m);
                    afterReplaceStr = mailContent.replaceAll(
                            (String) arrayList1.get(m), addString);
                }
                return afterReplaceStr;
            }

            //����һ������ַ�����Ϊ�˸�ͼƬ�趨Content-IDֵ
            private String createRandomStr() {
                char[] randomChar = new char[8];
                for (int i = 0; i < 8; i++) {
                    randomChar[i] = (char) (Math.random() * 26 + 'a');
                }
                String replaceStr = new String(randomChar);
                return replaceStr;
            }

            private final static String ISO8859_1 = "8859_1";
        };
    }
    /**
     * ����ʵ���ʼ������û���֤
     * @see javax.mail.Authenticator#getPasswordAuthentication
     */
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, userpasswd);
    }

    /**
     * �����ʼ�����
     * @param mailSubject
     * @throws MessagingException
     */
    public void setSubject(String mailSubject) throws MessagingException {
        this.mailSubject = mailSubject;
        mailMessage.setSubject(mailSubject);
    }

    /**
     * �������඼��Ҫʵ�ֵĳ��󷽷���Ϊ��֧�ֲ�ͬ���ʼ�����
     * @param mailContent
     * @throws MessagingException
     */
    public abstract void setMailContent(String mailContent) throws MessagingException;

    /**
     * �����ʼ���������
     * @param sendDate
     * @throws MessagingException
     */
    public void setSendDate(Date sendDate) throws MessagingException {
        this.mailSendDate = sendDate;
        mailMessage.setSentDate(sendDate);
    }

    /**
     * �����ʼ����͸���
     * @param attachmentName
     * @throws MessagingException
     */
    public void setAttachments(String attachmentName) throws MessagingException {
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachmentName);
        messageBodyPart.setDataHandler(new DataHandler(source));
        int index = attachmentName.lastIndexOf(File.separator);
        String attachmentRealName = attachmentName.substring(index + 1);
        messageBodyPart.setFileName(attachmentRealName);
        multipart.addBodyPart(messageBodyPart);
    }

    /**
     * ���÷����˵�ַ
     * @param mailFrom
     * @throws MessagingException
     */
    public void setMailFrom(String mailFrom) throws MessagingException {
        mailFromAddress = new InternetAddress(mailFrom);
        mailMessage.setFrom(mailFromAddress);
    }

    /**
     * �����ռ��˵�ַ���ռ�������Ϊto,cc,bcc(��Сд����)
     * @param mailTo   �ʼ������ߵ�ַ
     * @param mailType ֵΪto,cc,bcc
     * @author Liudong
     */
    public void setMailTo(String[] mailTo, String mailType) throws Exception {
        for (int i = 0; i < mailTo.length; i++) {
            mailToAddress = new InternetAddress(mailTo[i]);
            if (mailType.equalsIgnoreCase("to")) {
                mailMessage.addRecipient(Message.RecipientType.TO,mailToAddress);
            } else if (mailType.equalsIgnoreCase("cc")) {
                mailMessage.addRecipient(Message.RecipientType.CC,mailToAddress);
            } else if (mailType.equalsIgnoreCase("bcc")) {
                mailMessage.addRecipient(Message.RecipientType.BCC,mailToAddress);
            } else {
                throw new Exception("Unknown mailType: " + mailType + "!");
            }
        }
    }
    /**
     * ��ʼ�����ʼ�
     * @throws MessagingException
     * @throws SendFailedException
     */
    public void sendMail() throws MessagingException, SendFailedException {
        if (mailToAddress == null)
            throw new MessagingException("�����������д�ռ��˵�ַ��");
        mailMessage.setContent(multipart);
        Transport.send(mailMessage);
    }

    /**
     * �ʼ����Ͳ���
     * @param args
     */
    public static void main(String args[]) {
        String mailHost = "smtp.163.com";	//�����ʼ���������ַ
        String mailUser = "419066357@163.com";			//�����ʼ����������û��ʺ�
        String mailPassword = "369880500";	//�����ʼ����������û�����

        String[] toAddress = {"419066357@qq.com"};
        //ʹ�ó��ı���ʽ�����ʼ�
        SimpleMailSender sendmail = SimpleMailSender.getHtmlMailSender(mailHost, mailUser, mailPassword);
        //ʹ�ô��ı���ʽ�����ʼ�
        //MailSender sendmail = MailSender.getTextMailSender(mailHost, mailUser,mailPassword);
        try {
            sendmail.setSubject("�ʼ����Ͳ���");
            sendmail.setSendDate(new Date());
            String content = "<H1>���,�й�</H1><img src=\"http://localhost/images/b_sample.jpg\">";
            //��ע������Ǳ���ͼƬ����ʹ��б����ΪĿ¼�ָ���,������ʾ
            content+="<img src=\"C:/Users/user/Desktop/1.jpg\"/>";
            sendmail.setMailContent(content); //
            //sendmail.setAttachments("E:\\TOOLS\\pm_sn.txt");
            sendmail.setMailFrom("419066357@163.com");
            sendmail.setMailTo(toAddress, "to");
            //sendmail.setMailTo(toAddress, "cc");//���ó��͸�...
            //��ʼ�����ʼ�
            System.out.println("���ڷ����ʼ������Ժ�.......");
            sendmail.sendMail();
            System.out.println("��ϲ�㣬�ʼ��Ѿ��ɹ�����!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}