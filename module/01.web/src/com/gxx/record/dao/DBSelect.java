/**
 * File Name:    DBSelect.java
 *
 * File Desc:    ���ݲ�ѯ������
 *
 * Product AB:   PAYGATE_1_0_0
 *
 * Product Name: PAYGATE
 *
 * Module Name:  01.core
 *
 * Module AB:    01.core
 *
 * Author:       Gxx
 *
 * History:      2013-06-26 created by Gxx
 */
package com.gxx.record.dao;

import com.gxx.record.entities.*;
import com.gxx.record.entities.wedisle.WedisleUser;
import com.gxx.record.enums.ActionType;
import com.gxx.record.enums.DeleteType;
import com.gxx.record.enums.UserState;
import com.gxx.record.enums.UserType;
import com.gxx.record.utils.ServiceDataUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * ���ݲ�ѯ������
 */
public class DBSelect
{
    /**
     * ���� ���ID + ʱ�䷶Χ + �Ƿ񾫻� + ˳��͵��� ����������
     *
     * @param sectionId ���ID
     * @param time      ʱ�䷶Χ
     * @param good      �Ƿ񾫻�
     * @param order     ˳��͵���
     * @return
     * @throws Exception
     */
    public static List queryTopicsBySectionIdAndConditions(int sectionId, String time, String good, String order)
            throws Exception
    {
        List results = queryTopicsBySectionIdAndConditionsAndDeleteType(sectionId, time, good, order, DeleteType.NOT_DELETE);
        return results;
    }

    /**
     * ���� ���ID + ʱ�䷶Χ + �Ƿ񾫻� + ˳��͵��� + �Ƿ�ɾ�� ����������
     *
     * @param sectionId  ���ID
     * @param time       ʱ�䷶Χ
     * @param good       �Ƿ񾫻�
     * @param order      ˳��͵���
     * @param deleteType �Ƿ�ɾ��
     * @return
     * @throws Exception
     */
    public static List queryTopicsBySectionIdAndConditionsAndDeleteType(int sectionId, String time, String good,
                                                                        String order, DeleteType deleteType) throws Exception
    {
        List topics = new ArrayList();
        String sql = "SELECT " +
                "id,section_id,user_id,title,content,tag,request_date,request_time,is_good,view_times,is_delete" +
                " FROM TOPIC WHERE SECTION_ID=" + sectionId;
        if (StringUtils.isNotBlank(time))//��ʱ������ѡ
        {
            java.util.Date date = new java.util.Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if ("1".equals(time))
            {
                // ȫ��ʱ��
            } else if ("2".equals(time))
            {
                // ������
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            } else if ("3".equals(time))
            {
                // ������
                calendar.add(Calendar.DATE, -1);
                date = calendar.getTime();
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            } else if ("4".equals(time))
            {
                // һ����
                calendar.add(Calendar.DATE, -6);
                date = calendar.getTime();
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            } else if ("5".equals(time))
            {
                // һ������
                calendar.add(Calendar.DATE, -29);
                date = calendar.getTime();
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            } else if ("6".equals(time))
            {
                // ��������
                calendar.add(Calendar.DATE, -89);
                date = calendar.getTime();
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            }
        }
        if (StringUtils.isNotBlank(good))//����������ѡ
        {
            sql += " AND is_good=" + good;
        }
        if (DeleteType.ALL != deleteType)
        {
            sql += " AND IS_DELETE=" + deleteType.getDeleteInt();
        }
        if (StringUtils.isNotBlank(order))//��ʱ������ѡ
        {
            if ("1".equals(order))
            {
                // ����˳��
                sql += " ORDER BY ID";
            } else if ("2".equals(order))
            {
                // ��������
                sql += " ORDER BY ID DESC";
            } else if ("3".equals(order))
            {
                // ����ظ�
                sql += " ORDER BY REQUEST_DATE DESC, REQUEST_TIME DESC";
            }
        }
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
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String tag = rs.getString("tag");
                String requestDate = rs.getString("request_date");
                String requestTime = rs.getString("request_time");
                boolean is_good = 1 == rs.getInt("is_good");
                int viewTimes = rs.getInt("view_times");
                boolean delete = 1 == rs.getInt("is_delete");
                Topic topic = new Topic(id, sectionId, userId, title, content, tag, requestDate, requestTime, is_good, viewTimes, delete);
                topics.add(topic);
            }

