/**
 * File Name:    Image.java
 *
 * File Desc:    ͼƬʵ��
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
 * ͼƬʵ��
 * @author Gxx
 * @version 1.0
 */
public class Image
{
    /**
     * ͼƬID ���� ����(��1����) ��Ϊ��
     */
    private int id;

    /**
     * ͼƬ·�� ��Ϊ��
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