package com.gxx.record.entities.wedisle;

import org.apache.commons.lang.StringUtils;

/**
 * 用户步骤表
 * Create by Gxx
 * Time: 2014-03-22 12:12
 */
public class WedisleUserStep
{
    int userId;
    int stepId;
    boolean isDone;
    String dispatchFriends;
    String dispatchTitle;
    String dispatchBeginDate;
    String dispatchEndDate;
    String dispatchContent;
    String dispatchHtml;

    /**
     * 查询时候用
     * @param userId
     * @param stepId
     * @param isDone
     * @param dispatchFriends
     * @param dispatchTitle
     * @param dispatchBeginDate
     * @param dispatchEndDate
     * @param dispatchContent
     * @param dispatchHtml
     */
    public WedisleUserStep(int userId, int stepId, boolean isDone, String dispatchFriends, String dispatchTitle,
                           String dispatchBeginDate, String dispatchEndDate, String dispatchContent, String dispatchHtml) {
        this.userId = userId;
        this.stepId = stepId;
        this.isDone = isDone;
        this.dispatchFriends = dispatchFriends;
        this.dispatchTitle = dispatchTitle;
        this.dispatchBeginDate = dispatchBeginDate;
        this.dispatchEndDate = dispatchEndDate;
        this.dispatchContent = dispatchContent;
        this.dispatchHtml = dispatchHtml;
    }

    /**
     * 新增时候使用
     * @param userId
     * @param stepId
     */
    public WedisleUserStep(int userId, int stepId) {
        this.userId = userId;
        this.stepId = stepId;
        this.isDone = false;
        this.dispatchFriends = StringUtils.EMPTY;
        this.dispatchTitle = StringUtils.EMPTY;
        this.dispatchBeginDate = StringUtils.EMPTY;
        this.dispatchEndDate = StringUtils.EMPTY;
        this.dispatchContent = StringUtils.EMPTY;
        this.dispatchHtml = StringUtils.EMPTY;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getDispatchFriends() {
        return dispatchFriends;
    }

    public void setDispatchFriends(String dispatchFriends) {
        this.dispatchFriends = dispatchFriends;
    }

    public String getDispatchTitle() {
        return dispatchTitle;
    }

    public void setDispatchTitle(String dispatchTitle) {
        this.dispatchTitle = dispatchTitle;
    }

    public String getDispatchBeginDate() {
        return dispatchBeginDate;
    }

    public void setDispatchBeginDate(String dispatchBeginDate) {
        this.dispatchBeginDate = dispatchBeginDate;
    }

    public String getDispatchEndDate() {
        return dispatchEndDate;
    }

    public void setDispatchEndDate(String dispatchEndDate) {
        this.dispatchEndDate = dispatchEndDate;
    }

    public String getDispatchContent() {
        return dispatchContent;
    }

    public void setDispatchContent(String dispatchContent) {
        this.dispatchContent = dispatchContent;
    }

    public String getDispatchHtml() {
        return dispatchHtml;
    }

    public void setDispatchHtml(String dispatchHtml) {
        this.dispatchHtml = dispatchHtml;
    }
}
