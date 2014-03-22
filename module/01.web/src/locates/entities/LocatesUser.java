package locates.entities;

/**
 * 定位系统用户实体
 * User: Gxx
 * Time: 2013-10-27 12:16
 */
public class LocatesUser
{
    /**
     * IMEI用户名
     */
    String imei;
    /**
     * 密码
     */
    String pwd;
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
     * 是否在监听中 1是 0否
     */
    boolean isListening;
    /**
     * 定位监听ID
     */
    int listenId;

    /**
     * 注册用户时候用到
     * @param imei
     * @param pwd
     */
    public LocatesUser(String imei, String pwd) {
        this.imei = imei;
        this.pwd = pwd;
        this.isPenOpen = false;
        this.isListening = false;
    }

    /**
     * 查询数据库时候用到
     * @param imei
     * @param pwd
     * @param isPenOpen
     * @param penRadius
     * @param penCenterLat
     * @param penCenterLng
     * @param isListening
     * @param listenId
     */
    public LocatesUser(String imei, String pwd, boolean isPenOpen, double penRadius, double penCenterLat,
                       double penCenterLng, boolean isListening, int listenId) {
        this.imei = imei;
        this.pwd = pwd;
        this.isPenOpen = isPenOpen;
        this.penRadius = penRadius;
        this.penCenterLat = penCenterLat;
        this.penCenterLng = penCenterLng;
        this.isListening = isListening;
        this.listenId = listenId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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

    public int getListenId() {
        return listenId;
    }

    public void setListenId(int listenId) {
        this.listenId = listenId;
    }

    public boolean isListening() {
        return isListening;
    }

    public void setListening(boolean listening) {
        isListening = listening;
    }
}
