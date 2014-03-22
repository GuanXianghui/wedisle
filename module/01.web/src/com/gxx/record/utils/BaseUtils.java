/**
 * File Name:    BaseUtils.java
 *
 * File Desc:    基础工具类
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
import com.gxx.record.dao.DBUpdate;
import com.gxx.record.entities.ActionLog;
import com.gxx.record.entities.Reply;
import com.gxx.record.entities.ScoreLog;
import com.gxx.record.entities.User;
import com.gxx.record.enums.ActionType;
import com.gxx.record.interfaces.BaseInterface;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 基础工具类
 * @author Gxx
 * @version 1.0
 */
public class BaseUtils
{
    /**
     * 判今天或者昨天 返回 true 其他返回 false
     * @param date
     * @return
     */
    public static boolean isTodayOrYesterday(String date)
    {
        Date today = new Date();
        Date yesterday = ServiceDataUtil.getYesterday(today);
        String todayStr = ServiceDataUtil.getDate(today);
        String yesterdayStr = ServiceDataUtil.getDate(yesterday);
        if(todayStr.equals(date) || yesterdayStr.equals(date))
        {
            return true;
        }
        return false;
    }

    /**
     * 页面显示距离现在多少时间
     * 1年前 1000 * 60 * 60 * 24 * 365
     * 1个月前 1000 * 60 * 60 * 24 * 30
     * 1天前 1000 * 60 * 60 * 24
     * 1小时前 1000 * 60 * 60
     * 1分钟前 1000 * 60
     * 1秒前 1000
     * @param dateTime
     * @return
     */
    public static String showTimeDistance(Date dateTime)
    {
        // 定义时间段长度============================
        long year = 1000l * 60l * 60l * 24l * 365l;
        long month = 1000l * 60l * 60l * 24l * 30l;
        long day = 1000l * 60l * 60l * 24l;
        long hour = 1000l * 60l * 60l;
        long minute = 1000l * 60l;
        long second = 1000l;
        // ==========================================
        long now = new Date().getTime();
        long time = dateTime.getTime();
        long distance = now - time;
        if(distance > year)
        {
            return distance/year + "年前";
        }
        if(distance > month)
        {
            return distance/month + "个月前";
        }
        if(distance > day)
        {
            return distance/day + "天前";
        }
        if(distance > hour)
        {
            return distance/hour + "小时前";
        }
        if(distance > minute)
        {
            return distance/minute + "分钟前";
        }
        if(distance > second)
        {
            return distance/second + "秒前";
        }
        return "刚刚";
    }

    /**
     * 初始化回帖集合的楼层
     * @param replies
     * @param order 1或者空顺序 2倒序
     */
    public static void initReplyListFloors(List replies, String order)
    {
        boolean isAsc = StringUtils.isBlank(order) || "1".equals(order);
        if(null != replies && replies.size() > 0)
        {
            for(int i=0;i<replies.size();i++)
            {
                Reply reply = (Reply)replies.get(i);
                if(isAsc)
                {
                    reply.setFloor((i+1)+"楼");
                } else
                {
                    reply.setFloor((replies.size()-i)+"楼");
                }
            }
        }
    }

    /**
     * 得到字符串字节长度
     * @param str
     * @return
     */
    public static int getStrByteLength(String str)
    {
        if(null == str)
        {
            return 0;
        }
        str = str.replaceAll("[^\\x00-\\xff]" , "**");
        int length = str.length();
        return length;
    }

    /**
     * 动作发生 加积分
     * 加积分的动作：登录(一天一次 +2分) 发帖(一次 +2分) 回帖(一次 +1分 贴主 +2分)
     * @param actionLog
     */
    public synchronized static void addScore(ActionLog actionLog) throws Exception
    {
        Date date = new Date();
        String requestDate = ServiceDataUtil.getDate(date);
        String requestTime = ServiceDataUtil.getTime(date);
        // 老的积分
        int oldScore = DBSelect.getUserById(actionLog.getUserId()).getScore();

        // 登录(一天一次 +2分)
        if(ActionType.LOGIN.equals(actionLog.getActionType()))
        {
            int loginCount = 0;
            // 当天用户登录的次数
            loginCount = DBSelect.queryActionLogCountByUserAndTypeAndDate(actionLog.getUserId(), actionLog.getActionType(), actionLog.getRequestDate());
            // 如果是第一次登录 为用户加积分
            if(loginCount == 1)
            {
                // 变化积分
                int changeScore = Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.LOGIN_ADD_SCORE));
                // 新积分
                int newScore = oldScore + changeScore;
                // 为用户加积分
                DBUpdate.addScore(actionLog.getUserId(), newScore);
                // 构造积分变化日志
                ScoreLog scoreLog = new ScoreLog(actionLog.getId(), actionLog.getUserId(), requestDate, requestTime, oldScore, changeScore, newScore);
                // 新增积分变化日志
                DBUpdate.addScoreLog(scoreLog);
            }
        }

        // 发帖(一次 +2分)
        if(ActionType.CREATE_TOPIC.equals(actionLog.getActionType()))
        {
            // 变化积分
            int changeScore = Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.CREATE_TOPIC_ADD_SCORE));
            // 新积分
            int newScore = oldScore + changeScore;
            // 为用户加积分
            DBUpdate.addScore(actionLog.getUserId(), newScore);
            // 构造积分变化日志
            ScoreLog scoreLog = new ScoreLog(actionLog.getId(), actionLog.getUserId(), requestDate, requestTime, oldScore, changeScore, newScore);
            // 新增积分变化日志
            DBUpdate.addScoreLog(scoreLog);
        }

        // 回帖(一次 +1分 贴主 +2分)
        if(ActionType.REPLY_TOPIC.equals(actionLog.getActionType()))
        {
            // 回帖 变化积分
            int changeScore = Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.REPLY_TOPIC_ADD_SCORE));
            // 回帖 新积分
            int newScore = oldScore + changeScore;
            // 回帖 为用户加积分
            DBUpdate.addScore(actionLog.getUserId(), newScore);
            // 回帖 构造积分变化日志
            ScoreLog scoreLog = new ScoreLog(actionLog.getId(), actionLog.getUserId(), requestDate, requestTime, oldScore, changeScore, newScore);
            // 回帖 新增积分变化日志
            DBUpdate.addScoreLog(scoreLog);

            // 帖子所属用户 贴主
            User topicUser = DBSelect.getUserById(DBSelect.getTopicById(actionLog.getTopicId()).getUserId());
            // 贴主 老的积分
            int oldScore2 = topicUser.getScore();
            // 贴主 变化积分
            int changeScore2 = Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.REPLY_TOPIC_USER_ADD_SCORE));
            // 贴主 新积分
            int newScore2 = oldScore2 + changeScore2;
            // 贴主 为用户加积分
            DBUpdate.addScore(topicUser.getId(), newScore2);
            // 贴主 构造积分变化日志
            ScoreLog scoreLog2 = new ScoreLog(actionLog.getId(), topicUser.getId(), requestDate, requestTime, oldScore2, changeScore2, newScore2);
            // 贴主 新增积分变化日志
            DBUpdate.addScoreLog(scoreLog2);
        }
    }

    /**
     * 清楚特殊符号
     * @param str
     * @return
     */
    public static String clearStrSpecialSign(String str)
    {
        return str.replaceAll(",","")
                .replaceAll(":","")
                .replaceAll(";","")
                .replaceAll("#","")
                .replaceAll("\\\\","")
                .replaceAll("'","")
                .replaceAll("\\\"","");
    }
}
