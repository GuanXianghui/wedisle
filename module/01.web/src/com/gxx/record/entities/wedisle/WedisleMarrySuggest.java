package com.gxx.record.entities.wedisle;

/**
 * 婚嫁建议表
 * Create by Gxx
 * Time: 2014-03-08 22:30
 */
public class WedisleMarrySuggest
{
    int year;//年
    int month;//月
    String legalFrom;//法定婚嫁开始日期
    String legalEnd;//法定婚嫁结束日期
    String lateFrom;//晚婚嫁开始日期
    String lateEnd;//晚定婚嫁结束日期

    /**
     * 构造函数
     * @param year
     * @param month
     * @param legalFrom
     * @param legalEnd
     * @param lateFrom
     * @param lateEnd
     */
    public WedisleMarrySuggest(int year, int month, String legalFrom, String legalEnd, String lateFrom, String lateEnd) {
        this.year = year;
        this.month = month;
        this.legalFrom = legalFrom;
        this.legalEnd = legalEnd;
        this.lateFrom = lateFrom;
        this.lateEnd = lateEnd;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getLegalFrom() {
        return legalFrom;
    }

    public void setLegalFrom(String legalFrom) {
        this.legalFrom = legalFrom;
    }

    public String getLegalEnd() {
        return legalEnd;
    }

    public void setLegalEnd(String legalEnd) {
        this.legalEnd = legalEnd;
    }

    public String getLateFrom() {
        return lateFrom;
    }

    public void setLateFrom(String lateFrom) {
        this.lateFrom = lateFrom;
    }

    public String getLateEnd() {
        return lateEnd;
    }

    public void setLateEnd(String lateEnd) {
        this.lateEnd = lateEnd;
    }
}
