package com.gxx.record.dao;

import com.gxx.record.entities.*;
import com.gxx.record.entities.wedisle.WedisleUser;
import com.gxx.record.enums.DeleteType;
import com.gxx.record.enums.SexType;
import org.apache.commons.lang.StringUtils;

public class DBUpdate {

    public static void insertTopic(Topic topic) throws Exception
    {
        String sql = "insert into topic" +
                "(id,section_id,user_id,title,content,tag,request_date,request_time,is_good,view_times,is_delete)" +
                "values" +
                "(null," +
                topic.getSectionId() +
                "," +
                topic.getUserId() +
                ",'" +
                topic.getTitle() +
                "','" +
                topic.getContent() +
                "','" +
                topic.getTag() +
                "','" +
                topic.getRequestDate() +
                "','" +
                topic.getRequestTime() +
                "'," +
                (topic.isGood()?1:0) +
                "," +
                topic.getViewTimes() +
                "," +
                (topic.isDelete()?1:0) +
                ")";
        DB.executeUpdate(sql);
    }

    /**
     * 更新帖子
     * 根据 id和userId 只修改 title content tag dateStr timeStr 其他不变
     * @param topic
     * @throws Exception
     */
    public static void updateTopic(Topic topic) throws Exception
    {
        String sql = "UPDATE TOPIC SET " +
                "title='" + topic.getTitle() +
                "',content='" + topic.getContent() +
                "',tag='" + topic.getTag() +
                "',request_date='" + topic.getRequestDate() +
                "',request_time='" + topic.getRequestTime() +
                "' WHERE id= " + topic.getId() +
                " AND user_id=" + topic.getUserId();
        DB.executeUpdate(sql);
    }

    /**
     * 删除帖子
     * @param topic
     * @throws Exception
     */
    public static void deleteTopic(Topic topic) throws Exception
    {
        String sql = "UPDATE TOPIC SET IS_DELETE=" + DeleteType.DELETE.getDeleteInt() + " WHERE ID=" + topic.getId();
        DB.executeUpdate(sql);
    }

    /**
     * 新增回帖
     * @param reply
     * @throws Exception
     */
    public static void insertReply(Reply reply) throws Exception
    {
        String sql = "insert into reply" +
                "(id,topic_id,user_id,content,request_date,request_time,is_delete)" +
                "values" +
                "(null," +
                reply.getTopicId() +
                "," +
                reply.getUserId() +
                ",'" +
                reply.getContent() +
                "','" +
                reply.getRequestDate() +
                "','" +
                reply.getRequestTime() +
                "'," +
                (reply.isDelete()?1:0) +
                ")";
        DB.executeUpdate(sql);
    }

    /**
     * 更新回帖
     * 根据 id和userId 只修改 content dateStr timeStr 其他不变
     * @param reply
     * @throws Exception
     */
    public static void updateReply(Reply reply) throws Exception
    {
        String sql = "UPDATE REPLY SET " +
                "content='" + reply.getContent() +
                "',request_date='" + reply.getRequestDate() +
                "',request_time='" + reply.getRequestTime() +
                "' WHERE id= " + reply.getId() +
                " AND user_id=" + reply.getUserId();
        DB.executeUpdate(sql);
    }

    /**
     * 根据 帖子id 删除所有回帖
     * @param topicId
     * @throws Exception
     */
    public static void deleteReplies(int topicId) throws Exception
    {
        String sql = "UPDATE reply SET IS_DELETE=" + DeleteType.DELETE.getDeleteInt() + " WHERE topic_id=" + topicId;
        DB.executeUpdate(sql);
    }

    /**
     * 根据 回帖id 删除回帖
     * @param id
     * @throws Exception
     */
    public static void deleteReply(int id) throws Exception
    {
        String sql = "UPDATE reply SET IS_DELETE=" + DeleteType.DELETE.getDeleteInt() + " WHERE id=" + id;
        DB.executeUpdate(sql);
    }

