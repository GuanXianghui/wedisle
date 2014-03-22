package com.gxx.record.dao.wedisle;

import com.gxx.record.dao.DB;
import com.gxx.record.entities.wedisle.WedisleSeatInfo;
import com.gxx.record.entities.wedisle.WedisleWorkDay;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ������ʵ�������
 * User: Gxx
 * Time: 2014-03-05 21:05
 */
public class WedisleWorkDayDao
{
    /**
     * �����·�yyyyMM�鹤����
     * @param month
     * @return
     */
    public static List<WedisleWorkDay> queryWedisleWorkDaysByMonth(String month) throws Exception
    {
        List<WedisleWorkDay> list = new ArrayList<WedisleWorkDay>();
        String sql = "SELECT date, is_work_day FROM wedisle_work_day WHERE date like '" + month + "%'";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try
        {
            if (rs == null)
            {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next())
            {
                String date = rs.getString("date");
                boolean isWorkDay = rs.getInt("is_work_day") == 1;
                WedisleWorkDay wedisleWorkDay = new WedisleWorkDay(date, isWorkDay);
                list.add(wedisleWorkDay);
            }
            return list;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }
    /**
     * �������ڲ鹤����
     * @param date
     * @return
     */
    public static WedisleWorkDay getWedisleWorkDayByDate(String date) throws Exception
    {
        WedisleWorkDay wedisleWorkDay = null;
        String sql = "SELECT is_work_day FROM wedisle_work_day WHERE date=" + date;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try
        {
            if (rs == null)
            {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next())
            {
                boolean isWorkDay = rs.getInt("is_work_day") == 1;
                wedisleWorkDay = new WedisleWorkDay(date, isWorkDay);
            }
            return wedisleWorkDay;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �Ƿ���ڹ�����
     * @param date
     * @return
     * @throws Exception
     */
    public static boolean isExistWorkDay(String date) throws Exception
    {
        WedisleWorkDay wedisleWorkDay = getWedisleWorkDayByDate(date);
        if(null == wedisleWorkDay){
            return false;
        }
        return true;
    }

    /**
     * ����������
     * @param wedisleWorkDay
     * @throws Exception
     */
    public static void insertWedisleWorkDay(WedisleWorkDay wedisleWorkDay) throws Exception
    {
        String sql = "insert into wedisle_work_day" +
                "(date,is_work_day)" +
                "values" +
                "('" +
                wedisleWorkDay.getDate() +
                "'," +
                (wedisleWorkDay.isWorkDay()?1:0) +
                ")";
        DB.executeUpdate(sql);
    }

    /**
     * �޸Ĺ�����
     * @param wedisleWorkDay
     * @throws Exception
     */
    public static void updateWedisleWorkDay(WedisleWorkDay wedisleWorkDay) throws Exception
    {
        String sql = "update wedisle_work_day set is_work_day=" + (wedisleWorkDay.isWorkDay()?1:0)
                + " where date='" + wedisleWorkDay.getDate() + "'";
        DB.executeUpdate(sql);
    }

    /**
     * ����ϯλ��Ϣ
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
