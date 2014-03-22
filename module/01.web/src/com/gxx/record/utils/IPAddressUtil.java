/**
 * File Name:    IPAddressUtil.java
 *
 * File Desc:    ȡ�ͻ�IP��ַ������
 *
 * Product AB:   PAYGATE_1_0_0
 *
 * Product Name: PAYGATE
 *
 * Module Name:  02.web
 *
 * Module AB:    02.web
 *
 * Author:       Gxx
 *
 * History:      2012-05-16 created by Gxx
 */
package com.gxx.record.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * ȡ�ͻ�IP��ַ������
 *
 * @author Gxx
 * @version 1.0
 */
public class IPAddressUtil
{
    /**
     * ��HTTP�����л�ȡ�ͻ�IP��ַ
     *
     * @param request http����
     * @return �ͻ�IP��ַ
     */
    public static String getIPAddress(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
