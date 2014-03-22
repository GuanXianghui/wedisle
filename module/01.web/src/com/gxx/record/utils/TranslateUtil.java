/**
 * File Name:    TranslateUtil.java
 *
 * File Desc:    翻译文件读取工具类
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
 * 翻译文件读取工具类
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
     * 投机有道配置缓存刷新
     */
    public static void refresh()
    {
        // 1 读取properties文件
        URL configUrl = Thread.currentThread().getContextClassLoader().getResource(propertyRoute);
        if (null == configUrl)
        {
            throw new RuntimeException("找不到配置文件:" + propertyRoute);
        }
        // 2 将文件URL装载为properties类
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
                    throw new RuntimeException("配置文件流关闭异常!");
                }
            }
        }
    }

    /**
     * 获取属性
     *
     * @param key
     * @return
     */
    public String getProperty(String key)
    {
        return prop.getProperty(key);
    }
}
