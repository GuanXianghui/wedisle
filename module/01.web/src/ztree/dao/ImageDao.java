package ztree.dao;

import com.gxx.record.dao.DB;
import ztree.entities.Image;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * ͼƬ�������
 */
public class ImageDao
{

    /**
     * �� id�����ֶε����ֵ
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
     * ����ͼƬ
     * @param image
     * @throws Exception
     */
    public static void addImage(Image image) throws Exception
    {
        String sql = "insert into image(id, path)values(" +
                "null, '" + image.getPath() + "')";
        DB.executeUpdate(sql);

        // ���õ�ǰ����ID
        image.setId(getMaxId());
    }

    /**
     * ����ID��Image
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
                throw new RuntimeException("���ݿ�������������ԣ�");
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
