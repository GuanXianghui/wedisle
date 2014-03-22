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
 * 文章表操作类
 */
public class ArticleDao
{
    /**
     * 查当前目录下有多少元素
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
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next())
            {
                int count = rs.getInt("count");
                return count;
            }
            throw new RuntimeException("数据库操作出错，请重试！");
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 查当前目录下最大的index_id
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
                throw new RuntimeException("数据库操作出错，请重试！");
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
            throw new RuntimeException("数据库操作出错，请重试！");
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 查 id自增字段的最大值
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
                throw new RuntimeException("数据库操作出错，请重试！");
            }
            while (rs.next())
            {
                int mid = rs.getInt("mid");
                return mid;
            }
            throw new RuntimeException("数据库操作出错，请重试！");
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * 新增节点
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

        // 设置当前最大的ID
        article.setId(getMaxId());
    }

    /**
     * 查询所有Article实体放到List里
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
                throw new RuntimeException("数据库操作出错，请重试！");
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
     * 递归查询 Article下的所有Article
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
                throw new RuntimeException("数据库操作出错，请重试！");
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
     * 根据ID查Article
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
                throw new RuntimeException("数据库操作出错，请重试！");
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
     * 修改Article对象
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
     * targetArticle同一级别 包括/不包括targetArticle 所有index_id都+1
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
