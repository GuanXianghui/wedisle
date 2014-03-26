package com.gxx.record.dao.wedisle;

import com.gxx.record.dao.DB;
import com.gxx.record.entities.wedisle.WedisleRelaFriend;
import com.gxx.record.entities.wedisle.WedisleUserStep;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户步骤实体操作类
 * User: Gxx
 * Time: 2014-03-22 12:16
 */
public class WedisleUserStepDao
{
    /**
     * 新增用户步骤
     * @param step
     * @return
     */
    public static void insertWedisleUserStep(WedisleUserStep step) throws Exception
    {
        String sql = "INSERT wedisle_user_step(user_id,step_id,is_done,dispatch_friends,dispatch_title," +
                "dispatch_begin_date,dispatch_end_date,dispatch_content,dispatch_html) values(" + step.getUserId()
                + "," + step.getStepId() + "," + (step.getIsDone()?1:0) + ",'" + step.getDispatchFriends()
                + "','" + step.getDispatchTitle() + "','" + step.getDispatchBeginDate()
                + "','" + step.getDispatchEndDate() + "','" + step.getDispatchContent() + "','" + step.getDispatchHtml() + "')";
        DB.executeUpdate(sql);
    }

    /**
     * 删除用户步骤
     * @param userId
     * @param stepId
     * @return
     */
    public static void deleteWedisleUserStep(int userId, int stepId) throws Exception
    {
        String sql = "DELETE FROM wedisle_user_step WHERE user_id=" + userId + " AND step_id=" + stepId;
        DB.executeUpdate(sql);
    }

    /**
     * 根据userId查用户步骤
     * @param userId
     * @return
     * @throws Exception
     */
    public static List<WedisleUserStep> queryWedisleUserStepsByUserId(int userId) throws Exception
    {
        List<WedisleUserStep> list = new ArrayList<WedisleUserStep>();
        String sql = "SELECT step_id,is_done,dispatch_friends,dispatch_title," +
                "dispatch_begin_date,dispatch_end_date,dispatch_content,dispatch_html FROM wedisle_user_step" +
                " WHERE user_id=" + userId;
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
                int stepId = rs.getInt("step_id");
                boolean isDone = 1==rs.getInt("is_done");
                String dispatchFriends = rs.getString("dispatch_friends");
                String dispatchTitle = rs.getString("dispatch_title");
                String dispatchBeginDate = rs.getString("dispatch_begin_date");
                String dispatchEndDate = rs.getString("dispatch_end_date");
                String dispatchContent = rs.getString("dispatch_content");
                String dispatchHtml = rs.getString("dispatch_html");
                WedisleUserStep step = new WedisleUserStep(userId, stepId, isDone, dispatchFriends, dispatchTitle,
                        dispatchBeginDate, dispatchEndDate, dispatchContent, dispatchHtml);
                list.add(step);
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
     * 根据userId,step_id查询用户步骤
     * @param userId
     * @return
     * @throws Exception
     */
    public static WedisleUserStep getWedisleUserStepsByUserIdAndStepId(int userId, int stepId) throws Exception
    {
        WedisleUserStep userStep = null;
        String sql = "SELECT is_done,dispatch_friends,dispatch_title," +
                "dispatch_begin_date,dispatch_end_date,dispatch_content,dispatch_html FROM wedisle_user_step" +
                " WHERE user_id=" + userId + " AND step_id=" + stepId;
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
                boolean isDone = 1==rs.getInt("is_done");
                String dispatchFriends = rs.getString("dispatch_friends");
                String dispatchTitle = rs.getString("dispatch_title");
                String dispatchBeginDate = rs.getString("dispatch_begin_date");
                String dispatchEndDate = rs.getString("dispatch_end_date");
                String dispatchContent = rs.getString("dispatch_content");
                String dispatchHtml = rs.getString("dispatch_html");
                userStep = new WedisleUserStep(userId, stepId, isDone, dispatchFriends, dispatchTitle,
                        dispatchBeginDate, dispatchEndDate, dispatchContent, dispatchHtml);
            }
            return userStep;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 更新用户步骤
     * @param step
     * @return
     */
    public static void updateWedisleUserStep(WedisleUserStep step) throws Exception
    {
        String sql = "UPDATE wedisle_user_step SET is_done=" + (step.getIsDone()?1:0)
                + ",dispatch_friends='" + step.getDispatchFriends()
                + "',dispatch_title='" + step.getDispatchTitle()
                + "',dispatch_begin_date='" + step.getDispatchBeginDate()
                + "',dispatch_end_date='" + step.getDispatchEndDate()
                + "',dispatch_content='" + step.getDispatchContent()
                + "',dispatch_html='" + step.getDispatchHtml()
                + "' WHERE user_id=" + step.getUserId() + " AND step_id=" + step.getStepId();;
        DB.executeUpdate(sql);
    }
}