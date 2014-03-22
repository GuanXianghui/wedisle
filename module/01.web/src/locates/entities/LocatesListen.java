package locates.entities;

import com.gxx.record.utils.ServiceDataUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * ��λ����ʵ��
 * User: Gxx
 * Time: 2013-10-27 12:16
 */
public class LocatesListen
{
    /**
     * ��λ����ID
     */
    int listenId;
    /**
     * IMEI�û���
     */
    String imei;
    /**
     * �Ƿ��ڼ����� 1���� 0�ر�
     */
    boolean isListening;
    /**
     * ��ʼ����ʱ�� yyyyMMddHHmmss
     */
    String startDateTime;
    /**
     * ��������ʱ�� yyyyMMddHHmmss
     */
    String endDateTime;

    /**
     * ��������ʱ����õ�
     * @param imei
     */
    public LocatesListen(String imei) {
        this.imei = imei;
        this.isListening = true;
        this.startDateTime = ServiceDataUtil.getDateTime(new Date());
    }

    /**
     * ��ѯ���ݿ�ʱ���õ�
     * @param listenId
     * @param imei
     * @param isListening
     * @param startDateTime
     * @param endDateTime
     */
    public LocatesListen(int listenId, String imei, boolean isListening, String startDateTime, String endDateTime) {
        this.listenId = listenId;
        this.imei = imei;
        this.isListening = isListening;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public int getListenId() {
        return listenId;
    }

    public void setListenId(int listenId) {
        this.listenId = listenId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public boolean isListening() {
        return isListening;
    }

    public void setListening(boolean listening) {
        isListening = listening;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }
}
