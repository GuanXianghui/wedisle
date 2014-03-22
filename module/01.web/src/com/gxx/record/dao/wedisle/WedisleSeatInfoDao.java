package com.gxx.record.dao.wedisle;

import com.gxx.record.dao.DB;
import com.gxx.record.entities.wedisle.WedisleRelaFriend;
import com.gxx.record.entities.wedisle.WedisleSeatInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 席位信息实体操作类
 * User: Gxx
 * Time: 2013-11-02 09:12
 */
public class WedisleSeatInfoDao
{
    /**
     * 根据用户Id查席位信息
     * @param userId
     * @return
     */
    public static WedisleSeatInfo getSeatInfoByUserId(int userId) throws Exception
    {
        WedisleSeatInfo seatInfo;
        String sql = "SELECT table_count,num_every_table FROM wedisle_seat_info WHERE USER_ID=" + userId;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try
        {
            if (rs == null)
            {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next())
            {
                int tableCount = rs.getInt("table_count");
                int numEveryTable = rs.getInt("num_every_table");
                seatInfo = new WedisleSeatInfo(userId, tableCount, numEveryTable);
                return seatInfo;
            }
            seatInfo = initSeatInto(userId);
            return seatInfo;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 初始化席位信息
     * @param userId
     * @throws Exception
     */
    public static WedisleSeatInfo initSeatInto(int userId) throws Exception
    {
        String sql = "insert into wedisle_seat_info" +
                "(user_id,table_count,num_every_table)" +
                "values" +
                "(" +
                userId +
                "," +
                WedisleSeatInfo.DEFAULT_TABLE_COUNT +
                "," +
                WedisleSeatInfo.DEFAULT_NUM_EVERY_TABLE +
                ")";
        DB.executeUpdate(sql);
        WedisleSeatInfo seatInfo = new WedisleSeatInfo(userId, WedisleSeatInfo.DEFAULT_TABLE_COUNT,
                WedisleSeatInfo.DEFAULT_NUM_EVERY_TABLE);
        return seatInfo;
    }

    /**
     * 更新席位信息
     * @param seatInfo
     * @return
     */
    public static void updateSeatInfo(WedisleSeatInfo seatInfo) throws Exception
    {
        String sql = "UPDATE wedisle_seat_info SET table_count=" +
                seatInfo.getTableCount() +
                ",num_every_table=" +
                seatInfo.getNumEveryTable() +
                " WHERE USER_ID=" + seatInfo.getUserId();
        DB.executeUpdate(sql);
    }
}
