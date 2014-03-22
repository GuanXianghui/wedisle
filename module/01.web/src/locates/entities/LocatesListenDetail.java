package locates.entities;

/**
 * 定位监听详细实体
 * User: Gxx
 * Time: 2013-10-27 12:16
 */
public class LocatesListenDetail
{
    /**
     * 主键自增
     */
    int id;
    /**
     * 定位监听ID
     */
    int listenId;
    /**
     * 维度
     */
    double lat;
    /**
     * 经度
     */
    double lng;
    /**
     * 时间点
     */
    String dateTime;
    /**
     * 是否开启电子围栏 1开启 0关闭
     */
    boolean isPenOpen;
    /**
     * 电子围栏半径 米位单位
     */
    double penRadius;
    /**
     * 电子围栏中心维度
     */
    double penCenterLat;
    /**
     * 电子围栏中心经度
     */
    double penCenterLng;

    /**
     * 新增定位监听详细实体时候会用到
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
     * 查询数据库时候会用到
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
