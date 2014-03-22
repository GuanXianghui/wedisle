/**
 * File Name:    ActionLog.java
 *
 * File Desc:    动作类型枚举
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
package com.gxx.record.enums;

/**
 * 动作类型枚举
 * @author Gxx
 * @version 1.0
 */
public enum ActionType
{
    LOGIN(0, "LOGIN"),
    LOGIN_OUT(1, "LOGIN_OUT"),
    CREATE_TOPIC(2, "CREATE_TOPIC"),
    REPLY_TOPIC(3, "REPLY_TOPIC");

    private int actionInt;
    private String actionType;
    private ActionType(int actionInt, String actionType) {
        this.actionInt = actionInt;
        this.actionType = actionType;
    }

    public static ActionType getActionType(int actionTypeInt)
    {
        if(LOGIN.actionInt == actionTypeInt)
        {
            return LOGIN;
        }
        if(LOGIN_OUT.actionInt == actionTypeInt)
        {
            return LOGIN_OUT;
        }
        if(CREATE_TOPIC.actionInt == actionTypeInt)
        {
            return CREATE_TOPIC;
        }
        if(REPLY_TOPIC.actionInt == actionTypeInt)
        {
            return REPLY_TOPIC;
        }
        return LOGIN;
    }

    public int getActionInt()
    {
        return actionInt;
    }

    public void setActionInt(int actionInt)
    {
        this.actionInt = actionInt;
    }

    public String getActionType()
    {
        return actionType;
    }

    public void setActionType(String actionType)
    {
        this.actionType = actionType;
    }
}
