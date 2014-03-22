package com.gxx.record.entities.wedisle;

/**
 * 首页步骤表
 * Create by Gxx
 * Time: 2014-2-21 11:31
 */
public class WedisleMainStep
{
    int id;
    int pid;
    int level;
    int indexId;
    String name;
    int articleId;

    /**
     * 新增时使用
     * @param pid
     * @param level
     * @param indexId
     * @param name
     * @param articleId
     */
    public WedisleMainStep(int pid, int level, int indexId, String name, int articleId) {
        this.pid = pid;
        this.level = level;
        this.indexId = indexId;
        this.name = name;
        this.articleId = articleId;
    }

    /**
     * 查询时使用
     * @param id
     * @param pid
     * @param level
     * @param indexId
     * @param name
     * @param articleId
     */
    public WedisleMainStep(int id, int pid, int level, int indexId, String name, int articleId) {
        this.id = id;
        this.pid = pid;
        this.level = level;
        this.indexId = indexId;
        this.name = name;
        this.articleId = articleId;
    }

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
