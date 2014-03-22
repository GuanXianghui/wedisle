/**
 * File Name:    Image.java
 *
 * File Desc:    图片实体
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
 * History:      2013-09-10 created by Gxx
 */
package ztree.entities;

/**
 * 图片实体
 * @author Gxx
 * @version 1.0
 */
public class Image
{
    /**
     * 图片ID 主键 自增(从1自增) 不为空
     */
    private int id;

    /**
     * 图片路径 不为空
     */
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}