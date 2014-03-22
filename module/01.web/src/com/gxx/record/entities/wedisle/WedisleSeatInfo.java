package com.gxx.record.entities.wedisle;

/**
 * 席位信息
 * User: Gxx
 * Time: 2013-11-02 10:18
 */
public class WedisleSeatInfo
{
    /**
     * 初始化桌数
     */
    public static final int DEFAULT_TABLE_COUNT = 5;
    /**
     * 初始化每桌人数
     */
    public static final int DEFAULT_NUM_EVERY_TABLE = 5;
    /**
     * 用户ID 不为空
     */
    int userId;
    /**
     * 桌数 不为空 初始5
     */
    int tableCount;
    /**
     * 每桌人数 不为空 初始5
     */
    int numEveryTable;

    /**
     * 构造函数
     * @param userId
     * @param tableCount
     * @param numEveryTable
     */
    public WedisleSeatInfo(int userId, int tableCount, int numEveryTable) {
        this.userId = userId;
        this.tableCount = tableCount;
        this.numEveryTable = numEveryTable;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public int getNumEveryTable() {
        return numEveryTable;
    }

    public void setNumEveryTable(int numEveryTable) {
        this.numEveryTable = numEveryTable;
    }
}
