/**
 * File Name:    ScoreLog.java
 *
 * File Desc:    积分变化日志实体
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

/**
 * 积分变化日志实体
 * @author Gxx
 * @version 1.0
 */
public class ScoreLog
{
    int id;
    int actionLogId;
    int userId;
    String requestDate;
    String requestTime;
    int oldScore;
    int changeScore;
    int newScore;

    public ScoreLog(int id, int actionLogId, int userId, String requestDate, String requestTime, int oldScore, int changeScore, int newScore)
    {
        this.id = id;
        this.actionLogId = actionLogId;
        this.userId = userId;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        this.oldScore = oldScore;
        this.changeScore = changeScore;
        this.newScore = newScore;
    }

    public ScoreLog(int actionLogId, int userId, String requestDate, String requestTime, int oldScore, int changeScore, int newScore)
    {
        this.actionLogId = actionLogId;
        this.userId = userId;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        this.oldScore = oldScore;
        this.changeScore = changeScore;
        this.newScore = newScore;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getActionLogId()
    {
        return actionLogId;
    }

    public void setActionLogId(int actionLogId)
    {
        this.actionLogId = actionLogId;
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

    public int getOldScore()
    {
        return oldScore;
    }

    public void setOldScore(int oldScore)
    {
        this.oldScore = oldScore;
    }

    public int getChangeScore()
    {
        return changeScore;
    }

    public void setChangeScore(int changeScore)
    {
        this.changeScore = changeScore;
    }

    public int getNewScore()
    {
        return newScore;
    }

    public void setNewScore(int newScore)
    {
        this.newScore = newScore;
    }
}