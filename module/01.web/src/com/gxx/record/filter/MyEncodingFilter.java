/**
 * File Name:    MyEncodingFilter.java
 *
 * File Desc:    ���������
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
 * History:      2013-06-20 created by Gxx
 */
package com.gxx.record.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ���������
 * @author Gxx
 * @version 1.0
 */
public class MyEncodingFilter implements Filter
{

    private FilterConfig filterConfig = null;

    /**
     * ���캯��
     * @param filterConfig
     * @throws javax.servlet.ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException
    {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        //���������ַ�����
        request.setCharacterEncoding("UTF-8");

        //ִ����һ��������
        filterChain.doFilter(req, res);
    }

    //����
    public void destroy()
    {
        this.filterConfig = null;
    }

    public FilterConfig getFilterConfig()
    {
        return filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }
}
