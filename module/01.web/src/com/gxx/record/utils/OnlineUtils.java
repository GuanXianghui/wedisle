/**
 * File Name:    OnlineUtils.java
 *
 * File Desc:    用户在线统计工具类
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
 * 用户在线统计工具类
 * @author Gxx
 * @version 1.0
 */
public class OnlineUtils
{
    /**
     * 在线用户Map
     * Key : 用户id
     * Value : 用户上次活动时间
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
     * 刷新用户活动时间
     * @param id
     */
    public void refreshUser(int id)
    {
        String dateTime = ServiceDataUtil.getDateTime(new Date());
        userStates.put(""+id, dateTime);
    }

    /**
     * 用户登出
     * @param id
     */
    public void logOutUser(int id)
    {
        userStates.remove(""+id);

        // 记录用户登出动作日志
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
     * 查用户在线人数
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
     * 判时间超时
     * 超过4分钟(4*60*1000)没反应就算离线
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
     * 判用户是否在线
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