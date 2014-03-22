/**
 * File Name:    Article.java
 *
 * File Desc:    ����ʵ��
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
 * History:      2013-09-08 created by Gxx
 */
package ztree.entities;

/**
 * ����ʵ��
 * @author Gxx
 * @version 1.0
 */
public class Article
{
    /**
     * �������������ID ���� ����(��1����) ��Ϊ��
     */
    private int id;

    /**
     * �����ϲ�������id ��Ϊ��
     */
    private int pid;

    /**
     * ���ȼ� 0~n ��Ϊ��
     */
    private int indexId;

    /**
     * ������������������ ��Ϊ��
     */
    private String name;

    /**
     * �Ƿ�Ϊ������ true/false ��Ϊ��
     */
    private boolean isArticleType;

    /**
     * �������� ��Ϊ��
     */
    private String content;

    /**
     * ͼƬID���� ���ŷָ� ��Ϊ��
     */
    private String imageIds;

    /**
     * �������� ��Ϊ�� �޸����� ��ʱ����¼
     */
    private String requestDate;

    /**
     * ����ʱ�� ��Ϊ�� �޸�ʱ�� ��ʱ����¼
     */
    private String requestTime;

    /**
     * �Ƿ�ɾ�� true/false
     */
    private boolean isDelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getIndexId() {
        return indexId;
    }

    public void setIndexId(int indexId) {
        this.indexId = indexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isArticleType() {
        return isArticleType;
    }

    public void setArticleType(boolean articleType) {
        isArticleType = articleType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageIds() {
        return imageIds;
    }

    public void setImageIds(String imageIds) {
        this.imageIds = imageIds;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}