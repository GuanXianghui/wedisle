package ztree.dao;

import com.gxx.record.dao.DB;
import com.gxx.record.dao.DBUpdate;
import ztree.entities.Article;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ���±������
 */
public class ArticleDao
{
    /**
     * �鵱ǰĿ¼���ж���Ԫ��
     * @param pid
     * @return
     * @throws Exception
     */
    public static int getCountByPid(int pid) throws Exception
    {
        String sql = "SELECT count(*) count FROM ARTICLE WHERE pid=" + pid;
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
                int count = rs.getInt("count");
                return count;
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
     * �鵱ǰĿ¼������index_id
     * @param pid
     * @return
     * @throws Exception
     */
    public static int getMaxIndexIdByPid(int pid) throws Exception
    {
        String sql = "SELECT max(index_id) index_id FROM ARTICLE WHERE pid=" + pid;
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
                if(null == rs.getObject("index_id"))
                {
                    return 0;
                } else
                {
                    int count = rs.getInt("index_id");
                    return count;
                }
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
     * �� id�����ֶε����ֵ
     * @return
     * @throws Exception
     */
    public static int getMaxId() throws Exception
    {
        String sql = "SELECT MAX(id) mid FROM ARTICLE";
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
                int mid = rs.getInt("mid");
                return mid;
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
     * �����ڵ�
     * @param article
     * @throws Exception
     */
    public static void addNode(Article article) throws Exception
    {
        String sql = "insert into article(id, pid, index_id, name, is_article_type, content, image_ids, " +
                "request_date, request_time, is_delete)values(" +
                "null, " +
                article.getPid() + ", " +
                article.getIndexId() +  ", " +
                "'" + article.getName() + "', " +
                (article.isArticleType() ? "1" : "0") + ", " +
                "'" + article.getContent() + "', " +
                "'" + article.getImageIds() + "', " +
                "'" + article.getRequestDate() + "', " +
                "'" + article.getRequestTime() + "', " +
                (article.isDelete() ? "1" : "0") + ")";
        DB.executeUpdate(sql);

        // ���õ�ǰ����ID
        article.setId(getMaxId());
    }

    /**
     * ��ѯ����Articleʵ��ŵ�List��
     * @return
     */
    public static List<Article> queryAllArticles() throws Exception
    {
        List<Article> articles = new ArrayList<Article>();
        String sql = "SELECT id, pid, index_id, name, is_article_type, content, image_ids, " +
                "request_date, request_time, is_delete FROM ARTICLE WHERE IS_DELETE=0 ORDER BY index_id";
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
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setPid(rs.getInt("pid"));
                article.setIndexId(rs.getInt("index_id"));
                article.setName(rs.getString("name"));
                article.setArticleType(1 == rs.getInt("is_article_type"));
                article.setContent(rs.getString("content"));
                article.setImageIds(rs.getString("image_ids"));
                article.setRequestDate(rs.getString("request_date"));
                article.setRequestTime(rs.getString("request_time"));
                article.setDelete(1 == rs.getInt("is_delete"));
                articles.add(article);
            }
            return articles;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �ݹ��ѯ Article�µ�����Article
     * @return
     */
    public static List<Article> queryAllSubArticlesByPid(int pid) throws Exception
    {
        List<Article> articles = new ArrayList<Article>();
        String sql = "SELECT id, pid, index_id, name, is_article_type, content, image_ids, " +
                "request_date, request_time, is_delete FROM ARTICLE WHERE pid=" + pid;
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
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setPid(rs.getInt("pid"));
                article.setIndexId(rs.getInt("index_id"));
                article.setName(rs.getString("name"));
                article.setArticleType(1 == rs.getInt("is_article_type"));
                article.setContent(rs.getString("content"));
                article.setImageIds(rs.getString("image_ids"));
                article.setRequestDate(rs.getString("request_date"));
                article.setRequestTime(rs.getString("request_time"));
                article.setDelete(1 == rs.getInt("is_delete"));
                articles.add(article);

                if(article.isArticleType())
                {
                    articles.addAll(queryAllSubArticlesByPid(article.getId()));
                }
            }
            return articles;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����ID��Article
     * @param id
     * @return
     */
    public static Article getArticleById(int id) throws Exception
    {
        Article article = null;
        String sql = "SELECT id, pid, index_id, name, is_article_type, content, image_ids, " +
                "request_date, request_time, is_delete FROM ARTICLE WHERE id=" + id;
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
                article = new Article();
                article.setId(rs.getInt("id"));
                article.setPid(rs.getInt("pid"));
                article.setIndexId(rs.getInt("index_id"));
                article.setName(rs.getString("name"));
                article.setArticleType(1 == rs.getInt("is_article_type"));
                article.setContent(rs.getString("content"));
                article.setImageIds(rs.getString("image_ids"));
                article.setRequestDate(rs.getString("request_date"));
                article.setRequestTime(rs.getString("request_time"));
                article.setDelete(1 == rs.getInt("is_delete"));
            }
            return article;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �޸�Article����
     * @param article
     * @throws Exception
     */
    public static void updateArticle(Article article) throws Exception
    {
        String sql = "UPDATE ARTICLE SET " +
                "pid=" + article.getPid() + ", " +
                "index_id=" + article.getIndexId() + ", " +
                "name='" + article.getName() + "', " +
                "is_article_type=" + (article.isArticleType()?"1":"0") + ", " +
                "content='" + article.getContent() + "', " +
                "image_ids='" + article.getImageIds() + "', " +
                "request_date='" + article.getRequestDate() + "', " +
                "request_time='" + article.getRequestTime() + "', " +
                "is_delete=" + (article.isDelete()?"1":"0") + " " +
                "WHERE id=" + article.getId();
        DB.executeUpdate(sql);
    }

    /**
     * targetArticleͬһ���� ����/������targetArticle ����index_id��+1
     * @param pid
     * @param indexId
     * @param isContains
     */
    public static void addIndexIdByOne(int pid, int indexId, boolean isContains) throws Exception
    {
        String sql = "UPDATE ARTICLE SET index_id=index_id+1 WHERE pid=" + pid +
                " AND index_id>" + (isContains?"=":"") + indexId;
        DB.executeUpdate(sql);
    }
}
