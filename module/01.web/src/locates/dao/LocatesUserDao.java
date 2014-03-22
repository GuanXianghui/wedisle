package locates.dao;

import com.gxx.record.dao.DB;
import locates.entities.LocatesUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 定位系统用户表操作类
 * User: Gxx
 * Time: 2013-10-27 12:14
 */
public class LocatesUserDao
{
    /**
     * 用户注册
     * @param user
     * @throws Exception
     */
    public static void registerUser(LocatesUser user) throws Exception
    {
        String sql = "insert into locates_user(imei, pwd, is_pen_open, pen_radius, pen_center_lat, " +
                "pen_center_lng, is_listening, listen_id)values(" +
                "'" + user.getImei() + "', " +
                "'" + user.getPwd() +  "', " +
                (user.isPenOpen()?1:0) + ", " +
                user.getPenRadius() + ", " +
                user.getPenCenterLat() + ", " +
                user.getPenCenterLng() + ", " +
                (user.isListening()?1:0) + ", " +
                user.getListenId() +
                ")";
        DB.executeUpdate(sql);
    }

    /**
     * 查询所有用户
     * @return
     * @throws Exception
     */
    public static List<LocatesUser> queryAllUsers() throws Exception
    {
        String sql = "SELECT imei, pwd, is_pen_open, pen_radius, pen_center_lat, pen_center_lng, " +
                "is_listening, listen_id FROM locates_user";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try
        {
            if (rs == null)
            {
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            List<LocatesUser> users = new ArrayList<LocatesUser>();
            while (rs.next())
            {
                String imei = rs.getString("imei");
                String pwd = rs.getString("pwd");
                boolean isPenOpen = 1==rs.getInt("is_pen_open");
                double penRadius = rs.getDouble("pen_radius");
                double penCenterLat = rs.getDouble("pen_center_lat");
                double penCenterLng = rs.getDouble("pen_center_lng");
                boolean isListening = 1==rs.getInt("is_listening");
                int listenId = rs.getInt("listen_id");
                LocatesUser user = new LocatesUser(imei, pwd, isPenOpen, penRadius, penCenterLat, penCenterLng,
                        isListening, listenId);
                users.add(user);
            }
            return users;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据IMEI查询用户
     * @param imei
     */
    public static LocatesUser getUserByImei(String imei) throws Exception
    {
        String sql = "SELECT pwd, is_pen_open, pen_radius, pen_center_lat, pen_center_lng, is_listening, " +
                "listen_id FROM locates_user WHERE imei='" + imei + "'";
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
                String pwd = rs.getString("pwd");
                boolean isPenOpen = 1==rs.getInt("is_pen_open");
                double penRadius = rs.getDouble("pen_radius");
                double penCenterLat = rs.getDouble("pen_center_lat");
                double penCenterLng = rs.getDouble("pen_center_lng");
                boolean isListening = 1==rs.getInt("is_listening");
                int listenId = rs.getInt("listen_id");
                LocatesUser user = new LocatesUser(imei, pwd, isPenOpen, penRadius, penCenterLat, penCenterLng,
                        isListening, listenId);
                return user;
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
     * 用户修改
     * @param user
     * @throws Exception
     */
    public static void updateUser(LocatesUser user) throws Exception
    {
        String sql = "UPDATE locates_user SET " +
                "pwd='" + user.getPwd() + "', " +
                "is_pen_open=" + (user.isPenOpen()?1:0) + ", " +
                "pen_radius=" + user.getPenRadius() + ", " +
                "pen_center_lat=" + user.getPenCenterLat() + ", " +
                "pen_center_lng=" + user.getPenCenterLng() + ", " +
                "is_listening=" + (user.isListening()?1:0) + ", " +
                "listen_id=" + user.getListenId() + " " +
                "WHERE imei='" + user.getImei() + "'";
        DB.executeUpdate(sql);
    }
}
