/**
 * File Name:    DeleteType.java
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
package com.gxx.record.enums;

/**
 * @author Gxx
 * @version 1.0
 */
public enum SexType
{
    MALE(1, "MALE"),
    FEMALE(0, "FEMALE");

    private int sexTypeInt;
    private String sexType;
    private SexType(int sexTypeInt, String sexType) {
        this.sexTypeInt = sexTypeInt;
        this.sexType = sexType;
    }

    public static SexType getSexType(int sexTypeInt)
    {
        if(MALE.sexTypeInt == sexTypeInt)
        {
            return MALE;
        }
        if(FEMALE.sexTypeInt == sexTypeInt)
        {
            return FEMALE;
        }
        return MALE;
    }

    public int getSexTypeInt()
    {
        return sexTypeInt;
    }

    public void setSexTypeInt(int sexTypeInt)
    {
        this.sexTypeInt = sexTypeInt;
    }

    public String getSexType()
    {
        return sexType;
    }

    public void setSexType(String sexType)
    {
        this.sexType = sexType;
    }
}