    public static void insertUser(User user) throws Exception
    {
        String sql = "insert into user" +
                "(id,user_name,password,email,head_photo,user_type,user_state,sex,birthday,score," +
                "register_date,register_time,register_ip,visit_date,visit_time,visit_ip)" +
                "values" +
                "(null,'" +
                user.getUserName() +
                "','" +
                user.getPassword() +
                "','" +
                user.getEmail() +
                "','" +
                user.getHeadPhoto() +
                "'," +
                user.getUserType().getUserTypeInt() +
                "," +
                user.getUserState().getUserStateInt() +
                "," +
                user.getSex() +
                ",'" +
                user.getBirthday() +
                "'," +
                user.getScore() +
                ",'" +
                user.getRegisterDate() +
                "','" +
                user.getRegisterTime() +
                "','" +
                user.getRegisterIp() +
                "','" +
                user.getVisitDate() +
                "','" +
                user.getVisitTime() +
                "','" +
                user.getVisitIp() +
                "')";
        DB.executeUpdate(sql);
    }

    public synchronized static Record insertRecord(Record record) throws Exception
    {
        String sql = "insert into record" +
                "(id,user_id,groups,title,content,tag,request_date,request_time,is_delete)" +
                "values" +
                "(null," +
                record.getUserId() +
                ",'" +
                record.getGroups() +
                "','" +
                record.getTitle() +
                "','" +
                record.getContent() +
                "','" +
                record.getTag() +
                "','" +
                record.getRequestDate() +
                "','" +
                record.getRequestTime() +
                "'," +
                (record.isDelete()?1:0) +
                ")";
        DB.executeUpdate(sql);
        record = DBSelect.queryNewestRecord();
        System.out.println("2_" + record.getId());
        return record;
    }

    /**
     * 更新记录
     * @param record
     * @throws Exception
     */
    public static void updateRecord(Record record) throws Exception
    {
        String sql = "UPDATE RECORD SET " +
                "groups='" + record.getGroups() +
                "',title='" + record.getTitle() +
                "',content='" + record.getContent() +
                "',tag='" + record.getTag() +
                "',request_date='" + record.getRequestDate() +
                "',request_time='" + record.getRequestTime() +
                "',is_delete= " + (record.isDelete()?DeleteType.DELETE.getDeleteInt():DeleteType.NOT_DELETE.getDeleteInt()) +
                " WHERE id= " + record.getId() +
                " AND user_id=" + record.getUserId();
        DB.executeUpdate(sql);
    }

    /**
     * 删除记录
     * @param record
     * @throws Exception
     */
    public static void deleteRecord(Record record) throws Exception
    {
        String sql = "UPDATE RECORD SET IS_DELETE=" + DeleteType.DELETE.getDeleteInt() + " WHERE ID=" + record.getId();
        DB.executeUpdate(sql);
    }

    /**
     * 更新用户登录参数
     * @param user
     * @throws Exception
     */
    public static void updateLogin(User user) throws Exception
    {
        String sql = "update user set " +
                "visit_date='" +
                user.getVisitDate() +
                "',visit_time='" +
                user.getVisitTime() +
                "',visit_ip='" +
                user.getVisitIp() +
                "' where id=" +
                user.getId();
        DB.executeUpdate(sql);
    }

