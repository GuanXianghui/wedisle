package locates.dao;

import com.gxx.record.dao.DB;
import locates.entities.LocatesListen;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ��λ�����������
 * User: Gxx
 * Time: 2013-10-27 12:14
 */
public class LocatesListenDao
{
    /**
     * �����µĶ�λ����
     * @param listen
     * @throws Exception
     */
    public static void createNewListen(LocatesListen listen) throws Exception
    {
        String sql = "insert into locates_listen(listen_id, imei, is_listening, start_date_time, end_date_time)" +
                "values(" +
                "null, " +
                "'" + listen.getImei() +  "', " +
                (listen.isListening()?1:0) + ", " +
                listen.getStartDateTime() + ", " +
                listen.getEndDateTime() +
                ")";
        DB.executeUpdate(sql);

        // ���ö�λ����ID
        listen.setListenId(getMaxIdByImei(listen.getImei()));
    }

    /**
     * ����IMEI�鿴���µĶ�λ����ID �����µĶ�λ������ �����
     * @return
     * @throws Exception
     */
    public static int getMaxIdByImei(String imei) throws Exception
    {
        String sql = "SELECT MAX(listen_id) max_id FROM locates_listen WHERE imei='" + imei + "'";
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
     * ���ݶ�λ����ID��ѯ��λ����
     * @param listenId
     */
    public static LocatesListen getListenById(int listenId) throws Exception
    {
        String sql = "SELECT imei, is_listening, start_date_time, end_date_time " +
                "FROM locates_listen WHERE listen_id=" + listenId;
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
                String imei = rs.getString("imei");
                boolean isListening = 1==rs.getInt("is_listening");
                String startDateTime = rs.getString("start_date_time");
                String endDateTime = rs.getString("end_date_time");
                LocatesListen listen = new LocatesListen(listenId, imei, isListening, startDateTime, endDateTime);
                return listen;
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
     * ����IMEI��ѯ���ж�λ����¼��
     * @param imei
     */
    public static List<LocatesListen> queryNotListeningListensByImei(String imei) throws Exception
    {
        /**
         * �ڶ������� 1������ 0�������� -1������������ѯ
         */
        return queryListensByImei(imei, 0);
    }

    /**
     * ����IMEI��ѯ���ж�λ������Ϣ
     * @param imei
     * @param is_listening 1������ 0�������� -1������������ѯ
     */
    public static List<LocatesListen> queryListensByImei(String imei, int is_listening) throws Exception
    {
        List<LocatesListen> results = new ArrayList<LocatesListen>();
        String sql = "SELECT listen_id, imei, is_listening, start_date_time, end_date_time " +
                "FROM locates_listen WHERE imei='" + imei + "'";
        if(is_listening >= 0)
        {
            sql += " AND is_listening=" + is_listening;
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
                int listenId = rs.getInt("listen_id");
                boolean isListening = 1==rs.getInt("is_listening");
                String startDateTime = rs.getString("start_date_time");
                String endDateTime = rs.getString("end_date_time");
                LocatesListen listen = new LocatesListen(listenId, imei, isListening, startDateTime, endDateTime);
                results.add(listen);
            }
            return results;
        } finally
        {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }


    /**
     * ��λ�����޸� ֻ���޸����is_listening �� end_date_time
     * @param listen
     * @throws Exception
     */
    public static void updateListen(LocatesListen listen) throws Exception
    {
        String sql = "UPDATE locates_listen SET " +
                "is_listening=" + (listen.isListening()?1:0) + ", " +
                "end_date_time='" + listen.getEndDateTime() + "' " +
                "WHERE listen_id=" + listen.getListenId();
        DB.executeUpdate(sql);
    }
}
