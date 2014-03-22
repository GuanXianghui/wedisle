/**
 * File Name:    UserState.java
 *
 * File Desc:    用户状态
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
package com.gxx.record.enums;

/**
 * 用户状态
 * @author Gxx
 * @version 1.0
 */
public enum UserState
{
    NORMAL(1, "NORMAL"),
    LOCK(2, "LOCK"),
    DELETE(3, "DELETE");

    private int userStateInt;
    private String userStateType;
    private UserState(int userStateInt, String userStateType) {
        this.userStateInt = userStateInt;
        this.userStateType = userStateType;
    }

    public static UserState getUserState(int userStateInt)
    {
        if(NORMAL.userStateInt == userStateInt)
        {
            return NORMAL;
        }
        if(LOCK.userStateInt == userStateInt)
        {
            return LOCK;
        }
        if(DELETE.userStateInt == userStateInt)
        {
            return DELETE;
        }
        return DELETE;
    }

    public int getUserStateInt()
    {
        return userStateInt;
    }

    public void setUserStateInt(int userStateInt)
    {
        this.userStateInt = userStateInt;
    }

    public String getUserStateType()
    {
        return userStateType;
    }

    public void setUserStateType(String userStateType)
    {
        this.userStateType = userStateType;
    }
}