    /**
     * 增加帖子访问次数 +1次
     * @param topicId
     */
    public static void addTopicVisitTimes(int topicId) throws Exception
    {
        try
        {
            Topic topic = DBSelect.getTopicById(topicId);
            System.out.println("VIEW_TIMES=" + topic.getViewTimes());
            if(null != topic)
            {
                String sql = "UPDATE TOPIC SET VIEW_TIMES=" + (topic.getViewTimes()+1) + " WHERE ID=" + topicId;
                System.out.println("sql=" + sql);
                DB.executeUpdate(sql);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 新增工作日志
     * @param actionLog
     * @throws Exception
     */
    public synchronized static void addActionLog(ActionLog actionLog) throws Exception
    {
        String sql = "INSERT INTO ACTION_LOG(id,action_type,user_id,request_date,request_time,topic_id,reply_id) VALUES(" +
                "null," + actionLog.getActionType().getActionInt() + "," + actionLog.getUserId() + ",'" + actionLog.getRequestDate() + "','"
                + actionLog.getRequestTime() + "'," + actionLog.getTopicId() + "," + actionLog.getReplyId() + ")";
        DB.executeUpdate(sql);

        actionLog = DBSelect.queryNewestActionLog();
    }

    /**
     * 给用户加积分
     * @param userId
     * @param newScore
     * @throws Exception
     */
    public static void addScore(int userId, int newScore) throws Exception
    {
        try
        {
            User user = DBSelect.getUserById(userId);
            if(null != user)
            {
                String sql = "UPDATE USER SET SCORE=" + newScore + " WHERE ID=" + userId;
                System.out.println("sql=" + sql);
                DB.executeUpdate(sql);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 新增积分变化日志
     * @param scoreLog
     * @throws Exception
     */
    public static void addScoreLog(ScoreLog scoreLog) throws Exception
    {
        String sql = "INSERT INTO SCORE_LOG(id,action_log_id,user_id,request_date,request_time,old_score,change_score,new_score) VALUES(" +
                "null," + scoreLog.getActionLogId()+ "," + scoreLog.getUserId() + ",'" + scoreLog.getRequestDate() + "','"
                + scoreLog.getRequestTime() + "'," + scoreLog.getOldScore() + "," + scoreLog.getChangeScore() + "," + scoreLog.getNewScore() + ")";
        DB.executeUpdate(sql);
    }

    /**
     * 修改用户基础资料
     * @param id
     * @param sexType
     * @param birthday
     * @param email
     * @throws Exception
     */
    public static void updateBaseInfo(int id, SexType sexType, String birthday, String email) throws Exception
    {
        String sql = "update user set " +
                "sex=" + sexType.getSexTypeInt() + ", birthday='" + birthday + "', email='" + email +  "' " +
                "where id=" + id;
        DB.executeUpdate(sql);
    }

    /**
     * 修改用户头像
     * @param id
     * @param headPhoto
     * @throws Exception
     */
    public static void updateHeadPhoto(int id, String headPhoto) throws Exception
    {
        String sql = "update user set " +
                "head_photo='" + headPhoto + "' " +
                "where id=" + id;
        DB.executeUpdate(sql);
    }

    /**
     * 修改用户密码
     * @param id
     * @param password
     * @throws Exception
     */
    public static void updatePassword(int id, String password) throws Exception
    {
        String sql = "update user set " +
                "password='" + password + "' " +
                "where id=" + id;
        DB.executeUpdate(sql);
    }

    public static void insertWedisleUser(WedisleUser user) throws Exception
    {
        String sql = "insert into WEDISLE_USER" +
                "(id,user_name,password,type,email,is_email_validate,mobile,is_mobile_validate," +
                "user_state,error_pwd_num,friends_type,register_date,register_time,register_ip)" +
                "values" +
                "(null,'" +
                user.getUserName() +
                "','" +
                user.getPassword() +
                "'," +
                user.getType() +
                ",'" +
                user.getEmail() +
                "'," +
                (user.isEmailValidate()?1:0) +
                ",'" +
                StringUtils.trimToEmpty(user.getMobile()) +
                "'," +
                (user.isMobileValidate()?1:0) +
                "," +
                user.getUserState() +
                "," +
                user.getErrorPwdNum() +
                ",'" +
                user.getFriendsType() +
                "','" +
                user.getRegisterDate() +
                "','" +
                user.getRegisterTime() +
                "','" +
                user.getRegisterIp() +
                "')";
        DB.executeUpdate(sql);
    }

    /**
     * 修改用户亲友关系
     * @param user
     * @throws Exception
     */
    public static void updateWedisleUserFriendType(WedisleUser user) throws Exception
    {
        String sql = "update WEDISLE_USER set friends_type='" + user.getFriendsType() + "' where id=" + user.getId();
        DB.executeUpdate(sql);
    }
}