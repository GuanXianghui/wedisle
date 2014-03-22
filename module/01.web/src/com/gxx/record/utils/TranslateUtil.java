/**
 * File Name:    TranslateUtil.java
 *
 * File Desc:    �����ļ���ȡ������
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
 * History:      2013-06-26 created by Gxx
 */
package com.gxx.record.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * �����ļ���ȡ������
 *
 * @author Gxx
 * @version 1.0
 */
public class TranslateUtil
{
    private static TranslateUtil instance;

    public static TranslateUtil getInstance()
    {
        if (null == instance)
        {
            instance = new TranslateUtil();
        }
        return instance;
    }

    private static String propertyRoute = "translate.properties";

    static Properties prop;

    private TranslateUtil()
    {
        refresh();
    }

    /**
     * Ͷ���е����û���ˢ��
     */
    public static void refresh()
    {
        // 1 ��ȡproperties�ļ�
        URL configUrl = Thread.currentThread().getContextClassLoader().getResource(propertyRoute);
        if (null == configUrl)
        {
            throw new RuntimeException("�Ҳ��������ļ�:" + propertyRoute);
        }
        // 2 ���ļ�URLװ��Ϊproperties��
        prop = new Properties();
        InputStream configIs = null;
        try
        {
            configIs = configUrl.openStream();
            prop.load(configIs);
        } catch (Exception ex)
        {
            throw new RuntimeException("0006", ex);
        } finally
        {
            if (configIs != null)
            {
                try
                {
                    configIs.close();
                } catch (IOException e)
                {
                    throw new RuntimeException("�����ļ����ر��쳣!");
                }
            }
        }
    }

    /**
     * ��ȡ����
     *
     * @param key
     * @return
     */
    public String getProperty(String key)
    {
        return prop.getProperty(key);
    }
}
