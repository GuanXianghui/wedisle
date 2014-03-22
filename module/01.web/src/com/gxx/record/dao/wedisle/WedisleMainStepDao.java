package com.gxx.record.dao.wedisle;

import com.gxx.record.dao.DB;
import com.gxx.record.entities.wedisle.WedisleMainStep;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ��ҳ����ʵ�������
 * User: Gxx
 * Time: 2014-2-21 11:33
 */
public class WedisleMainStepDao
{
    /**
     * ��ѯ������ҳ����
     * ��index_id˳������
     * @return
     */
    public static List<WedisleMainStep> queryAllWedisleMainSteps() throws Exception
    {
        List<WedisleMainStep> wedisleMainSteps = new ArrayList<WedisleMainStep>();
        String sql = "SELECT id, pid, level, index_id, name, article_id FROM WEDISLE_MAIN_STEP order by index_id";
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
                int pid = rs.getInt("pid");
                int level = rs.getInt("level");
                int indexId = rs.getInt("index_id");
                String name = rs.getString("name");
                int articleId = rs.getInt("article_id");
                WedisleMainStep wedisleMainStep = new WedisleMainStep(id, pid, level, indexId, name, articleId);
                wedisleMainSteps.add(wedisleMainStep);
            }
            return wedisleMainSteps;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���ݼ���1id�鼶��2
     * @param level1_id
     * @return
     * @throws Exception
     */
    public static List<WedisleMainStep> queryLevel2ByLevel1Id(int level1_id) throws Exception
    {
        List<WedisleMainStep> wedisleMainSteps = new ArrayList<WedisleMainStep>();
        String sql = "SELECT id, pid, level, index_id, name, article_id FROM WEDISLE_MAIN_STEP WHERE pid=" + level1_id
                + " and level=2 order by index_id";
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
                int pid = rs.getInt("pid");
                int level = rs.getInt("level");
                int indexId = rs.getInt("index_id");
                String name = rs.getString("name");
                int articleId = rs.getInt("article_id");
                WedisleMainStep wedisleMainStep = new WedisleMainStep(id, pid, level, indexId, name, articleId);
                wedisleMainSteps.add(wedisleMainStep);
            }
            return wedisleMainSteps;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���ݼ���2id�鼶��3
     * @param level2_id
     * @return
     * @throws Exception
     */
    public static List<WedisleMainStep> queryLevel3ByLevel2Id(int level2_id) throws Exception
    {
        List<WedisleMainStep> wedisleMainSteps = new ArrayList<WedisleMainStep>();
        String sql = "SELECT id, pid, level, index_id, name, article_id FROM WEDISLE_MAIN_STEP WHERE pid=" + level2_id
                + " and level=3 order by index_id";
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
                int pid = rs.getInt("pid");
                int level = rs.getInt("level");
                int indexId = rs.getInt("index_id");
                String name = rs.getString("name");
                int articleId = rs.getInt("article_id");
                WedisleMainStep wedisleMainStep = new WedisleMainStep(id, pid, level, indexId, name, articleId);
                wedisleMainSteps.add(wedisleMainStep);
            }
            return wedisleMainSteps;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����level��pid������Index
     * @param level
     * @param pid
     * @return
     * @throws Exception
     */
    public static int getMaxIndexFromLevelAndPid(int level, int pid) throws Exception
    {
        String sql = "SELECT MAX(index_id) max_index_id FROM WEDISLE_MAIN_STEP WHERE level=" + level + " and pid=" + pid;
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
                int maxIndexId = rs.getInt("max_index_id");
                return maxIndexId;
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
     * ������ҳ����
     * @param step
     * @throws Exception
     */
    public static void insertWedisleMainStep(WedisleMainStep step) throws Exception
    {
        String sql = "insert into WEDISLE_MAIN_STEP" +
                "(id, pid, level, index_id, name, article_id)" +
                "values" +
                "(null," +
                step.getPid() +
                "," +
                step.getLevel() +
                "," +
                step.getIndexId() +
                ",'" +
                step.getName() +
                "'," +
                step.getArticleId() +
                ")";
        DB.executeUpdate(sql);
    }

    /**
     * ������ҳ����
     * @param step
     * @throws Exception
     */
    public static void updateWedisleMainStep(WedisleMainStep step) throws Exception
    {
        String sql = "UPDATE WEDISLE_MAIN_STEP set pid=" + step.getPid() +
                ", level=" + step.getLevel() +
                ", index_id=" + step.getIndexId() +
                ", name='" + step.getName() + "'" +
                ", article_id=" + step.getArticleId() +
                " WHERE id=" + step.getId();
        DB.executeUpdate(sql);
    }

    /**
     * ɾ����ҳ����
     * @param step
     * @throws Exception
     */
    public static void deleteWedisleMainStep(WedisleMainStep step) throws Exception
    {
        String sql = "DELETE FROM WEDISLE_MAIN_STEP WHERE id=" + step.getId();
        DB.executeUpdate(sql);
    }

    /**
     * ����id��ѯ��ҳ����
     * @param id
     * @throws Exception
     */
    public static WedisleMainStep getWedisleMainStepById(int id) throws Exception
    {
        String sql = "SELECT pid, level, index_id, name, article_id FROM WEDISLE_MAIN_STEP WHERE id=" + id;
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

                int pid = rs.getInt("pid");
                int level = rs.getInt("level");
                int indexId = rs.getInt("index_id");
                String name = rs.getString("name");
                int articleId = rs.getInt("article_id");
                WedisleMainStep wedisleMainStep = new WedisleMainStep(id, pid, level, indexId, name, articleId);
                return wedisleMainStep;
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