/**
 * File Name:    Record.java
 *
 * File Desc:    记录实体
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
 * History:      2013-06-30 created by Gxx
 */
package com.gxx.record.entities;

/**
 * 记录实体
 * @author Gxx
 * @version 1.0
 */
public class Record
{
    int id;
    int userId;
    String groups;
    String title;
    String content;
    String tag;
    String requestDate;
    String requestTime;
    boolean isDelete;

    public Record(int id, int userId, String groups, String title, String content, String tag, String requestDate, String requestTime, boolean isDelete)
    {
        this.id = id;
        this.userId = userId;
        this.groups = groups;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        this.isDelete = isDelete;
    }

    public Record(int userId, String groups, String title, String content, String tag, String requestDate, String requestTime, boolean isDelete)
    {
        this.userId = userId;
        this.groups = groups;
        this.title = title;
        this.content = content;
        this.tag = tag;
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

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getGroups()
    {
        return groups;
    }

    public void setGroups(String groups)
    {
        this.groups = groups;
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

    public boolean isDelete()
    {
        return isDelete;
    }

    public void setDelete(boolean delete)
    {
        isDelete = delete;
    }
}
