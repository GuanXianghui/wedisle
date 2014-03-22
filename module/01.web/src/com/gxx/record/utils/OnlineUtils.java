/**
 * File Name:    OnlineUtils.java
 *
 * File Desc:    �û�����ͳ�ƹ�����
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

import com.gxx.record.dao.DBUpdate;
import com.gxx.record.entities.ActionLog;
import com.gxx.record.enums.ActionType;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * �û�����ͳ�ƹ�����
 * @author Gxx
 * @version 1.0
 */
public class OnlineUtils
{
    /**
     * �����û�Map
     * Key : �û�id
     * Value : �û��ϴλʱ��
     */
    private Map userStates = new HashMap();

    private static OnlineUtils instance;

    public static OnlineUtils getInstance()
    {
        if(null == instance)
        {
            instance = new OnlineUtils();
        }
        return instance;
    }

    /**
     * ˢ���û��ʱ��
     * @param id
     */
    public void refreshUser(int id)
    {
        String dateTime = ServiceDataUtil.getDateTime(new Date());
        userStates.put(""+id, dateTime);
    }

    /**
     * �û��ǳ�
     * @param id
     */
    public void logOutUser(int id)
    {
        userStates.remove(""+id);

        // ��¼�û��ǳ�������־
        Date date = new Date();
        String logOutDate = ServiceDataUtil.getDate(date);
        String logOutTime = ServiceDataUtil.getTime(date);
        ActionType actionType = ActionType.LOGIN_OUT;
        ActionLog actionLog = new ActionLog(actionType, id, logOutDate, logOutTime, 0, 0);
        try
        {
            DBUpdate.addActionLog(actionLog);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ���û���������
     * @return
     */
    public int getOnlineCount()
    {
        Iterator it = userStates.keySet().iterator();
        while (it.hasNext())
        {
            String key = (String)it.next();
            if(isTimeOverdue((String)userStates.get(key)))
            {
                logOutUser(Integer.parseInt(key));
            }
        }
        return userStates.size();
    }

    /**
     * ��ʱ�䳬ʱ
     * ����4����(4*60*1000)û��Ӧ��������
     * @param dateTime
     * @return
     */
    public boolean isTimeOverdue(String dateTime)
    {
        long dateTimeLong = ServiceDataUtil.getDateTime(dateTime).getTime();
        long now = new Date().getTime();
        return (now - dateTimeLong > (4*60*1000));
    }

    /**
     * ���û��Ƿ�����
     * @param userId
     * @return
     */
    public boolean isOnline(int userId)
    {
        String dateTime = (String)userStates.get(""+userId);
        if(null == dateTime || isTimeOverdue(dateTime))
        {
            return false;
        }
        return true;
    }
}