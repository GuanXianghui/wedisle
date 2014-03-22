package com.gxx.record.entities.wedisle;

/**
 * ϯλ��Ϣ
 * User: Gxx
 * Time: 2013-11-02 10:18
 */
public class WedisleSeatInfo
{
    /**
     * ��ʼ������
     */
    public static final int DEFAULT_TABLE_COUNT = 5;
    /**
     * ��ʼ��ÿ������
     */
    public static final int DEFAULT_NUM_EVERY_TABLE = 5;
    /**
     * �û�ID ��Ϊ��
     */
    int userId;
    /**
     * ���� ��Ϊ�� ��ʼ5
     */
    int tableCount;
    /**
     * ÿ������ ��Ϊ�� ��ʼ5
     */
    int numEveryTable;

    /**
     * ���캯��
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
