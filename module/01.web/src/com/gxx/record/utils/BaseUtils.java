/**
 * File Name:    BaseUtils.java
 *
 * File Desc:    ����������
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
 * ����������
 * @author Gxx
 * @version 1.0
 */
public class BaseUtils
{
    /**
     * �н���������� ���� true �������� false
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
     * ҳ����ʾ�������ڶ���ʱ��
     * 1��ǰ 1000 * 60 * 60 * 24 * 365
     * 1����ǰ 1000 * 60 * 60 * 24 * 30
     * 1��ǰ 1000 * 60 * 60 * 24
     * 1Сʱǰ 1000 * 60 * 60
     * 1����ǰ 1000 * 60
     * 1��ǰ 1000
     * @param dateTime
     * @return
     */
    public static String showTimeDistance(Date dateTime)
    {
        // ����ʱ��γ���============================
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
            return distance/year + "��ǰ";
        }
        if(distance > month)
        {
            return distance/month + "����ǰ";
        }
        if(distance > day)
        {
            return distance/day + "��ǰ";
        }
        if(distance > hour)
        {
            return distance/hour + "Сʱǰ";
        }
        if(distance > minute)
        {
            return distance/minute + "����ǰ";
        }
        if(distance > second)
        {
            return distance/second + "��ǰ";
        }
        return "�ո�";
    }

    /**
     * ��ʼ���������ϵ�¥��
     * @param replies
     * @param order 1���߿�˳�� 2����
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
                    reply.setFloor((i+1)+"¥");
                } else
                {
                    reply.setFloor((replies.size()-i)+"¥");
                }
            }
        }
    }

    /**
     * �õ��ַ����ֽڳ���
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
     * �������� �ӻ���
     * �ӻ��ֵĶ�������¼(һ��һ�� +2��) ����(һ�� +2��) ����(һ�� +1�� ���� +2��)
     * @param actionLog
     */
    public synchronized static void addScore(ActionLog actionLog) throws Exception
    {
        Date date = new Date();
        String requestDate = ServiceDataUtil.getDate(date);
        String requestTime = ServiceDataUtil.getTime(date);
        // �ϵĻ���
        int oldScore = DBSelect.getUserById(actionLog.getUserId()).getScore();

        // ��¼(һ��һ�� +2��)
        if(ActionType.LOGIN.equals(actionLog.getActionType()))
        {
            int loginCount = 0;
            // �����û���¼�Ĵ���
            loginCount = DBSelect.queryActionLogCountByUserAndTypeAndDate(actionLog.getUserId(), actionLog.getActionType(), actionLog.getRequestDate());
            // ����ǵ�һ�ε�¼ Ϊ�û��ӻ���
            if(loginCount == 1)
            {
                // �仯����
                int changeScore = Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.LOGIN_ADD_SCORE));
                // �»���
                int newScore = oldScore + changeScore;
                // Ϊ�û��ӻ���
                DBUpdate.addScore(actionLog.getUserId(), newScore);
                // ������ֱ仯��־
                ScoreLog scoreLog = new ScoreLog(actionLog.getId(), actionLog.getUserId(), requestDate, requestTime, oldScore, changeScore, newScore);
                // �������ֱ仯��־
                DBUpdate.addScoreLog(scoreLog);
            }
        }

        // ����(һ�� +2��)
        if(ActionType.CREATE_TOPIC.equals(actionLog.getActionType()))
        {
            // �仯����
            int changeScore = Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.CREATE_TOPIC_ADD_SCORE));
            // �»���
            int newScore = oldScore + changeScore;
            // Ϊ�û��ӻ���
            DBUpdate.addScore(actionLog.getUserId(), newScore);
            // ������ֱ仯��־
            ScoreLog scoreLog = new ScoreLog(actionLog.getId(), actionLog.getUserId(), requestDate, requestTime, oldScore, changeScore, newScore);
            // �������ֱ仯��־
            DBUpdate.addScoreLog(scoreLog);
        }

        // ����(һ�� +1�� ���� +2��)
        if(ActionType.REPLY_TOPIC.equals(actionLog.getActionType()))
        {
            // ���� �仯����
            int changeScore = Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.REPLY_TOPIC_ADD_SCORE));
            // ���� �»���
            int newScore = oldScore + changeScore;
            // ���� Ϊ�û��ӻ���
            DBUpdate.addScore(actionLog.getUserId(), newScore);
            // ���� ������ֱ仯��־
            ScoreLog scoreLog = new ScoreLog(actionLog.getId(), actionLog.getUserId(), requestDate, requestTime, oldScore, changeScore, newScore);
            // ���� �������ֱ仯��־
            DBUpdate.addScoreLog(scoreLog);

            // ���������û� ����
            User topicUser = DBSelect.getUserById(DBSelect.getTopicById(actionLog.getTopicId()).getUserId());
            // ���� �ϵĻ���
            int oldScore2 = topicUser.getScore();
            // ���� �仯����
            int changeScore2 = Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.REPLY_TOPIC_USER_ADD_SCORE));
            // ���� �»���
            int newScore2 = oldScore2 + changeScore2;
            // ���� Ϊ�û��ӻ���
            DBUpdate.addScore(topicUser.getId(), newScore2);
            // ���� ������ֱ仯��־
            ScoreLog scoreLog2 = new ScoreLog(actionLog.getId(), topicUser.getId(), requestDate, requestTime, oldScore2, changeScore2, newScore2);
            // ���� �������ֱ仯��־
            DBUpdate.addScoreLog(scoreLog2);
        }
    }

    /**
     * ����������
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
