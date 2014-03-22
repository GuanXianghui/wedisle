package ztree.dao;

import com.gxx.record.dao.DB;
import ztree.entities.Image;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 图片表操作类
 */
public class ImageDao
{

    /**
     * 查 id自增字段的最大值
     * @return
     * @throws Exception
     */
    public static int getMaxId() throws Exception
    {
        String sql = "SELECT MAX(id) mid FROM IMAGE";
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
     * 新增图片
     * @param image
     * @throws Exception
     */
    public static void addImage(Image image) throws Exception
    {
        String sql = "insert into image(id, path)values(" +
                "null, '" + image.getPath() + "')";
        DB.executeUpdate(sql);

        // 设置当前最大的ID
        image.setId(getMaxId());
    }

    /**
     * 根据ID查Image
     * @param id
     * @return
     */
    public static Image getImageById(int id) throws Exception
    {
        Image image = null;
        String sql = "SELECT id, path FROM IMAGE WHERE id=" + id;
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
                image = new Image();
                image.setId(rs.getInt("id"));
                image.setPath(rs.getString("path"));
            }
            return image;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }
}
