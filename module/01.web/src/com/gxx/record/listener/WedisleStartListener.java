package com.gxx.record.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 启动监听器
 * Create by Gxx
 * Time: 2014-03-19 14:56
 */
public class WedisleStartListener implements ServletContextListener
{
    /**
     * 线程
     */
    private WedisleStartThread startThread;

    /**
     * 初始化
     * @param servletContextEvent
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (startThread == null) {
            startThread = new WedisleStartThread();
            startThread.start(); // servlet 上下文初始化时启动 socket
        }
    }

    /**
     * 结束
     * @param servletContextEvent
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (startThread != null && startThread.isInterrupted()) {
            startThread.interrupt();
        }
    }
}
