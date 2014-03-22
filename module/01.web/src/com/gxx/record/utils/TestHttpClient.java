package com.gxx.record.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.HttpURLConnection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * User: Gxx
 * Time: 2014-02-20 15:04
 */
public class TestHttpClient
{
    public static void main(String[] param) throws Exception{
        //BaseUtils.loggerOut(grabSN189, "余额查询");
        PostMethod method = new PostMethod("http://localhost/weixin/record-forum/weixin.jsp?hello=xx3xx");
        String respStr = connect(method);

        System.out.println("respStr=" + respStr);
    }

    /**
     * 与服务器通讯
     */
    public static String connect(PostMethod method) throws Exception
    {
        InputStream is = null;
        BufferedReader br = null;
        try
        {
            HttpClient client = new HttpClient();
            int returnCode = client.executeMethod(method);

            if (HttpURLConnection.HTTP_OK != returnCode)
            {
                //logger.error("与服务器交互发生异常,returnCode=" + returnCode);
            }
            is = method.getResponseBodyAsStream();
            br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
            StringBuffer response = new StringBuffer();
            String readLine;
            while (((readLine = br.readLine()) != null))
            {
                response.append(readLine + "\r\n");
            }
            return response.toString();
        } catch (Exception e)
        {
            //logger.error("与服务器交互发生异常~", e);
            throw new RuntimeException("与服务器交互发生异常~", e);
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
                //logger.error("与服务器交互发生异常~", e);
            }
        }
    }
}
