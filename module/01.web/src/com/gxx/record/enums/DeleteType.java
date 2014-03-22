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
public enum DeleteType
{
    NOT_DELETE(0, "NOT_DELETE"),
    DELETE(1, "DELETE"),
    ALL(2, "ALL");

    private int deleteInt;
    private String deleteType;
    private DeleteType(int deleteInt, String deleteType) {
        this.deleteInt = deleteInt;
        this.deleteType = deleteType;
    }
    
    public int getDeleteInt()
    {
        return deleteInt;
    }
    
    public String getDeleteType()
    {
        return deleteType;
    }
}
