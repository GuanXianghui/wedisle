package com.gxx.record.dao.wedisle;

import com.gxx.record.dao.DB;
import com.gxx.record.dao.DBUpdate;
import com.gxx.record.entities.wedisle.WedisleRelaFriend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 亲友簿实体操作类
 * User: Gxx
 * Time: 2013-11-02 09:12
 */
public class WedisleRelaFriendDao
{
    /**
     * 根据用户Id查亲友簿
     * @param userId
     * @return
     */
    public static List queryRelaFriendsByUserId(int userId) throws Exception
    {
        List relaFriends = new ArrayList();
        String sql = "SELECT name,num,relationship,email,phone,place,resv,help_group,worker,seat FROM" +
                " wedisle_rela_friend WHERE USER_ID=" + userId;
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
                String name = rs.getString("name");
                int num = rs.getInt("num");
                String relationship = rs.getString("relationship");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String place = rs.getString("place");
                String resv = rs.getString("resv");
                String helpGroup = rs.getString("help_group");
                String worker = rs.getString("worker");
                int seat = rs.getInt("seat");
                WedisleRelaFriend friend = new WedisleRelaFriend(userId, name, num, relationship, email, phone,
                        place, resv, helpGroup, worker, seat);
                relaFriends.add(friend);
            }

            return relaFriends;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 判相同用户，相同名字是否存在
     * @param userId
     * @param name
     * @return
     * @throws Exception
     */
    public static boolean isExistSameUserIdAndName(int userId, String name) throws Exception
    {
        String sql = "SELECT * FROM wedisle_rela_friend WHERE USER_ID=" + userId + " AND name='" + name + "'";
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
                return true;
            }
            return false;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 查询亲友
     * @param userId
     * @param name
     * @return
     */
    public static WedisleRelaFriend getRelaFriend(int userId, String name) throws Exception
    {
        String sql = "SELECT num,relationship,email,phone,place,resv,help_group,worker,seat" +
                " FROM wedisle_rela_friend WHERE USER_ID=" + userId + " AND name='" + name + "'";
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
                String relationship = rs.getString("relationship");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String place = rs.getString("place");
                String resv = rs.getString("resv");
                String helpGroup = rs.getString("help_group");
                String worker = rs.getString("worker");
                int num = rs.getInt("num");
                int seat = rs.getInt("seat");
                WedisleRelaFriend friend = new WedisleRelaFriend(userId, name, num, relationship, email, phone,
                        place, resv, helpGroup, worker, seat);
                return friend;
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
     * 查询亲友
     * @param userId
     * @param name
     * @param num
     * @return
     */
    public static WedisleRelaFriend getRelaFriend(int userId, String name, int num) throws Exception
    {
        String sql = "SELECT relationship,email,phone,place,resv,help_group,worker,seat" +
                " FROM wedisle_rela_friend WHERE USER_ID=" + userId + " AND name='" + name + "' AND num=" + num;
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
                String relationship = rs.getString("relationship");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String place = rs.getString("place");
                String resv = rs.getString("resv");
                String helpGroup = rs.getString("help_group");
                String worker = rs.getString("worker");
                int seat = rs.getInt("seat");
                WedisleRelaFriend friend = new WedisleRelaFriend(userId, name, num, relationship, email, phone,
                        place, resv, helpGroup, worker, seat);
                return friend;
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
     * 更新亲友
     * @param friend
     * @return
     */
    public static void updateRelaFriend(WedisleRelaFriend friend) throws Exception
    {
        String sql = "UPDATE wedisle_rela_friend SET num=" + friend.getNum() + ",seat=" + friend.getSeat()
                + ",relationship='" + friend.getRelationship() + "'" + ",email='" + friend.getEmail() + "'"
                + ",phone='" + friend.getPhone() + "'" + ",place='" + friend.getPlace() + "'"
                + ",resv='" + friend.getResv() + "'" + ",help_group='" + friend.getHelpGroup() + "'"
                + ",worker='" + friend.getWorker() + "'"
                + " WHERE USER_ID=" + friend.getUserId()
                + " AND name='" + friend.getName() + "'";
        DB.executeUpdate(sql);
    }

    /**
     * 新增亲友簿
     * @param friend
     * @throws Exception
     */
    public static void insertRelaFriend(WedisleRelaFriend friend) throws Exception
    {
        String sql = "insert into wedisle_rela_friend" +
                "(user_id,name,num,relationship,email,phone,place,resv,help_group,worker,seat)" +
                "values" +
                "(" +
                friend.getUserId() +
                ",'" +
                friend.getName() +
                "'," +
                friend.getNum() +
                ",'" +
                friend.getRelationship() +
                "','" +
                friend.getEmail() +
                "','" +
                friend.getPhone() +
                "','" +
                friend.getPlace() +
                "','" +
                friend.getResv() +
                "','" +
                friend.getHelpGroup() +
                "','" +
                friend.getWorker() +
                "'," +
                friend.getSeat() +
                ")";
        DB.executeUpdate(sql);
    }

    /**
     * 根据用户Id+已安排座位(seat>0)查亲友簿
     * @param userId
     * @return
     */
    public static List queryRelaFriendsByUserIdWithSeat(int userId) throws Exception
    {
        List relaFriends = new ArrayList();
        String sql = "SELECT name,num,relationship,email,phone,place,resv,help_group,worker,seat " +
                "FROM wedisle_rela_friend WHERE USER_ID=" + userId + " AND seat>0";
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
                String name = rs.getString("name");
                int num = rs.getInt("num");
                String relationship = rs.getString("relationship");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String place = rs.getString("place");
                String resv = rs.getString("resv");
                String helpGroup = rs.getString("help_group");
                String worker = rs.getString("worker");
                int seat = rs.getInt("seat");
                WedisleRelaFriend friend = new WedisleRelaFriend(userId, name, num, relationship, email, phone,
                        place, resv, helpGroup, worker, seat);
                relaFriends.add(friend);
            }

            return relaFriends;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 根据用户Id+未安排座位(seat=0)查亲友簿
     * @param userId
     * @return
     */
    public static List queryRelaFriendsByUserIdWithoutSeat(int userId) throws Exception
    {
        List relaFriends = new ArrayList();
        String sql = "SELECT name,num,relationship,email,phone,place,resv,help_group,worker,seat " +
                "FROM wedisle_rela_friend WHERE USER_ID=" + userId + " AND seat=0";
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
                String name = rs.getString("name");
                int num = rs.getInt("num");
                String relationship = rs.getString("relationship");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String place = rs.getString("place");
                String resv = rs.getString("resv");
                String helpGroup = rs.getString("help_group");
                String worker = rs.getString("worker");
                int seat = rs.getInt("seat");
                WedisleRelaFriend friend = new WedisleRelaFriend(userId, name, num, relationship, email, phone,
                        place, resv, helpGroup, worker, seat);
                relaFriends.add(friend);
            }

            return relaFriends;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 删除好友
     * @param userId
     * @param names
     * @throws Exception
     */
    public static void deleteRelaFriends(int userId, String names) throws Exception{
        String sql = "DELETE FROM wedisle_rela_friend  WHERE USER_ID=" + userId + " AND name in (" + names + ")";
        DB.executeUpdate(sql);
    }
}
