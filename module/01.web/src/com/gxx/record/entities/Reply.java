/**
 * File Name:    Topic.java
 *
 * File Desc:
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
 * History:      2013-06-22 created by Gxx
 */
package com.gxx.record.entities;

/**
 * @author Gxx
 * @version 1.0
 */
public class Reply
{
    int id;
    int topicId;
    int userId;
    String content;
    String requestDate;
    String requestTime;
    boolean isDelete;
    String floor;//Â¥²ã

    public Reply(int id, int topicId, int userId, String content,String requestDate, String requestTime, boolean isDelete)
    {
        this.id = id;
        this.topicId = topicId;
        this.userId = userId;
        this.content = content;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        this.isDelete = isDelete;
    }

    public Reply(int topicId, int userId, String content,String requestDate, String requestTime, boolean isDelete)
    {
        this.topicId = topicId;
        this.userId = userId;
        this.content = content;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        this.isDelete = isDelete;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getTopicId()
    {
        return topicId;
    }

    public void setTopicId(int topicId)
    {
        this.topicId = topicId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
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

    public boolean isDelete()
    {
        return isDelete;
    }

    public void setDelete(boolean delete)
    {
        isDelete = delete;
    }

    public String getFloor()
    {
        return floor;
    }

    public void setFloor(String floor)
    {
        this.floor = floor;
    }
}
