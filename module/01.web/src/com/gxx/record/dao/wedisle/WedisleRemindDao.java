package com.gxx.record.dao.wedisle;

import com.gxx.record.dao.DB;
import com.gxx.record.entities.wedisle.WedisleRemind;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 提醒实体操作类
 * User: Gxx
 * Time: 2014-3-15 21:43
 */
public class WedisleRemindDao
{
    /**
     * 查询提醒
     * @param id
     * @param userId
     * @return
     */
    public static WedisleRemind getWedisleRemindByIdAndUserId(int id, int userId) throws Exception
    {
        WedisleRemind wedisleRemind = null;
        String sql = "SELECT event, date, remind_type, remind_date, remind_time FROM WEDISLE_REMIND" +
                " WHERE id=" + id + " AND user_id=" + userId;
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

                String event = rs.getString("event");
                String date = rs.getString("date");
                String remindType = rs.getString("remind_type");
                String remindDate = StringUtils.trimToEmpty(rs.getString("remind_date"));
                String remindTime = StringUtils.trimToEmpty(rs.getString("remind_time"));
                wedisleRemind = new WedisleRemind(id, userId, event, date, remindType, remindDate, remindTime);
            }
            return wedisleRemind;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据起止日期查用户提醒
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static List<WedisleRemind> queryWedisleRemindsBetweenDate(int userId, String startDate, String endDate) throws Exception
    {
        List<WedisleRemind> wedisleReminds = new ArrayList<WedisleRemind>();
        String sql = "SELECT id, event, date, remind_type, remind_date, remind_time FROM WEDISLE_REMIND" +
                " WHERE user_id=" + userId + " AND date between '" + startDate + "' and '" + endDate + "' order by id";
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
                int id = rs.getInt("id");
                String event = rs.getString("event");
                String date = rs.getString("date");
                String remindType = rs.getString("remind_type");
                String remindDate = StringUtils.trimToEmpty(rs.getString("remind_date"));
                String remindTime = StringUtils.trimToEmpty(rs.getString("remind_time"));
                WedisleRemind wedisleRemind = new WedisleRemind(id, userId, event, date, remindType, remindDate, remindTime);
                wedisleReminds.add(wedisleRemind);
            }
            return wedisleReminds;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据userId查最大的Id
     * @param userId
     * @return
     * @throws Exception
     */
    public static int getMaxIdByUserId(int userId) throws Exception
    {
        String sql = "SELECT MAX(id) max_id FROM WEDISLE_REMIND WHERE user_id=" + userId;
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
                int maxId = rs.getInt("max_id");
                return maxId;
            }
            return 0;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 新增提醒
     * @param wedisleRemind
     * @throws Exception
     */
    public static void insertWedisleRemind(WedisleRemind wedisleRemind) throws Exception
    {
        String sql = "insert into WEDISLE_REMIND" +
                "(id, user_id, event, date, remind_type, remind_date, remind_time)" +
                "values" +
                "(null," +
                wedisleRemind.getUserId() +
                ",'" +
                wedisleRemind.getEvent() +
                "','" +
                wedisleRemind.getDate() +
                "','" +
                wedisleRemind.getRemindType() +
                "','" +
                wedisleRemind.getRemindDate() +
                "','" +
                wedisleRemind.getRemindTime() +
                "')";
        DB.executeUpdate(sql);
    }

    /**
     * 修改提醒
     * @param wedisleRemind
     * @throws Exception
     */
    public static void updateWedisleRemind(WedisleRemind wedisleRemind) throws Exception
    {
        String sql = "UPDATE WEDISLE_REMIND set event='" + wedisleRemind.getEvent() +
                "', remind_type='" + wedisleRemind.getRemindType() +
                "', remind_date='" + wedisleRemind.getRemindDate() +
                "', remind_time='" + wedisleRemind.getRemindTime() +
                "' WHERE id=" + wedisleRemind.getId();
        DB.executeUpdate(sql);
    }

    /**
     * 删除提醒
     * @param wedisleRemind
     * @throws Exception
     */
    public static void deleteWedisleRemind(WedisleRemind wedisleRemind) throws Exception
    {
        String sql = "DELETE FROM WEDISLE_REMIND WHERE id=" + wedisleRemind.getId()
                + " AND user_id=" + wedisleRemind.getUserId();
        DB.executeUpdate(sql);
    }

    /**
     * 根据起止提醒日期查用户提醒
     * @param startDateTime yyyyMMddHHmmss
     * @param endDateTime yyyyMMddHHmmss
     * @return
     * @throws Exception
     */
    public static List<WedisleRemind> queryWedisleRemindsBetweenRemindDateTime(String startDateTime, String endDateTime) throws Exception
    {
        List<WedisleRemind> wedisleReminds = new ArrayList<WedisleRemind>();
        String sql = "SELECT user_id, id, event, date, remind_type, remind_date, remind_time FROM WEDISLE_REMIND" +
                " WHERE remind_type != 'no' AND concat(remind_date,remind_time) >= '" + startDateTime + "' " +
                "AND concat(remind_date,remind_time) < '" + endDateTime + "' order by id";
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
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                String event = rs.getString("event");
                String date = rs.getString("date");
                String remindType = rs.getString("remind_type");
                String remindDate = StringUtils.trimToEmpty(rs.getString("remind_date"));
                String remindTime = StringUtils.trimToEmpty(rs.getString("remind_time"));
                WedisleRemind wedisleRemind = new WedisleRemind(id, userId, event, date, remindType, remindDate, remindTime);
                wedisleReminds.add(wedisleRemind);
            }
            return wedisleReminds;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }
}