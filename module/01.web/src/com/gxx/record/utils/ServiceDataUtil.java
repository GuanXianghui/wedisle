/**
 * File Name:    ServiceDataUtil.java
 *
 * File Desc:    ���÷������ݹ�����
 *
 * Product AB:   NEWTRADE_1_0_0
 *
 * Product Name: NEWTRADE
 *
 * Module Name:  Web.Util
 *
 * Module AB:    Web.Util
 *
 * Author:       ����
 *
 * History:      2008-10-30 created by ����
 */
package com.gxx.record.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ���÷������ݹ�����
 *
 * @author chenqi32
 * @version 1.0
 */
public class ServiceDataUtil
{
    static Logger logger = Logger.getLogger(ServiceDataUtil.class);

    /**
     * ��ʽ��ʱ��ΪDate����
     *
     * @param srcDate    ԭʱ��
     * @param srcPattern ԭģʽ
     * @return ��ʽ����ʱ��
     */
    public static Date parseDateTime(String srcDate, String srcPattern)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(srcPattern);
            return sdf.parse(srcDate);
        } catch (Exception e)
        {
            logger.error("�쳣����~", e);
            return null;
        }
    }


    /**
     * ��ʽ��ʱ��Ϊ�ַ�������
     *
     * @param srcDate    ԭʱ��
     * @param srcPattern ԭģʽ
     * @param dstPattern Ŀ��ģʽ
     * @return ��ʽ����ʱ��
     */
    public static String formatDateTime(Object srcDate, String srcPattern, String dstPattern)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(srcPattern == null ? "yyyyMMdd" : srcPattern);
            Date _date = null;
            if (srcDate == null)
            {
                return "";
            }
            if (srcDate instanceof String)
            {
                _date = sdf.parse(srcDate.toString());
            } else if (srcDate instanceof Date)
            {
                _date = (Date) srcDate;
            }
            sdf = new SimpleDateFormat(dstPattern);
            return sdf.format(_date);
        } catch (Exception e)
        {
            logger.error("�쳣����~", e);
            return null;
        }
    }

    /**
     * add chenqi32
     * �������ַ���ת��ΪDate
     *
     * @param date �����ַ���
     * @return ����
     */
    public static Date getDate(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (StringUtils.isBlank(date))
        {
            return null;
        }
        try
        {
            return sdf.parse(date);
        } catch (ParseException e)
        {
            logger.error("�쳣����~", e);
            return null;
        }
    }

    /**
     * add chenqi32
     * ��ֱ��dateTimeת����date
     *
     * @param dateTime ֱ��ʱ��yyyyMMddHHmmss
     * @return Date
     */
    public static Date getDateTime(String dateTime)
    {
        if (StringUtils.isBlank(dateTime))
        {
            return null;
        }
        SimpleDateFormat sdf;
        if (dateTime.indexOf("-") > 0)
        {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else
        {
            sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        }
        try
        {
            sdf.setLenient(false);
            return sdf.parse(dateTime);
        } catch (ParseException e)
        {
            logger.error("�쳣����~", e);
            return null;
        }
    }

    /**
     * add chenqi32
     *
     * @param time ֱ���ַ���ʽʱ��HHmmss
     * @return DATE
     */
    public static Date getTime(String time)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        if (StringUtils.isBlank(time))
        {
            return null;
        }
        try
        {
            return sdf.parse(time);
        } catch (ParseException e)
        {
            logger.error("�쳣����~", e);
            return null;
        }
    }

    /**
     * ������ת���ɱ�׼��ʽyyyy-mm-dd hh:mm:ss
     *
     * @param dateTime ʱ��
     * @return ��׼��ʽ�ַ���
     */
    public static String getDefaultDateTime(Date dateTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            return sdf.format(dateTime);
        } catch (Exception e)
        {
            logger.error("�쳣����~", e);
            return null;
        }
    }


    /**
     * add chenqi32
     * ��ֱ�����ڷ��س�֧�����������ַ���
     *
     * @param dateTime ֱ��dateTime�����ַ���
     * @return ֧���������������ַ���
     */
    public static String getDateTime(Date dateTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try
        {
            return sdf.format(dateTime);
        } catch (Exception e)
        {
            logger.error("�쳣����~", e);
            return null;
        }
    }

    /**
     * add chenqi32
     * ��ֱ�����ڷ��س�֧�����������ַ���
     *
     * @param date ֱ��date�����ַ���
     * @param time ֱ��time�����ַ���
     * @return ֧���������������ַ���
     */
    public static Date getDateTime(String date, String time)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try
        {
            return sdf.parse(date + time);
        } catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * ������ת��Ϊֱ�������ַ���
     *
     * @param date ����
     * @return ֱ�������ַ���
     */
    public static String getDate(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (date == null)
        {
            return "";
        }
        try
        {
            return sdf.format(date);
        } catch (Exception e)
        {
            logger.error("�쳣����~", e);
            return "";
        }
    }

    /**
     * ������ת��Ϊ���ַ�������ʽ��yyMMdd
     *
     * @param date ����
     * @return �ַ���
     */
    public static String getShortDate(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        if (date == null)
        {
            return null;
        }
        try
        {
            return sdf.format(date);
        } catch (Exception e)
        {
            logger.error("�쳣����~", e);
            return null;
        }
    }

    /**
     * ������ת��Ϊ���ַ�������ʽ��yyyy-MM-dd
     *
     * @param date ����
     * @return �ַ���
     */
    public static String getLongDate(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null)
        {
            return null;
        }
        try
        {
            return sdf.format(date);
        } catch (Exception e)
        {
            logger.error("�쳣����~", e);
            return null;
        }
    }

    /**
     * ��ʱ��ת��Ϊֱ�������ַ���
     *
     * @param date ʱ��
     * @return ֱ�������ַ���
     */
    public static String getTime(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        if (date == null)
        {
            return "";
        }
        try
        {
            return sdf.format(date);
        } catch (Exception e)
        {
            logger.error("�쳣����~", e);
            return "";
        }
    }

    /**
     * add leo
     * �������ַ���ת��ΪDate��Ȼʱ��
     *
     * @param date �����ַ���
     * @return ��Ȼʱ��
     */
    public static String getFreeTime(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
        if (date == null)
        {
            return null;
        }
        try
        {
            return sdf.format(date);
        } catch (Exception e)
        {
            logger.error("�쳣����~", e);
            return null;
        }
    }

    /**
     * add leo
     * �������ַ���ת��ΪDate��Ȼ���ڵ���
     *
     * @param date �����ַ���
     * @return ��Ȼ���ڵ���
     */
    public static String getFreeDay(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        if (date == null)
        {
            return null;
        }
        try
        {
            return sdf.format(date);
        } catch (Exception e)
        {
            logger.error("�쳣����~", e);
            return null;
        }
    }

    /**
     * ת�����й���������ʱ��
     * @param date
     * @return
     */
    public static String getCNDateTime(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��");
        if (date == null)
        {
            return null;
        }
        try
        {
            return sdf.format(date);
        } catch (Exception e)
        {
            logger.error("�쳣����~", e);
            return null;
        }
    }

    /**
     * �õ����ڵ�ǰһ��
     * @param date
     * @return
     */
    public static Date getYesterday(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        return yesterday;
    }

    /**
     * �õ���ǰ����
     * @return
     */
    public static String getNowDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    /**
     * �õ���ǰ����
     * @return
     */
    public static String getNowTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        return sdf.format(new Date());
    }
}
