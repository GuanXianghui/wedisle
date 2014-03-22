/**
 * File Name:    UserState.java
 *
 * File Desc:    �û�״̬
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
 * History:      2013-10-13 created by Gxx
 */
package com.gxx.record.enums.wedisle;

/**
 * �û�״̬
 * @author Gxx
 * @version 1.0
 */
public enum UserState
{
    NORMAL(1, "NORMAL"),
    LOCK(2, "LOCK"),
    CANCEL(3, "CANCEL");

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
        if(CANCEL.userStateInt == userStateInt)
        {
            return CANCEL;
        }
        return CANCEL;
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
