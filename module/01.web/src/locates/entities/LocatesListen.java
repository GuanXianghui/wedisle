package locates.entities;

import com.gxx.record.utils.ServiceDataUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * 定位监听实体
 * User: Gxx
 * Time: 2013-10-27 12:16
 */
public class LocatesListen
{
    /**
     * 定位监听ID
     */
    int listenId;
    /**
     * IMEI用户名
     */
    String imei;
    /**
     * 是否在监听中 1开启 0关闭
     */
    boolean isListening;
    /**
     * 开始监听时间 yyyyMMddHHmmss
     */
    String startDateTime;
    /**
     * 结束监听时间 yyyyMMddHHmmss
     */
    String endDateTime;

    /**
     * 新增监听时候会用到
     * @param imei
     */
    public LocatesListen(String imei) {
        this.imei = imei;
        this.isListening = true;
        this.startDateTime = ServiceDataUtil.getDateTime(new Date());
    }

    /**
     * 查询数据库时候用到
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
