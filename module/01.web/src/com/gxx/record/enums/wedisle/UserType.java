/**
 * File Name:    UserType.java
 *
 * File Desc:    用户类型
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
 * 用户类型
 * @author Gxx
 * @version 1.0
 */
public enum UserType
{
    NORMAL(1, "NORMAL"),
    SUPER_ADMIN(2, "SUPER_ADMIN");

    private int userTypeInt;
    private String userType;
    private UserType(int userTypeInt, String userType) {
        this.userTypeInt = userTypeInt;
        this.userType = userType;
    }

    public static UserType getUserType(int userTypeInt)
    {
        if(NORMAL.userTypeInt == userTypeInt)
        {
            return NORMAL;
        }
        if(SUPER_ADMIN.userTypeInt == userTypeInt)
        {
            return SUPER_ADMIN;
        }
        return NORMAL;
    }

    public int getUserTypeInt()
    {
        return userTypeInt;
    }

    public void setUserTypeInt(int userTypeInt)
    {
        this.userTypeInt = userTypeInt;
    }

    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }
}
