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
public class Topic
{
    int id;
    int sectionId;
    int userId;
    String title;
    String content;
    String tag;
    String requestDate;
    String requestTime;
    boolean isGood;
    int viewTimes;
    boolean isDelete;

    public Topic(int id, int sectionId, int userId, String title, String content, String tag, String requestDate,
                 String requestTime, boolean good, int viewTimes, boolean isDelete)
    {
        this.id = id;
        this.sectionId = sectionId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        isGood = good;
        this.viewTimes = viewTimes;
        this.isDelete = isDelete;
    }

    public Topic(int sectionId, int userId, String title, String content, String tag, String requestDate,
                 String requestTime, boolean good, int viewTimes, boolean isDelete)
    {
        this.sectionId = sectionId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        isGood = good;
        this.viewTimes = viewTimes;
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

    public int getSectionId()
    {
        return sectionId;
    }

    public void setSectionId(int sectionId)
    {
        this.sectionId = sectionId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
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

    public boolean isGood()
    {
        return isGood;
    }

    public void setGood(boolean good)
    {
        isGood = good;
    }

    public int getViewTimes()
    {
        return viewTimes;
    }

    public void setViewTimes(int viewTimes)
    {
        this.viewTimes = viewTimes;
    }

    public boolean isDelete()
    {
        return isDelete;
    }

    public void setDelete(boolean delete)
    {
        isDelete = delete;
    }
}
