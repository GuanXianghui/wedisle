/**
 * File Name:    HttpClientUtils.java
 *
 * File Desc:    HttpClient������
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
 * History:      2013-04-24 created by Gxx
 */
package com.gxx.record.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;

/**
 * HttpClient������
 * @author Gxx
 * @version 1.0
 */
public class HttpClientUtils
{
    /**
     * ��־��¼��
     */
    static Logger logger = Logger.getLogger(HttpClientUtils.class);

    /**
     * UTF-8
     */
    public static final String ENCODE_UTF8 = "UTF-8";

    /**
     * UTF-8
     */
    public static final String ENCODE_UTF16 = "UTF-16";

    /**
     * UTF-8
     */
    public static final String ENCODE_GB2312 = "GB2312";

    /**
     * GBK
     */
    public static final String ENCODE_GBK = "GBK";

    /**
     * GBK
     */
    public static final String ENCODE_ISO = "ISO-8859-1";

    /**
     * httpclient post ����
     *
     * @param url            �����ַ
     * @param xml            ����xml����
     * @param requestEncode  �������
     * @param responseEncode ���ܱ���
     * @return
     */
    public static String post(String url, String xml, String requestEncode, String responseEncode)
    {
        logger.info("httpclient post ���� ��ʼ==============");
        logger.info("url=" + url);
        //PropertyUtil.getInstance().refresh();
        logger.info("requestXml=" + xml);
        logger.debug("û��ʹ��֤�飬Э��ע��443�˿���������֤�飬��ʼ=================================");
        Protocol easyHttps1 = new Protocol("https", new EasySSLProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", easyHttps1);
        logger.debug("û��ʹ��֤�飬Э��ע��443�˿���������֤�飬����=================================");

        String resultStr = null;
        PostMethod postMethod = new PostMethod(url);//����POST������ʵ��
        postMethod.setRequestBody(xml);//������ֵ����postMethod��
        postMethod.addRequestHeader("Content", "text/xml,charset=" + requestEncode + "");
        HttpClient httpClient = new HttpClient();//����HttpClient��ʵ��
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, requestEncode);

        try
        {
            int code = httpClient.executeMethod(postMethod);
            logger.info("Response status code: " + code);//����200Ϊ�ɹ�
            resultStr = new String(postMethod.getResponseBodyAsString().getBytes(), responseEncode);
            logger.info("resultStr=" + resultStr);
        } catch (Exception e)
        {
            logger.error("HttpClient�쳣����~", e);
        } finally
        {
            postMethod.releaseConnection();
        }

        logger.info("httpclient post ���� ����==============");
        return resultStr;
    }

    /**
     * httpclient post url ����
     * @param url            �����ַ
     * @param requestEncode  �������
     * @param responseEncode ���ܱ���
     * @return
     */
    public static String postUrl(String url, String requestEncode, String responseEncode)
    {
        logger.info("httpclient post url ���� ��ʼ==============");
        logger.info("url=" + url);
        logger.debug("û��ʹ��֤�飬Э��ע��443�˿���������֤�飬��ʼ=================================");
        Protocol easyHttps1 = new Protocol("https", new EasySSLProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", easyHttps1);
        logger.debug("û��ʹ��֤�飬Э��ע��443�˿���������֤�飬����=================================");

        String resultStr = null;
        PostMethod postMethod = new PostMethod(url);//����POST������ʵ��
        HttpClient httpClient = new HttpClient();//����HttpClient��ʵ��
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, requestEncode);

        try
        {
            int code = httpClient.executeMethod(postMethod);
            logger.info("Response status code: " + code);//����200Ϊ�ɹ�
            resultStr = new String(postMethod.getResponseBodyAsString().getBytes(), responseEncode);
            logger.info("resultStr=" + resultStr);
        } catch (Exception e)
        {
            logger.error("HttpClient�쳣����~", e);
        } finally
        {
            postMethod.releaseConnection();
        }

        logger.info("httpclient post url ���� ����==============");
        return resultStr;
    }

    /**
     * httpclient post props ����
     * @param url
     * @param props
     * @param requestEncode
     * @param responseEncode
     * @return
     */
    public static String postProps(String url, Properties props, String requestEncode, String responseEncode) throws Exception
    {
        PostMethod method = new PostMethod(url);
        Enumeration enums = props.keys();
        while (enums.hasMoreElements())
        {
            String key = (String)enums.nextElement();
            String value = props.getProperty(key);
            if(StringUtils.isNotBlank(value))
            {
                method.addParameter(key, value);
            }
        }
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,requestEncode);
        return connect(method, responseEncode);
    }

    /**
     * �������ͨѶ
     * @param method
     * @param responseEncode
     * @return
     * @throws Exception
     */
    public static String connect(PostMethod method, String responseEncode) throws Exception
    {
        InputStream is = null;
        BufferedReader br = null;
        try
        {
            HttpClient client = new HttpClient();
            int returnCode = client.executeMethod(method);

            if (HttpURLConnection.HTTP_OK != returnCode)
            {
                logger.error("����������������쳣,returnCode=" + returnCode);
            }
            is = method.getResponseBodyAsStream();
            br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), responseEncode));
            StringBuffer response = new StringBuffer();
            String readLine;
            while (((readLine = br.readLine()) != null))
            {
                response.append(readLine);
            }
            //logger.info("response.toString=====>" + response.toString());
            return response.toString();
        } catch (Exception e)
        {
            logger.error("����������������쳣~", e);
            throw new RuntimeException("����������������쳣~", e);
        } finally
        {
            try
            {
                if (null != method)
                {
                    method.releaseConnection();
                }
                if (null != is)
                {
                    is.close();
                }
                if (null != br)
                {
                    br.close();
                }
            } catch (Exception e)
            {
                logger.error("����������������쳣~", e);
            }
        }
    }
}