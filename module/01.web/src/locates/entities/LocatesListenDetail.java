package locates.entities;

/**
 * ��λ������ϸʵ��
 * User: Gxx
 * Time: 2013-10-27 12:16
 */
public class LocatesListenDetail
{
    /**
     * ��������
     */
    int id;
    /**
     * ��λ����ID
     */
    int listenId;
    /**
     * ά��
     */
    double lat;
    /**
     * ����
     */
    double lng;
    /**
     * ʱ���
     */
    String dateTime;
    /**
     * �Ƿ�������Χ�� 1���� 0�ر�
     */
    boolean isPenOpen;
    /**
     * ����Χ���뾶 ��λ��λ
     */
    double penRadius;
    /**
     * ����Χ������ά��
     */
    double penCenterLat;
    /**
     * ����Χ�����ľ���
     */
    double penCenterLng;

    /**
     * ������λ������ϸʵ��ʱ����õ�
     * @param listenId
     * @param lat
     * @param lng
     * @param dateTime
     * @param isPenOpen
     * @param penRadius
     * @param penCenterLat
     * @param penCenterLng
     */
    public LocatesListenDetail(int listenId, double lat, double lng, String dateTime, boolean isPenOpen,
                               double penRadius, double penCenterLat, double penCenterLng) {
        this.listenId = listenId;
        this.lat = lat;
        this.lng = lng;
        this.dateTime = dateTime;
        this.isPenOpen = isPenOpen;
        this.penRadius = penRadius;
        this.penCenterLat = penCenterLat;
        this.penCenterLng = penCenterLng;
    }

    /**
     * ��ѯ���ݿ�ʱ����õ�
     * @param id
     * @param listenId
     * @param lat
     * @param lng
     * @param dateTime
     * @param isPenOpen
     * @param penRadius
     * @param penCenterLat
     * @param penCenterLng
     */
    public LocatesListenDetail(int id, int listenId, double lat, double lng, String dateTime, boolean isPenOpen,
                               double penRadius, double penCenterLat, double penCenterLng) {
        this.id = id;
        this.listenId = listenId;
        this.lat = lat;
        this.lng = lng;
        this.dateTime = dateTime;
        this.isPenOpen = isPenOpen;
        this.penRadius = penRadius;
        this.penCenterLat = penCenterLat;
        this.penCenterLng = penCenterLng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListenId() {
        return listenId;
    }

    public void setListenId(int listenId) {
        this.listenId = listenId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isPenOpen() {
        return isPenOpen;
    }

    public void setPenOpen(boolean penOpen) {
        isPenOpen = penOpen;
    }

    public double getPenRadius() {
        return penRadius;
    }

    public void setPenRadius(double penRadius) {
        this.penRadius = penRadius;
    }

    public double getPenCenterLat() {
        return penCenterLat;
    }

    public void setPenCenterLat(double penCenterLat) {
        this.penCenterLat = penCenterLat;
    }

    public double getPenCenterLng() {
        return penCenterLng;
    }

    public void setPenCenterLng(double penCenterLng) {
        this.penCenterLng = penCenterLng;
    }
}
