package com.gxx.record.dao.wedisle;

import com.gxx.record.dao.DB;
import com.gxx.record.entities.wedisle.WedisleMarryRegist;
import com.gxx.record.entities.wedisle.WedisleRelaFriend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ���ǼǴ�ʵ�������
 * User: Gxx
 * Time: 2014-02-13 17:49
 */
public class WedisleMarryRegistDao
{
    /**
     * ��ѯ���н��ǼǴ�
     * @return
     */
    public static List<WedisleMarryRegist> queryAllWedisleMarryRegist() throws Exception
    {
        List<WedisleMarryRegist> list = new ArrayList<WedisleMarryRegist>();
        String sql = "SELECT id,name,place,phone,time,big_image_src,image_src FROM wedisle_marry_regist order by id";
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
                String name = rs.getString("name");
                String place = rs.getString("place");
                String phone = rs.getString("phone");
                String time = rs.getString("time");
                String bigImageSrc = rs.getString("big_image_src");
                String imageSrc = rs.getString("image_src");
                WedisleMarryRegist regist = new WedisleMarryRegist(id,name,place,phone,time,bigImageSrc,imageSrc);
                list.add(regist);
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
     * ���Ƿ��Ѿ����ڸ����ֵĵǼǴ�
     * @param name
     * @return
     * @throws Exception
     */
    public static boolean isExistSameRegistName(String name) throws Exception{
        String sql = "SELECT count(*) NAME_COUNT FROM wedisle_marry_regist WHERE name='" + name + "'";
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
                int count = rs.getInt("NAME_COUNT");
                if(count > 0){
                    return true;
                }
                return false;
            }
            throw new RuntimeException("���ݿ�������������ԣ�");
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �������ǼǴ�
     * @param regist
     * @throws Exception
     */
    public static void insertWedisleMarryRegist(WedisleMarryRegist regist) throws Exception
    {
        String sql = "insert into wedisle_marry_regist" +
                "(id,name,place,phone,time,big_image_src,image_src)" +
                "values" +
                "(null,'" +
                regist.getName() +
                "','" +
                regist.getPlace() +
                "','" +
                regist.getPhone() +
                "','" +
                regist.getTime() +
                "','" +
                regist.getBigImageSrc() +
                "','" +
                regist.getImageSrc() +
                "')";
        DB.executeUpdate(sql);
    }

    /**
     * ���½��ǼǴ�
     * @param regist
     * @throws Exception
     */
    public static void updateWedisleMarryRegist(WedisleMarryRegist regist) throws Exception
    {
        String sql = "update wedisle_marry_regist set name='" +
                regist.getName() +
                "',place='" +
                regist.getPlace() +
                "',phone='" +
                regist.getPhone() +
                "',time='" +
                regist.getTime() +
                "',big_image_src='" +
                regist.getBigImageSrc() +
                "',image_src='" +
                regist.getImageSrc() +
                "' where id=" +
                regist.getId();
        DB.executeUpdate(sql);
    }

    /**
     * ɾ�����ǼǴ�
     * @param regist
     * @throws Exception
     */
    public static void deleteWedisleMarryRegist(WedisleMarryRegist regist) throws Exception
    {
        String sql = "delete from wedisle_marry_regist  where id=" + regist.getId();
        DB.executeUpdate(sql);
    }

    /**
     * ����ID��ѯ���ǼǴ�
     * @param id
     * @return
     * @throws Exception
     */
    public static WedisleMarryRegist getWedisleMarryRegistById(int id) throws Exception
    {
        String sql = "SELECT id,name,place,phone,time,big_image_src,image_src FROM wedisle_marry_regist where id=" + id;
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
                String name = rs.getString("name");
                String place = rs.getString("place");
                String phone = rs.getString("phone");
                String time = rs.getString("time");
                String bigImageSrc = rs.getString("big_image_src");
                String imageSrc = rs.getString("image_src");
                WedisleMarryRegist regist = new WedisleMarryRegist(id,name,place,phone,time,bigImageSrc,imageSrc);
                return regist;
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
     * ����name��ѯ���ǼǴ�
     * @param name
     * @return
     * @throws Exception
     */
    public static WedisleMarryRegist getWedisleMarryRegistByName(String name) throws Exception
    {
        String sql = "SELECT id,name,place,phone,time,big_image_src,image_src FROM wedisle_marry_regist where name='" + name + "'";
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
                String place = rs.getString("place");
                String phone = rs.getString("phone");
                String time = rs.getString("time");
                String bigImageSrc = rs.getString("big_image_src");
                String imageSrc = rs.getString("image_src");
                WedisleMarryRegist regist = new WedisleMarryRegist(id,name,place,phone,time,bigImageSrc,imageSrc);
                return regist;
            }
            return null;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }
}
