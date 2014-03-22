/**
 * File Name:    CountUtils.java
 *
 * File Desc:    ͳ�ƹ�����
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
 * History:      2013-06-26 created by Gxx
 */
package com.gxx.record.utils;

import com.gxx.record.dao.DBSelect;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * ͳ�ƹ�����
 * @author Gxx
 * @version 1.0
 */
public class CountUtils
{
    /**
     * �õ���������
     * @return
     */
    public static int getTopicCount() throws Exception
    {
        return DBSelect.queryTopicCountWithSectionIdAndDate(null, null);
    }

    /**
     * �õ�������������
     * @return
     */
    public static int getTopicCountWithToday() throws Exception
    {
        Date date = new Date();
        String dateStr = ServiceDataUtil.getDate(date);
        return DBSelect.queryTopicCountWithSectionIdAndDate(null, dateStr);
    }

    /**
     * �õ�������������
     * @return
     */
    public static int getTopicCountWithYesterday() throws Exception
    {
        Date yesterday = ServiceDataUtil.getYesterday(new Date());
        String dateStr = ServiceDataUtil.getDate(yesterday);
        return DBSelect.queryTopicCountWithSectionIdAndDate(null, dateStr);
    }

    /**
     * ��ѯ�û�����
     * @return
     * @throws Exception
     */
    public static int getUserCount() throws Exception
    {
        return DBSelect.queryUserCount();
    }

    /**
     * �� ��� ����������
     * @param sectionId
     * @return
     */
    public static int getTopicCountWithSectionId(int sectionId) throws Exception
    {
        return DBSelect.queryTopicCountWithSectionIdAndDate(new Integer(sectionId), null);
    }

    /**
     * �� ��� + ���� ����������
     * @param sectionId
     * @return
     */
    public static int getTopicCountWithSectionIdAndToday(int sectionId) throws Exception
    {
        Date date = new Date();
        String dateStr = ServiceDataUtil.getDate(date);
        return DBSelect.queryTopicCountWithSectionIdAndDate(new Integer(sectionId), dateStr);
    }

    /**
     * ���� ���ID ���������
     * @param sectionId
     * @return
     * @throws Exception
     */
    public static int getReplyCountWithSectionId(int sectionId) throws Exception
    {
        return DBSelect.queryReplyCountWithSectionId(sectionId);
    }

    /**
     * ���� �û�ID + ���� + ����ֵ ����������
     *
     * @param userId        �û�ID
     * @param conditionType ����
     * @param type          ����ֵ
     * @return
     * @throws Exception
     */
    public static int getTopicCountByUserIdAndConditions(int userId, String conditionType, String type) throws Exception
    {
        return DBSelect.queryTopicsByUserIdAndConditions(userId, conditionType, type).size();
    }

    /**
     * ���� �û�ID �����л���
     *
     * @param userId        �û�ID
     * @return
     * @throws Exception
     */
    public static int getReplyCountByUserId(int userId) throws Exception
    {
        return DBSelect.queryRepliesByUserId(userId).size();
    }
}