            return topics;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� ��ǩ + ʱ�䷶Χ + �Ƿ񾫻� + ˳��͵��� ����������
     *
     * @param tag       ��ǩ
     * @param time      ʱ�䷶Χ
     * @param good      �Ƿ񾫻�
     * @param order     ˳��͵���
     * @return
     * @throws Exception
     */
    public static List queryTopicsByTagAndConditions(String tag, String time, String good, String order)
            throws Exception
    {
        /**
         * ע�� userId �� <=0���� �Ͳ�����userId����
         */
        List results = queryTopicsByUserIdAndTagAndConditionsAndDeleteType(0, tag, time, good, order, DeleteType.NOT_DELETE);
        return results;
    }

    /**
     * ���� �û�ID + ʱ�䷶Χ + �Ƿ񾫻� + ˳��͵��� ����������
     *
     * @param userId    �û�ID
     * @param time      ʱ�䷶Χ
     * @param good      �Ƿ񾫻�
     * @param order     ˳��͵���
     * @return
     * @throws Exception
     */
    public static List queryTopicsByUserIdAndConditions(int userId, String time, String good, String order)
            throws Exception
    {
        List results = queryTopicsByUserIdAndTagAndConditionsAndDeleteType(userId, null, time, good, order, DeleteType.NOT_DELETE);
        return results;
    }

