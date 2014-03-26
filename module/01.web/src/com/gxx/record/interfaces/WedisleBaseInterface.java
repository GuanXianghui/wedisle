/**
 * File Name:    BaseInterface.java
 *
 * File Desc:    �����ӿ�
 *
 * Product AB:   PAYGATE_1_0_0
 *
 * Product Name: PAYGATE
 *
 * Module Name:  01.core
 *
 * Module AB:    01.core
 *
 * Author:       Gxx
 *
 * History:      2013-06-24 created by Gxx
 */
package com.gxx.record.interfaces;

/**
 * �����ӿ�
 * @author Gxx
 * @version 1.0
 */
public class WedisleBaseInterface
{
    /**
     * Ĭ������������
     */
    public static final int DEFAULT_ERROR_PWD_NUM = 0;
    /**
     * Ĭ�����ѹ�ϵ
     */
    public static final String DEFAULT_FRIENDS_TYPE = "�з�����,Ů������,�з�����,Ů������,��ͬ����,����";
    /**
     * Ĭ������������
     */
    public static final String DISPATCH_REPLACE_STRING = "_GXX_REPLACE_";
    /**
     * ���������ʼ�ģ��
     */
    public static final String DISPATCH_EMAIL_MODEL = "<script type=\"text/javascript\">\n" +
            "    // �������ڸ�ʽ��\n" +
            "    defineDateFormat();\n" +
            "    // �������ڸ�ʽ��\n" +
            "    function defineDateFormat(){\n" +
            "        // ��Date����չ���� Date ת��Ϊָ����ʽ��String\n" +
            "        // ��(M)����(d)��Сʱ(h)����(m)����(s)������(q) ������ 1-2 ��ռλ����\n" +
            "        // ��(y)������ 1-4 ��ռλ��������(S)ֻ���� 1 ��ռλ��(�� 1-3 λ������)\n" +
            "        // ���ӣ�\n" +
            "        // (new Date()).format(\"yyyy-MM-dd hh:mm:ss.S\") ==> 2006-07-02 08:09:04.423\n" +
            "        // (new Date()).format(\"yyyy-M-d h:m:s.S\")      ==> 2006-7-2 8:9:4.18\n" +
            "        Date.prototype.format = function(fmt)\n" +
            "        {\n" +
            "            var o = {\n" +
            "                \"M+\" : this.getMonth()+1,                 //�·�\n" +
            "                \"d+\" : this.getDate(),                    //��\n" +
            "                \"h+\" : this.getHours(),                   //Сʱ\n" +
            "                \"m+\" : this.getMinutes(),                 //��\n" +
            "                \"s+\" : this.getSeconds(),                 //��\n" +
            "                \"q+\" : Math.floor((this.getMonth()+3)/3), //����\n" +
            "                \"S\"  : this.getMilliseconds()             //����\n" +
            "            };\n" +
            "            if(/(y+)/.test(fmt))\n" +
            "                fmt=fmt.replace(RegExp.$1, (this.getFullYear()+\"\").substr(4 - RegExp.$1.length));\n" +
            "            for(var k in o)\n" +
            "                if(new RegExp(\"(\"+ k +\")\").test(fmt))\n" +
            "                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : ((\"00\"+ o[k]).substr((\"\"+ o[k]).length)));\n" +
            "            return fmt;\n" +
            "        }\n" +
            "    }\n" +
            "</script>\n" +
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
            "<meta name=\"keywords\" content=\"��־,����,��������,7-blog\"/>\n" +
            "<meta name=\"Description\" content=\"My Dairy weekly\" />\n" +
            "<title>�û�����ƽ̨-����</title>\n" +
            "<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"http://7swing.com/favicon.ico\" />\n" +
            "<link href=\"http://www.wedisle.com/style/homePlus.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
            "<link href=\"http://www.wedisle.com/style/cp.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
            "<script type=\"text/javascript\" src=\"http://www.wedisle.com/Scripts/jquery-min.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"http://www.wedisle.com/Scripts/comm.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"http://www.wedisle.com/Scripts/fancybox/jquery.mousewheel-3.0.4.pack.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"http://www.wedisle.com/Scripts/fancybox/jquery.fancybox-1.3.4.pack.js\"></script>\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"http://www.wedisle.com/Scripts/fancybox/jquery.fancybox-1.3.4.css\" media=\"screen\" />\n" +
            "<script type=\"text/javascript\">\n" +
            "\t\t$(document).ready(function() {\n" +
            "\t\t\t$(\".dialogBtn\").fancybox();\n" +
            "\t\t});\n" +
            "\t</script>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div class=\"header\">\n" +
            "  <p class=\"topright\"><a href=\"#\">������ҳ</a> | <a href=\"#\">�ҵ���ҳ</a> | <a href=\"#\" class=\"msgrev msgnew\">&nbsp;</a> | <a href=\"#\">�˳�</a></p>\n" +
            "  <div class=\"title\"> <span class=\"logo\"><img src=\"http://www.wedisle.com//images/logo.png\" alt=\"My Wonderland\" /></span> <span class=\"pagetitle\">��¼�ҵ����̣�<a href=\"#\">�����ҵ���ҳ</a></span> </div>\n" +
            "</div>\n" +
            "<div class=\"main\">\n" +
            "  <div class=\"subbar\" style=\"display: none;\">\n" +
            "    <ul>\n" +
            "      <li class=\"selected\"><a href=\"#\">�ҵĻ���</a></li>\n" +
            "      <li><a href=\"#\">�ҵļƻ�</a></li>\n" +
            "      <li><a href=\"#\">���Ѳ�</a></li>\n" +
            "      <li><a href=\"#\">�������</a></li>\n" +
            "      <li><a href=\"#\">�ҵĹ���</a></li>\n" +
            "      <li><a href=\"#\">������Ϣά��</a></li>\n" +
            "    </ul>\n" +
            "  </div>\n" +
            "  <div class=\"mainmod\" style=\"margin-left:0px;\">\n" +
            "    <div class=\"main-inside\">\n" +
            "      <h1>����СƦ�Ӹ�����������:</h1>\n" +
            "      <div class=\"cShowMod\">\n" +
            "    <div class=\"cImagebox\"><img src=\"http://www.wedisle.com//images/b_sample.jpg\" /></div>\n" +
            "    <div class=\"cInfobox\">\n" +
            "    <h3 class=\"cname\"><span class=\"maleIco\">����СƦ��</span>+<span class=\"femaleIco\">����Ů����</span></h3>\n" +
            "    <ul>\n" +
            "    <li><div class=\"countdown\">�����ǵĻ�����<span class=\"num\">24</span>��</div></li>\n" +
            "    <li>���ǵĻ�����2013��3��19��</li>\n" +
            "    <li><a href=\"#\" target=\"_blank\">���ǵ���ҳ > </a> | <a class=\"icolink qrcode dialogBtn\" href=\"http://www.wedisle.com/images/mp_qrcode.png\" target=\"_blank\">ɨ���ά�����</a></li>\n" +
            "    </ul>\n" +
            "    </div>\n" +
            "  </div>\n" +
            "  <div class=\"wedInfo\">\n" +
            "  <h2>������Ϣ</h2>\n" +
            "  <ul class=\"marryInfos\">\n" +
            "    <li>���⣺" + DISPATCH_REPLACE_STRING + "dispatchTitle</li>\n" +
            "    <li>�����ˣ�" + DISPATCH_REPLACE_STRING + "dispatchFriends</li>\n" +
            "    <li>��ʼʱ�䣺" + DISPATCH_REPLACE_STRING + "dispatchBeginDate</li>\n" +
            "    <li>����ʱ�䣺" + DISPATCH_REPLACE_STRING + "dispatchEndDate</li>\n" +
            "    <br>�������ݣ�<br>" + DISPATCH_REPLACE_STRING + "dispatchContent\n" +
            "    </ul>\n" +
            "  </div>\n" +
            "</div>\n" +
            "<p class=\"copyright\"><span>Copyright?�Ϻ�XXXXXXXX���޹�˾</span><br />\n" +
            "  <a href=\"#\">���ڻ���</a></p>\n" +
            "<div style=\"display: none\">\n" +
            "    <img id=\"qr\" src=\"http://api.k780.com:88/?app=qr.get&level=L&size=6&data=http://go.jhost.cn/gxx\" alt=\"��ά��\">\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>\n";
}
