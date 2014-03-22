package locates.dao;

import com.gxx.record.dao.DB;
import locates.entities.LocatesListenDetail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ��λ������ϸʵ�������
 * User: Gxx
 * Time: 2013-10-27 17:17
 */
public class LocatesListenDetailDao
{
    /**
     * �����µĶ�λ������ϸ
     * @param detail
     * @throws Exception
     */
    public static void createNewListenDetail(LocatesListenDetail detail) throws Exception
    {
        String sql = "insert into locates_listen_detail(id, listen_id, lat, lng, date_time, is_pen_open," +
                "pen_radius, pen_center_lat, pen_center_lng)values(" +
                "null, " +
                detail.getListenId() + ", " +
                detail.getLat() + ", " +
                detail.getLng() + ", " +
                "'" + detail.getDateTime() +  "', " +
                (detail.isPenOpen()?1:0) + ", " +
                detail.getPenRadius() + ", " +
                detail.getPenCenterLat() + ", " +
                detail.getPenCenterLng() +
                ")";
        DB.executeUpdate(sql);

        // ��������ID
        detail.setId(getMaxIdByListenId(detail.getListenId()));
    }

    /**
     * ���ݶ�λ����ID�鿴���µ���������ID �����µĶ�λ������ϸ �����
     * @return
     * @throws Exception
     */
    public static int getMaxIdByListenId(int listenId) throws Exception
    {
        String sql = "SELECT MAX(id) max_id FROM locates_listen_detail WHERE listen_id=" + listenId;
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
                return rs.getInt("max_id");
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
     * ���ݶ�λ����ID�鿴���еĶ�λ������ϸ �ۿ�¼��ʱ��/����ʱ�� �����
     * @return
     * @throws Exception
     */
    public static List<LocatesListenDetail> queryDetailsByListenId(int listenId) throws Exception
    {
        List<LocatesListenDetail> results = new ArrayList<LocatesListenDetail>();
        String sql = "SELECT id, lat, lng, date_time, is_pen_open, pen_radius, pen_center_lat," +
                "pen_center_lng FROM locates_listen_detail WHERE listen_id=" + listenId + " ORDER BY ID ASC";
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
                double lat = rs.getDouble("lat");
                double lng = rs.getDouble("lng");
                String dateTime = rs.getString("date_time");
                boolean isPenOpen = 1==rs.getInt("is_pen_open");
                double penRadius = rs.getDouble("pen_radius");
                double penCenterLat = rs.getDouble("pen_center_lat");
                double penCenterLng = rs.getDouble("pen_center_lng");
                LocatesListenDetail detail = new LocatesListenDetail(id, listenId, lat, lng, dateTime, isPenOpen,
                penRadius, penCenterLat, penCenterLng);
                results.add(detail);
            }
            return results;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }
}