    /**
     * ���� ��ǩ + ʱ�䷶Χ + �Ƿ񾫻� + ˳��͵��� + �Ƿ�ɾ�� ����������
     *
     * @param tag        ��ǩ
     * @param time       ʱ�䷶Χ
     * @param good       �Ƿ񾫻�
     * @param order      ˳��͵���
     * @param deleteType �Ƿ�ɾ��
     * @return
     * @throws Exception
     */
    public static List queryTopicsByUserIdAndTagAndConditionsAndDeleteType(int userId, String tag, String time, String good,
                                                                        String order, DeleteType deleteType) throws Exception
    {
        List topics = new ArrayList();
        String sql = "SELECT " +
                "id,section_id,user_id,title,content,tag,request_date,request_time,is_good,view_times,is_delete" +
                " FROM TOPIC WHERE 1=1";
        if(userId > 0)
        {
            sql += " AND user_id=" + userId;
        }
        if(StringUtils.isNotBlank(tag))//����ǩ����ѡ ��title��tag
        {
            sql += " AND (title like '%" + tag + "%' OR tag like '%" + tag + "%')";
        }
        if (StringUtils.isNotBlank(time))//��ʱ������ѡ
        {
            java.util.Date date = new java.util.Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if ("1".equals(time))
            {
                // ȫ��ʱ��
            } else if ("2".equals(time))
            {
                // ������
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            } else if ("3".equals(time))
            {
                // ������
                calendar.add(Calendar.DATE, -1);
                date = calendar.getTime();
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            } else if ("4".equals(time))
            {
                // һ����
                calendar.add(Calendar.DATE, -6);
                date = calendar.getTime();
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            } else if ("5".equals(time))
            {
                // һ������
                calendar.add(Calendar.DATE, -29);
                date = calendar.getTime();
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            } else if ("6".equals(time))
            {
                // ��������
                calendar.add(Calendar.DATE, -89);
                date = calendar.getTime();
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            }
        }
        if (StringUtils.isNotBlank(good))//����������ѡ
        {
            sql += " AND is_good=" + good;
        }
        if (DeleteType.ALL != deleteType)
        {
            sql += " AND IS_DELETE=" + deleteType.getDeleteInt();
        }
        if (StringUtils.isNotBlank(order))//��ʱ������ѡ
        {
            if ("1".equals(order))
            {
                // ����˳��
                sql += " ORDER BY ID";
            } else if ("2".equals(order))
            {
                // ��������
                sql += " ORDER BY ID DESC";
            } else if ("3".equals(order))
            {
                // ����ظ�
            }
        }
        System.out.println("_____________>>>SQL=" + sql);
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
                int id = rs.getInt("id");
                int sectionId = rs.getInt("section_id");
                int userIdInt = rs.getInt("user_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String tagStr = rs.getString("tag");
                String requestDate = rs.getString("request_date");
                String requestTime = rs.getString("request_time");
                boolean is_good = 1 == rs.getInt("is_good");
                int viewTimes = rs.getInt("view_times");
                boolean delete = 1 == rs.getInt("is_delete");
                Topic topic = new Topic(id, sectionId, userIdInt, title, content, tagStr, requestDate, requestTime, is_good, viewTimes, delete);
                topics.add(topic);
            }

            return topics;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� ����ID ������ ���� �������ӷ��ʴ��� +1��
     * @param id
     * @return
     * @throws Exception
     */
    public static Topic getTopicByIdWithAddVisitTimes(int id) throws Exception
    {
        DBUpdate.addTopicVisitTimes(id);
        return getTopicById(id);
    }

    /**
     * ���� ����ID ������
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static Topic getTopicById(int id) throws Exception
    {
        return getTopicByIdAndDeleteType(id, DeleteType.NOT_DELETE);
    }

    /**
     * ���� ����ID + �Ƿ�ɾ�� ������
     *
     * @param id
     * @param deleteType
     * @return
     * @throws Exception
     */
    public static Topic getTopicByIdAndDeleteType(int id, DeleteType deleteType) throws Exception
    {
        String sql = "SELECT " +
                "id,section_id,user_id,title,content,tag,request_date,request_time,is_good,view_times,is_delete" +
                " FROM TOPIC WHERE id=" + id;
        if (DeleteType.ALL != deleteType)
        {
            sql += " AND IS_DELETE=" + deleteType.getDeleteInt();
        }
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
                int sectionId = rs.getInt("section_id");
                int userId = rs.getInt("user_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String tag = rs.getString("tag");
                String requestDate = rs.getString("request_date");
                String requestTime = rs.getString("request_time");
                boolean good = 1 == rs.getInt("is_good");
                int viewTimes = rs.getInt("view_times");
                boolean delete = 1 == rs.getInt("is_delete");
                Topic topic = new Topic(id, sectionId, userId, title, content, tag, requestDate, requestTime, good, viewTimes, delete);
                return topic;
            }
            throw new RuntimeException("�����Ӳ����ڣ�");
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� ����ID �����
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static Reply getReplyById(int id) throws Exception
    {
        return getReplyByIdAndDeleteType(id, DeleteType.NOT_DELETE);
    }

    /**
     * ���� ����ID + �Ƿ�ɾ�� �����
     *
     * @param id
     * @param deleteType
     * @return
     * @throws Exception
     */
    public static Reply getReplyByIdAndDeleteType(int id, DeleteType deleteType) throws Exception
    {
        String sql = "SELECT " +
                "id,topic_id,user_id,content,request_date,request_time,is_delete" +
                " FROM REPLY WHERE id=" + id;
        if (DeleteType.ALL != deleteType)
        {
            sql += " AND IS_DELETE=" + deleteType.getDeleteInt();
        }
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
                int topicId = rs.getInt("topic_id");
                int userId = rs.getInt("user_id");
                String content = rs.getString("content");
                String requestDate = rs.getString("request_date");
                String requestTime = rs.getString("request_time");
                boolean delete = 1 == rs.getInt("is_delete");
                Reply reply = new Reply(id, topicId, userId, content, requestDate, requestTime, delete);
                return reply;
            }
            throw new RuntimeException("�����Ӳ����ڣ�");
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� ����ID + �û�id + ˳����� �����лظ�
     *
     * @param topicId ����ID
     * @param userId �û�id ֻ�鿴���û��ظ������� all��������
     * @param order ˳�����
     * @return
     * @throws Exception
     */
    public static List queryRepliesByTopicId(int topicId, String userId, String order) throws Exception
    {
        return queryRepliesByTopicIdAndDeleteType(topicId, userId, order, DeleteType.NOT_DELETE);
    }

    /**
     * ���� ����ID + �û�id + ˳����� + �Ƿ�ɾ�� �����лظ�
     *
     * @param topicId ����ID
     * @param userId �û�id ֻ�鿴���û��ظ������� all��������
     * @param order ˳�����
     * @param deleteType �Ƿ�ɾ��
     * @return
     * @throws Exception
     */
    public static List queryRepliesByTopicIdAndDeleteType(int topicId, String userId, String order, DeleteType deleteType) throws Exception
    {
        List replies = new ArrayList();
        String sql = "SELECT " +
                "id,topic_id,user_id,content,request_date,request_time,is_delete" +
                " FROM REPLY WHERE topic_id=" + topicId;
        if(StringUtils.isNotBlank(userId) && !"all".equalsIgnoreCase(userId))
        {
            sql += " AND user_id='" + userId + "'";
        }
        if (DeleteType.ALL != deleteType)
        {
            sql += " AND IS_DELETE=" + deleteType.getDeleteInt();
        }
        if (StringUtils.isNotBlank(order))//��ʱ������ѡ
        {
            if ("1".equals(order))
            {
                // ����˳��
                sql += " ORDER BY ID";
            } else if ("2".equals(order))
            {
                // ��������
                sql += " ORDER BY ID DESC";
            }
        }
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
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                String content = rs.getString("content");
                String requestDate = rs.getString("request_date");
                String requestTime = rs.getString("request_time");
                boolean delete = 1 == rs.getInt("is_delete");
                Reply reply = new Reply(id, topicId, user_id, content, requestDate, requestTime, delete);
                replies.add(reply);
            }

            return replies;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� �û��� ���û�
     *
     * @param userName
     * @return
     * @throws Exception
     */
    public static User getUserByUserName(String userName) throws Exception
    {
        User user = null;
        String sql = "SELECT " +
                "id,user_name,password,email,head_photo,user_type,user_state,sex,birthday,score,register_date,register_time,register_ip,visit_date,visit_time,visit_ip" +
                " FROM USER WHERE user_name='" + userName + "'";
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
                int id = rs.getInt("id");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String headPhoto = rs.getString("head_photo");
                UserType userType = UserType.getUserType(rs.getInt("user_type"));
                UserState userState = UserState.getUserState(rs.getInt("user_state"));
                int sex = rs.getInt("sex");
                String birthday = rs.getString("birthday");
                int score = rs.getInt("score");
                String registerDate = rs.getString("register_date");
                String registerTime = rs.getString("register_time");
                String registerIp = rs.getString("register_ip");
                String visitDate = rs.getString("visit_date");
                String visitTime = rs.getString("visit_time");
                String visitIp = rs.getString("visit_ip");
                user = new User(id, userName, password, email, headPhoto, userType, userState, sex, birthday, score,
                        registerDate, registerTime, registerIp, visitDate, visitTime, visitIp);
            }

            return user;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� �û�ID ���û�
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static User getUserById(int id) throws Exception
    {
        User user = null;
        String sql = "SELECT " +
                "id,user_name,password,email,head_photo,user_type,user_state,sex,birthday,score,register_date,register_time,register_ip,visit_date,visit_time,visit_ip" +
                " FROM USER WHERE id=" + id;
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
                String userName = rs.getString("user_name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String headPhoto = rs.getString("head_photo");
                UserType userType = UserType.getUserType(rs.getInt("user_type"));
                UserState userState = UserState.getUserState(rs.getInt("user_state"));
                int sex = rs.getInt("sex");
                String birthday = rs.getString("birthday");
                int score = rs.getInt("score");
                String registerDate = rs.getString("register_date");
                String registerTime = rs.getString("register_time");
                String registerIp = rs.getString("register_ip");
                String visitDate = rs.getString("visit_date");
                String visitTime = rs.getString("visit_time");
                String visitIp = rs.getString("visit_ip");
                user = new User(id, userName, password, email, headPhoto, userType, userState, sex, birthday, score,
                        registerDate, registerTime, registerIp, visitDate, visitTime, visitIp);
            }

            return user;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� ������־ID �鶯����־
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static ActionLog getActionLogById(int id) throws Exception
    {
        ActionLog actionLog = null;
        String sql = "SELECT " +
                "id,action_type,user_id,request_date,request_time,topic_id,reply_id" +
                " FROM ACTION_LOG WHERE id=" + id;
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
                ActionType actionType = ActionType.getActionType(rs.getInt("action_type"));
                int userId = rs.getInt("user_id");
                String requestDate = rs.getString("request_date");
                String requestTime = rs.getString("request_time");
                int topicId = rs.getInt("topic_id");
                int replyId = rs.getInt("reply_id");
                actionLog = new ActionLog(id, actionType, userId, requestDate, requestTime, topicId, replyId);
            }

            return actionLog;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� ���ID �� ���� ����������
     *
     * @param sectionId
     * @param date      yyyyMMdd
     * @return
     * @throws Exception
     */
    public static int queryTopicCountWithSectionIdAndDate(Integer sectionId, String date) throws Exception
    {
        String sql = "SELECT COUNT(1) COUNT FROM TOPIC WHERE 1=1";
        if (null != sectionId)
        {
            sql += " AND section_id=" + sectionId.intValue();
        }
        if (StringUtils.isNotBlank(date))
        {
            sql += " AND request_date='" + date + "'";
        }
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
                return rs.getInt("COUNT");
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
     * ���� ���ID ���������
     *
     * @param sectionId
     * @return
     * @throws Exception
     */
    public static int queryReplyCountWithSectionId(Integer sectionId) throws Exception
    {
        String sql = "SELECT COUNT(1) COUNT FROM TOPIC t,REPLY r WHERE t.id=r.topic_id " +
                "AND t.section_id=" + sectionId;
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
                return rs.getInt("COUNT");
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
     * ��ѯ�û�����
     *
     * @return
     * @throws Exception
     */
    public static int queryUserCount() throws Exception
    {
        String sql = "SELECT COUNT(1) COUNT FROM USER";
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
                return rs.getInt("COUNT");
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
     * �õ�����ע����û�
     *
     * @return
     * @throws Exception
     */
    public static User queryNewestUser() throws Exception
    {
        int maxId = -1;
        String sql = "SELECT MAX(ID) MAXID FROM USER WHERE USER_STATE!=" + UserState.DELETE.getUserStateInt();
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
                maxId = rs.getInt("MAXID");
            }
            if (-1 == maxId)
            {
                return null;
            }
            return getUserById(maxId);
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� ���ID �õ���������
     *
     * @return
     * @throws Exception
     */
    public static Topic queryNewestTopicBySectionId(int sectionId) throws Exception
    {
        int maxId = -1;
        String sql = "SELECT MAX(ID) MAXID FROM TOPIC WHERE section_id=" + sectionId
                + " AND IS_DELETE=" + DeleteType.NOT_DELETE.getDeleteInt();
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
                /**
                 * ======================
                 * ע�⣺���û�м�¼MAX(ID)��ӡ������NULL����getInt����0
                 * ======================
                 */
                maxId = rs.getInt("MAXID");
            }
            if (0 >= maxId)
            {
                return null;
            }
            return getTopicById(maxId);
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� ���ID �õ����� ���µ� ����
     *
     * @return
     * @throws Exception
     */
    public static Topic queryNewestUpdateTopicBySectionId(int sectionId) throws Exception
    {
        int newestId = -1;
        String sql = "SELECT ID FROM TOPIC WHERE section_id=" + sectionId
                + " AND IS_DELETE=" + DeleteType.NOT_DELETE.getDeleteInt() + " ORDER BY REQUEST_DATE DESC," +
                "REQUEST_TIME DESC";
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
                newestId = rs.getInt("ID");
                break;//ȡ����һ�� ��������
            }
            if (0 >= newestId)
            {
                return null;
            }
            return getTopicById(newestId);
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� ����ID �õ����»���
     *
     * @return
     * @throws Exception
     */
    public static Reply queryNewestReplyByTopicId(int topicId) throws Exception
    {
        int maxId = -1;
        String sql = "SELECT MAX(ID) MAXID FROM REPLY WHERE topic_id=" + topicId
                + " AND IS_DELETE=" + DeleteType.NOT_DELETE.getDeleteInt();
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
                /**
                 * ======================
                 * ע�⣺���û�м�¼MAX(ID)��ӡ������NULL����getInt����0
                 * ======================
                 */
                maxId = rs.getInt("MAXID");
            }
            if (0 >= maxId)
            {
                return null;
            }
            return getReplyById(maxId);
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� �û�ID + ���� + ����ֵ ����������
     *
     * @param userId        �û�ID
     * @param conditionType ����
     * @param type          ����ֵ
     * @return
     * @throws Exception
     */
    public static List queryTopicsByUserIdAndConditions(int userId, String conditionType, String type) throws Exception
    {
        List results = queryTopicsByUserIdAndConditionsAndDeleteType(userId, conditionType, type, DeleteType.NOT_DELETE);
        return results;
    }

    /**
     * ���� �û�ID + ���� + ����ֵ + �Ƿ�ɾ�� ����������
     *
     * @param userId        �û�ID
     * @param conditionType ����
     * @param type          ����ֵ
     * @param deleteType    �Ƿ�ɾ��
     * @return
     * @throws Exception
     */
    public static List queryTopicsByUserIdAndConditionsAndDeleteType(int userId, String conditionType,
                                                                     String type, DeleteType deleteType) throws Exception
    {
        List topics = new ArrayList();
        String sql = "SELECT " +
                "id,section_id,user_id,title,content,tag,request_date,request_time,is_good,view_times,is_delete" +
                " FROM TOPIC WHERE user_id=" + userId;
        if ("good".equalsIgnoreCase(conditionType))//����������ѡ
        {
            sql += " AND is_good=" + type;
        }
        if (DeleteType.ALL != deleteType)
        {
            sql += " AND IS_DELETE=" + deleteType.getDeleteInt();
        }
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
                int id = rs.getInt("id");
                int sectionId = rs.getInt("section_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String tag = rs.getString("tag");
                String requestDate = rs.getString("request_date");
                String requestTime = rs.getString("request_time");
                boolean good = 1 == rs.getInt("is_good");
                int viewTimes = rs.getInt("view_times");
                boolean delete = 1 == rs.getInt("is_delete");
                Topic topic = new Topic(id, sectionId, userId, title, content, tag, requestDate, requestTime, good, viewTimes, delete);
                topics.add(topic);
            }

            return topics;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� �û�ID �����л���
     *
     * @param userId        �û�ID
     * @return
     * @throws Exception
     */
    public static List queryRepliesByUserId(int userId) throws Exception
    {
        List results = queryRepliesByUserIdAndDeleteType(userId, DeleteType.NOT_DELETE);
        return results;
    }

    /**
     * ���� �û�ID + �Ƿ�ɾ�� ����������
     *
     * @param userId        �û�ID
     * @param deleteType    �Ƿ�ɾ��
     * @return
     * @throws Exception
     */
    public static List queryRepliesByUserIdAndDeleteType(int userId, DeleteType deleteType) throws Exception
    {
        List replies = new ArrayList();
        String sql = "SELECT " +
                "id,topic_id,user_id,content,request_date,request_time,is_delete" +
                " FROM REPLY WHERE user_id=" + userId;
        if (DeleteType.ALL != deleteType)
        {
            sql += " AND IS_DELETE=" + deleteType.getDeleteInt();
        }
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
                int id = rs.getInt("id");
                int topicId = rs.getInt("topic_id");
                String content = rs.getString("content");
                String requestDate = rs.getString("request_date");
                String requestTime = rs.getString("request_time");
                boolean delete = 1 == rs.getInt("is_delete");
                Reply reply = new Reply(id, topicId, userId, content, requestDate, requestTime, delete);
                replies.add(reply);
            }

            return replies;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �õ���������
     * @return
     * @throws Exception
     */
    public static Topic queryNewestTopic() throws Exception
    {
        int maxId = -1;
        String sql = "SELECT MAX(ID) MAXID FROM TOPIC"
                + " WHERE IS_DELETE=" + DeleteType.NOT_DELETE.getDeleteInt();
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
                maxId = rs.getInt("MAXID");
            }
            if (-1 == maxId)
            {
                return null;
            }
            return getTopicById(maxId);
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �õ����»���
     * @return
     * @throws Exception
     */
    public static Reply queryNewestReply() throws Exception
    {
        int maxId = -1;
        String sql = "SELECT MAX(ID) MAXID FROM REPLY"
                + " WHERE IS_DELETE=" + DeleteType.NOT_DELETE.getDeleteInt();
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
                maxId = rs.getInt("MAXID");
            }
            if (-1 == maxId)
            {
                return null;
            }
            return getReplyById(maxId);
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �õ������û�������־
     *
     * @return
     * @throws Exception
     */
    public static ActionLog queryNewestActionLog() throws Exception
    {
        int maxId = -1;
        String sql = "SELECT MAX(ID) MAXID FROM ACTION_LOG";
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
                maxId = rs.getInt("MAXID");
            }
            if (-1 == maxId)
            {
                return null;
            }
            return getActionLogById(maxId);
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �õ������û�������־
     *
     * @return
     * @throws Exception
     */
    public static Record queryNewestRecord() throws Exception
    {
        int maxId = -1;
        String sql = "SELECT MAX(ID) MAXID FROM RECORD"
                + " WHERE IS_DELETE=" + DeleteType.NOT_DELETE.getDeleteInt();
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
                maxId = rs.getInt("MAXID");
            }
            if (-1 == maxId)
            {
                return null;
            }
            System.out.println("MAXID=" + maxId);
            return getRecordByRecordId(maxId);
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� �û�ID + �������� + ���� �鶯����
     * @param userId
     * @param actionType
     * @param requestDate
     * @return
     * @throws Exception
     */
    public static int queryActionLogCountByUserAndTypeAndDate(int userId, ActionType actionType, String requestDate) throws Exception
    {
        String sql = "SELECT COUNT(1) COUNT FROM ACTION_LOG t WHERE t.user_id=" + userId
                + " AND t.action_type=" + actionType.getActionInt() + " AND t.request_date='" + requestDate + "'";
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
                return rs.getInt("COUNT");
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
     * �����û�Id���¼����
     * @param userId
     * @return
     */
    public static List queryRecordGroupListByUserId(int userId) throws Exception
    {
        List groups = new ArrayList();
        String sql = "SELECT DISTINCT GROUPS FROM RECORD WHERE USER_ID=" + userId;
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
                String group = rs.getString("GROUPS");
                groups.add(group);
            }

            return groups;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� �û�ID + ʱ�䷶Χ + ���� + ˳��͵��� �����м�¼
     *
     * @param userId    �û�ID
     * @param time      ʱ�䷶Χ
     * @param groups      ����
     * @param order     ˳��͵���
     * @return
     * @throws Exception
     */
    public static List queryRecordsByUserIdAndConditions(int userId, String time, String groups, String order)
            throws Exception
    {
        List results = queryRecordsByUserIdAndTagAndConditionsAndDeleteType(userId, time, groups, order, DeleteType.NOT_DELETE);
        return results;
    }

    /**
     * ���� �û�ID + ʱ�䷶Χ + ���� + ˳��͵��� + �Ƿ�ɾ�� �����м�¼
     *
     * @param userId    �û�ID
     * @param time      ʱ�䷶Χ
     * @param groups      ����
     * @param order     ˳��͵���
     * @param deleteType �Ƿ�ɾ��
     * @return
     * @throws Exception
     */
    public static List queryRecordsByUserIdAndTagAndConditionsAndDeleteType(int userId, String time, String groups,
                                                                            String order, DeleteType deleteType) throws Exception
    {
        List records = new ArrayList();
        String sql = "SELECT " +
                "id,user_id,groups,title,content,tag,request_date,request_time,is_delete" +
                " FROM RECORD WHERE 1=1";
        if(userId > 0)//���û�ID����ѡ
        {
            sql += " AND user_id=" + userId;
        }
        if (StringUtils.isNotBlank(time))//��ʱ������ѡ
        {
            java.util.Date date = new java.util.Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if ("1".equals(time))
            {
                // ȫ��ʱ��
            } else if ("2".equals(time))
            {
                // ������
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            } else if ("3".equals(time))
            {
                // ������
                calendar.add(Calendar.DATE, -1);
                date = calendar.getTime();
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            } else if ("4".equals(time))
            {
                // һ����
                calendar.add(Calendar.DATE, -6);
                date = calendar.getTime();
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            } else if ("5".equals(time))
            {
                // һ������
                calendar.add(Calendar.DATE, -29);
                date = calendar.getTime();
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            } else if ("6".equals(time))
            {
                // ��������
                calendar.add(Calendar.DATE, -89);
                date = calendar.getTime();
                sql += " AND request_date>='" + ServiceDataUtil.getDate(date) + "'";
            }
        }
        if (StringUtils.isNotBlank(groups))//����������ѡ
        {
            sql += " AND groups='" + groups + "'";
        }
        if (DeleteType.ALL != deleteType)
        {
            sql += " AND IS_DELETE=" + deleteType.getDeleteInt();
        }
        if (StringUtils.isNotBlank(order))//��ʱ������ѡ
        {
            if ("1".equals(order))
            {
                // ����˳��
                sql += " ORDER BY ID";
            } else if ("2".equals(order))
            {
                // ��������
                sql += " ORDER BY ID DESC";
            }
        }
        System.out.println("_____________>>>SQL=" + sql);
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
                int id = rs.getInt("id");
                int userIdInt = rs.getInt("user_id");
                String groupsStr = rs.getString("groups");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String tag = rs.getString("tag");
                String requestDate = rs.getString("request_date");
                String requestTime = rs.getString("request_time");
                boolean delete = 1 == rs.getInt("is_delete");
                Record record = new Record(id, userIdInt, groupsStr, title, content, tag, requestDate, requestTime, delete);
                records.add(record);
            }

            return records;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� ��¼ID ���¼
     *
     * @param recordId  ��¼ID
     * @return
     * @throws Exception
     */
    public static Record getRecordByRecordId(int recordId) throws Exception
    {
        // ע�⣺ userId>0�ŷ����ѯ
        Record record = getRecordByUserIdAndRecordIdAndDeleteType(-1, recordId, DeleteType.NOT_DELETE);
        return record;
    }

    /**
     * ���� �û�ID + ��¼ID ���¼
     *
     * @param userId    �û�ID
     * @param recordId  ��¼ID
     * @return
     * @throws Exception
     */
    public static Record getRecordByUserIdAndRecordId(int userId, int recordId)
            throws Exception
    {
        Record record = getRecordByUserIdAndRecordIdAndDeleteType(userId, recordId, DeleteType.NOT_DELETE);
        return record;
    }

    /**
     * ���� �û�ID + ��¼ID + �Ƿ�ɾ�� ���¼
     * @param userId
     * @param recordId
     * @param deleteType
     * @return
     */
    private static Record getRecordByUserIdAndRecordIdAndDeleteType(int userId, int recordId, DeleteType deleteType) throws Exception
    {
        Record record = null;
        String sql = "SELECT " +
                "id,user_id,groups,title,content,tag,request_date,request_time,is_delete" +
                " FROM RECORD WHERE 1=1 AND id=" + recordId + " AND is_delete=" + deleteType.getDeleteInt();
        if(userId > 0)
        {
            sql += " AND user_id=" + userId;
        }
        System.out.println("sql=" + sql);
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
                int userIdInt = rs.getInt("user_id");
                String groups = rs.getString("groups");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String tag = rs.getString("tag");
                String requestDate = rs.getString("request_date");
                String requestTime = rs.getString("request_time");
                boolean delete = 1 == rs.getInt("is_delete");
                record = new Record(recordId, userIdInt, groups, title, content, tag, requestDate, requestTime, delete);
                System.out.println("3_" + record.getId());
            }

            return record;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� �û��� ���û�
     * @param userName
     * @return
     * @throws Exception
     */
    public static WedisleUser getWedisleUserByUserName(String userName) throws Exception
    {
        WedisleUser user = null;
        String sql = "SELECT id,password,type,email,is_email_validate,mobile,is_mobile_validate," +
                "user_state,error_pwd_num,friends_type,register_date,register_time,register_ip" +
                " FROM WEDISLE_USER WHERE user_name='" + userName + "'";
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
                int id = rs.getInt("id");
                String password = rs.getString("password");
                int type = rs.getInt("type");
                String email = rs.getString("email");
                boolean emailValidate = 1 == rs.getInt("is_email_validate");
                String mobile = rs.getString("mobile");
                boolean mobileValidate = 1 == rs.getInt("is_mobile_validate");
                int userState = rs.getInt("user_state");
                int errorPwdNum = rs.getInt("error_pwd_num");
                String friendsType = rs.getString("friends_type");
                String registerDate = rs.getString("register_date");
                String registerTime = rs.getString("register_time");
                String registerIp = rs.getString("register_ip");
                user = new WedisleUser(id, userName, password, type, email, emailValidate,
                        mobile, mobileValidate, userState, errorPwdNum, friendsType, registerDate,
                        registerTime, registerIp);
            }

            return user;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� id ���û�
     * @param id
     * @return
     * @throws Exception
     */
    public static WedisleUser getWedisleUserById(int id) throws Exception
    {
        WedisleUser user = null;
        String sql = "SELECT user_name,password,type,email,is_email_validate,mobile,is_mobile_validate," +
                "user_state,error_pwd_num,friends_type,register_date,register_time,register_ip" +
                " FROM WEDISLE_USER WHERE id=" + id;
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
                String userName = rs.getString("user_name");
                String password = rs.getString("password");
                int type = rs.getInt("type");
                String email = rs.getString("email");
                boolean emailValidate = 1 == rs.getInt("is_email_validate");
                String mobile = rs.getString("mobile");
                boolean mobileValidate = 1 == rs.getInt("is_mobile_validate");
                int userState = rs.getInt("user_state");
                int errorPwdNum = rs.getInt("error_pwd_num");
                String friendsType = rs.getString("friends_type");
                String registerDate = rs.getString("register_date");
                String registerTime = rs.getString("register_time");
                String registerIp = rs.getString("register_ip");
                user = new WedisleUser(id, userName, password, type, email, emailValidate,
                        mobile, mobileValidate, userState, errorPwdNum, friendsType, registerDate,
                        registerTime, registerIp);
            }

            return user;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �������û�
     * @return
     * @throws Exception
     */
    public static List<WedisleUser> queryAllWedisleUsers() throws Exception
    {
        List<WedisleUser> list = new ArrayList<WedisleUser>();
        String sql = "SELECT id, user_name,password,type,email,is_email_validate,mobile,is_mobile_validate," +
                "user_state,error_pwd_num,friends_type,register_date,register_time,register_ip" +
                " FROM WEDISLE_USER";
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
                int id = rs.getInt("id");
                String userName = rs.getString("user_name");
                String password = rs.getString("password");
                int type = rs.getInt("type");
                String email = rs.getString("email");
                boolean emailValidate = 1 == rs.getInt("is_email_validate");
                String mobile = rs.getString("mobile");
                boolean mobileValidate = 1 == rs.getInt("is_mobile_validate");
                int userState = rs.getInt("user_state");
                int errorPwdNum = rs.getInt("error_pwd_num");
                String friendsType = rs.getString("friends_type");
                String registerDate = rs.getString("register_date");
                String registerTime = rs.getString("register_time");
                String registerIp = rs.getString("register_ip");
                WedisleUser user = new WedisleUser(id, userName, password, type, email, emailValidate,
                        mobile, mobileValidate, userState, errorPwdNum, friendsType, registerDate,
                        registerTime, registerIp);
                list.add(user);
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
     * ��������ҳ�����������������û������������
     * @param userId
     * @return
     * @throws Exception
     */
    public static List<Integer> queryAddStepIdsByUserId(int userId) throws Exception
    {
        List<Integer> list = new ArrayList<Integer>();
        String sql = "SELECT id from wedisle_main_step where level=3"
                + " AND id not in (SELECT step_id from wedisle_user_step where user_id=" + userId + ")";
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
                int id = rs.getInt("id");
                list.add(new Integer(id));
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
     * ɾ�����в�����ҳ����������������
     * @param userId
     * @return
     * @throws Exception
     */
    public static List<Integer> queryDeleteStepIdsByUserId(int userId) throws Exception
    {
        List<Integer> list = new ArrayList<Integer>();
        String sql = "SELECT step_id from wedisle_user_step where user_id=" + userId
                + " AND step_id not in (SELECT id from wedisle_main_step where level=3)";
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
                int stepId = rs.getInt("step_id");
                list.add(new Integer(stepId));
            }

            return list;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }
}
