package com.gxx.record.dao.wedisle;

import com.gxx.record.dao.DB;
import com.gxx.record.entities.wedisle.WedisleGoodDay;
import com.gxx.record.entities.wedisle.WedisleSeatInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 黄道吉日实体操作类
 * User: Gxx
 * Time: 2014-2-13 10:09
 */
public class WedisleGoodDayDao
{
    /**
     * 根据年月模糊查询，比如：201402
     * @param yearAndMonth
     * @return
     */
    public static List queryWedisleGoodDays(String yearAndMonth) throws Exception
    {
        List<WedisleGoodDay> wedisleGoodDays = new ArrayList<WedisleGoodDay>();
        String sql = "SELECT DATE, GONGLI, NONGLI, CHONG, YI, JI, WUXING, CISUI, PENGZUBAIJI FROM" +
                " WEDISLE_GOOD_DAY WHERE DATE LIKE '" + yearAndMonth + "%'";
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

                String date = rs.getString("DATE");
                String gongli = rs.getString("GONGLI");
                String nongli = rs.getString("NONGLI");
                String chong = rs.getString("CHONG");
                String yi = rs.getString("YI");
                String ji = rs.getString("JI");
                String wuxing = rs.getString("WUXING");
                String cisui = rs.getString("CISUI");
                String pengzubaiji = rs.getString("PENGZUBAIJI");
                WedisleGoodDay wedisleGoodDay = new WedisleGoodDay(date, gongli, nongli, chong, yi, ji, wuxing, cisui, pengzubaiji);
                wedisleGoodDays.add(wedisleGoodDay);
            }
            return wedisleGoodDays;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }
}
