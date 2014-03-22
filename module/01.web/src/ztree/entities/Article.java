/**
 * File Name:    Article.java
 *
 * File Desc:    文章实体
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
 * 文章实体
 * @author Gxx
 * @version 1.0
 */
public class Article
{
    /**
     * 文章类或者文章ID 主键 自增(从1自增) 不为空
     */
    private int id;

    /**
     * 所属上层文章类id 不为空
     */
    private int pid;

    /**
     * 优先级 0~n 不为空
     */
    private int indexId;

    /**
     * 文章类名或者文章名 不为空
     */
    private String name;

    /**
     * 是否为文章类 true/false 不为空
     */
    private boolean isArticleType;

    /**
     * 文章内容 可为空
     */
    private String content;

    /**
     * 图片ID集合 逗号分隔 可为空
     */
    private String imageIds;

    /**
     * 发布日期 不为空 修改日期 暂时不记录
     */
    private String requestDate;

    /**
     * 发布时间 不为空 修改时间 暂时不记录
     */
    private String requestTime;

    /**
     * 是否被删除 true/false
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