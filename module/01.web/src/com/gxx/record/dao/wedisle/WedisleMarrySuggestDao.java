package com.gxx.record.dao.wedisle;

import com.gxx.record.dao.DB;
import com.gxx.record.entities.wedisle.WedisleMarrySuggest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 婚嫁建议实体操作类
 * User: Gxx
 * Time: 2014-03-08 22:37
 */
public class WedisleMarrySuggestDao
{
    /**
     * 根据年月查婚嫁建议
     * @param month
     * @return
     */
    public static WedisleMarrySuggest getWedisleMarrySuggestByYearAndMonth(int year, int month) throws Exception
    {
        String sql = "SELECT legal_from, legal_end, late_from, late_end FROM wedisle_marry_suggest " +
                "WHERE year=" + year + " AND month=" + month;
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
                String legalFrom = rs.getString("legal_from");
                String legalEnd = rs.getString("legal_end");
                String lateFrom = rs.getString("late_from");
                String lateEnd = rs.getString("late_end");
                WedisleMarrySuggest wedisleMarrySuggest = new WedisleMarrySuggest(year, month, legalFrom, legalEnd, lateFrom, lateEnd);
                return wedisleMarrySuggest;
            }
            return null;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据年月查婚嫁建议是否存在
     * @param year
     * @param month
     * @return
     * @throws Exception
     */
    public static boolean isWedisleExistMarrySuggest(int year, int month) throws Exception{
        if(getWedisleMarrySuggestByYearAndMonth(year, month) == null){
            return false;
        }
        return true;
    }

    /**
     * 新增婚嫁建议
     * @param wedisleMarrySuggest
     * @throws Exception
     */
    private static void saveWedisleExistMarry(WedisleMarrySuggest wedisleMarrySuggest) throws Exception{
        String sql = "insert into wedisle_marry_suggest" +
                "(year,month,legal_from,legal_end,late_from,late_end)" +
                "values" +
                "(" +
                wedisleMarrySuggest.getYear() +
                "," +
                wedisleMarrySuggest.getMonth() +
                ",'" +
                wedisleMarrySuggest.getLegalFrom() +
                "','" +
                wedisleMarrySuggest.getLegalEnd() +
                "','" +
                wedisleMarrySuggest.getLateFrom() +
                "','" +
                wedisleMarrySuggest.getLateEnd() +
                "')";
        DB.executeUpdate(sql);
    }

    /**
     * 修改婚嫁建议
     * @param wedisleMarrySuggest
     * @throws Exception
     */
    private static void updateWedisleExistMarry(WedisleMarrySuggest wedisleMarrySuggest) throws Exception{
        String sql = "update wedisle_marry_suggest set legal_from='" + wedisleMarrySuggest.getLegalFrom() +
                "',legal_end='" + wedisleMarrySuggest.getLegalEnd() +
                "',late_from='" + wedisleMarrySuggest.getLateFrom() +
                "',late_end='" + wedisleMarrySuggest.getLateEnd() +
                "' WHERE year=" + wedisleMarrySuggest.getYear() + " AND month=" + wedisleMarrySuggest.getMonth();
        DB.executeUpdate(sql);
    }

    /**
     * 新增/修改婚嫁建议
     * @param wedisleMarrySuggest
     * @throws Exception
     */
    public static void saveOrUpdateWedisleExistMarry(WedisleMarrySuggest wedisleMarrySuggest) throws Exception{
        if(isWedisleExistMarrySuggest(wedisleMarrySuggest.getYear(), wedisleMarrySuggest.getMonth())){
            updateWedisleExistMarry(wedisleMarrySuggest);
        } else
        {
            saveWedisleExistMarry(wedisleMarrySuggest);
        }
    }
}
