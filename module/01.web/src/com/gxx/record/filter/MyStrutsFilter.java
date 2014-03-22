/**
 * File Name:    MyStrutsFilter.java
 *
 * File Desc:
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

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Gxx
 * @version 1.0
 */
public class MyStrutsFilter extends StrutsPrepareAndExecuteFilter
{

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        //²»¹ýÂËµÄurl
        String url = request.getRequestURI();
        if (-1 != url.indexOf("/ueditor/jsp/")) {
            chain.doFilter(req, res);
        }else{
            super.doFilter(req, res, chain);
        }
    }
}
