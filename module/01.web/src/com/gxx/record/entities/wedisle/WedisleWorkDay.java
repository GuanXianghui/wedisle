package com.gxx.record.entities.wedisle;

/**
 * 工作日表
 * Create by Gxx
 * Time: 2014-03-05 21:04
 */
public class WedisleWorkDay
{
    String date;
    boolean isWorkDay;

    /**
     * 构造函数
     * @param date
     * @param workDay
     */
    public WedisleWorkDay(String date, boolean workDay) {
        this.date = date;
        isWorkDay = workDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isWorkDay() {
        return isWorkDay;
    }

    public void setWorkDay(boolean workDay) {
        isWorkDay = workDay;
    }
}
