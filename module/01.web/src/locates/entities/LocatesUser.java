package locates.entities;

/**
 * ��λϵͳ�û�ʵ��
 * User: Gxx
 * Time: 2013-10-27 12:16
 */
public class LocatesUser
{
    /**
     * IMEI�û���
     */
    String imei;
    /**
     * ����
     */
    String pwd;
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
     * �Ƿ��ڼ����� 1�� 0��
     */
    boolean isListening;
    /**
     * ��λ����ID
     */
    int listenId;

    /**
     * ע���û�ʱ���õ�
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
     * ��ѯ���ݿ�ʱ���õ�
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
