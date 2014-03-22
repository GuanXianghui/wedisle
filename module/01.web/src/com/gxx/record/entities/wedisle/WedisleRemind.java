package com.gxx.record.entities.wedisle;

/**
 * 提醒表
 * Create by Gxx
 * Time: 2014-3-15 21:41
 */
public class WedisleRemind
{
    int id;
    int userId;
    String event;
    String date;
    String remindType;
    String remindDate;
    String remindTime;

    /**
     * 构造函数 查询时使用
     * @param id
     * @param userId
     * @param event
     * @param date
     * @param remindType
     * @param remindDate
     * @param remindTime
     */
    public WedisleRemind(int id, int userId, String event, String date, String remindType, String remindDate, String remindTime) {
        this.id = id;
        this.userId = userId;
        this.event = event;
        this.date = date;
        this.remindType = remindType;
        this.remindDate = remindDate;
        this.remindTime = remindTime;
    }

    /**
     * 构造函数 新增时使用
     * @param userId
     * @param event
     * @param date
     * @param remindType
     * @param remindDate
     * @param remindTime
     */
    public WedisleRemind(int userId, String event, String date, String remindType, String remindDate, String remindTime) {
        this.userId = userId;
        this.event = event;
        this.date = date;
        this.remindType = remindType;
        this.remindDate = remindDate;
        this.remindTime = remindTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemindType() {
        return remindType;
    }

    public void setRemindType(String remindType) {
        this.remindType = remindType;
    }

    public String getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(String remindDate) {
        this.remindDate = remindDate;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }
}
