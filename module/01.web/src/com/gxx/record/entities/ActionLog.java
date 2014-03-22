/**
 * File Name:    ActionLog.java
 *
 * File Desc:    动作日志实体
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
 * History:      2013-06-29 created by Gxx
 */
package com.gxx.record.entities;

import com.gxx.record.enums.ActionType;

/**
 * 动作日志实体
 * @author Gxx
 * @version 1.0
 */
public class ActionLog
{
    int id;
    ActionType actionType;
    int userId;
    String requestDate;
    String requestTime;
    int topicId;
    int replyId;

    public ActionLog(int id, ActionType actionType, int userId, String requestDate, String requestTime, int topicId, int replyId)
    {
        this.id = id;
        this.actionType = actionType;
        this.userId = userId;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        this.topicId = topicId;
        this.replyId = replyId;
    }

    public ActionLog(ActionType actionType, int userId, String requestDate, String requestTime, int topicId, int replyId)
    {
        this.actionType = actionType;
        this.userId = userId;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        this.topicId = topicId;
        this.replyId = replyId;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public ActionType getActionType()
    {
        return actionType;
    }

    public void setActionType(ActionType actionType)
    {
        this.actionType = actionType;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getRequestDate()
    {
        return requestDate;
    }

    public void setRequestDate(String requestDate)
    {
        this.requestDate = requestDate;
    }

    public String getRequestTime()
    {
        return requestTime;
    }

    public void setRequestTime(String requestTime)
    {
        this.requestTime = requestTime;
    }

    public int getTopicId()
    {
        return topicId;
    }

    public void setTopicId(int topicId)
    {
        this.topicId = topicId;
    }

    public int getReplyId()
    {
        return replyId;
    }

    public void setReplyId(int replyId)
    {
        this.replyId = replyId;
    }
}
