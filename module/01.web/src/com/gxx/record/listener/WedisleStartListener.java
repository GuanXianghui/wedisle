package com.gxx.record.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ����������
 * Create by Gxx
 * Time: 2014-03-19 14:56
 */
public class WedisleStartListener implements ServletContextListener
{
    /**
     * �߳�
     */
    private WedisleStartThread startThread;

    /**
     * ��ʼ��
     * @param servletContextEvent
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (startThread == null) {
            startThread = new WedisleStartThread();
            startThread.start(); // servlet �����ĳ�ʼ��ʱ���� socket
        }
    }

    /**
     * ����
     * @param servletContextEvent
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (startThread != null && startThread.isInterrupted()) {
            startThread.interrupt();
        }
    }
}
