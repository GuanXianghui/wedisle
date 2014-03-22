/**
 * File Name:    CountUtils.java
 *
 * File Desc:    统计工具类
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
 * 统计工具类
 * @author Gxx
 * @version 1.0
 */
public class CountUtils
{
    /**
     * 得到帖子总数
     * @return
     */
    public static int getTopicCount() throws Exception
    {
        return DBSelect.queryTopicCountWithSectionIdAndDate(null, null);
    }

    /**
     * 得到今日帖子总数
     * @return
     */
    public static int getTopicCountWithToday() throws Exception
    {
        Date date = new Date();
        String dateStr = ServiceDataUtil.getDate(date);
        return DBSelect.queryTopicCountWithSectionIdAndDate(null, dateStr);
    }

    /**
     * 得到昨日帖子总数
     * @return
     */
    public static int getTopicCountWithYesterday() throws Exception
    {
        Date yesterday = ServiceDataUtil.getYesterday(new Date());
        String dateStr = ServiceDataUtil.getDate(yesterday);
        return DBSelect.queryTopicCountWithSectionIdAndDate(null, dateStr);
    }

    /**
     * 查询用户总数
     * @return
     * @throws Exception
     */
    public static int getUserCount() throws Exception
    {
        return DBSelect.queryUserCount();
    }

    /**
     * 查 板块 的帖子总数
     * @param sectionId
     * @return
     */
    public static int getTopicCountWithSectionId(int sectionId) throws Exception
    {
        return DBSelect.queryTopicCountWithSectionIdAndDate(new Integer(sectionId), null);
    }

    /**
     * 查 板块 + 今天 的帖子总数
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
     * 根据 板块ID 查回帖总数
     * @param sectionId
     * @return
     * @throws Exception
     */
    public static int getReplyCountWithSectionId(int sectionId) throws Exception
    {
        return DBSelect.queryReplyCountWithSectionId(sectionId);
    }

    /**
     * 根据 用户ID + 条件 + 条件值 查所有帖子
     *
     * @param userId        用户ID
     * @param conditionType 条件
     * @param type          条件值
     * @return
     * @throws Exception
     */
    public static int getTopicCountByUserIdAndConditions(int userId, String conditionType, String type) throws Exception
    {
        return DBSelect.queryTopicsByUserIdAndConditions(userId, conditionType, type).size();
    }

    /**
     * 根据 用户ID 查所有回帖
     *
     * @param userId        用户ID
     * @return
     * @throws Exception
     */
    public static int getReplyCountByUserId(int userId) throws Exception
    {
        return DBSelect.queryRepliesByUserId(userId).size();
    }
}
