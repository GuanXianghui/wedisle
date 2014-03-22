package locates;

import com.gxx.record.utils.ServiceDataUtil;
import com.opensymphony.xwork2.ActionSupport;
import locates.dao.LocatesListenDao;
import locates.dao.LocatesListenDetailDao;
import locates.dao.LocatesUserDao;
import locates.entities.LocatesListen;
import locates.entities.LocatesListenDetail;
import locates.entities.LocatesUser;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ����λ�ü�����ϸ
 * User: Gxx
 * Time: 2013-10-27 12:14
 */
public class LoadListenDetailsAction extends ActionSupport implements ServletResponseAware
{
    String imei;
    boolean isError;
    String errorMsg;
    String message;
    LocatesUser user;
    LocatesListen listen;
    List<LocatesListenDetail> detailList;
    HttpServletResponse response;


    public String execute() throws Exception
    {
        System.out.println("����λ�ü�����ϸ~imei=[" + imei + "]");

        if(StringUtils.isBlank(imei))
        {
            isError = true;
            errorMsg = "imei�û�������Ϊ��!";
            writeResult();
            return null;
        }

        user = LocatesUserDao.getUserByImei(imei);
        if(null == user)
        {
            isError = true;
            errorMsg = "���û�[" + imei + "]δע��!";
            writeResult();
            return null;
        }

        if(!user.isListening())
        {
            isError = true;
            errorMsg = "���û�[" + imei + "]�ѹرռ���!";
            writeResult();
            return null;
        }
        // ���ݶ�λ����ID��ѯ��λ����
        listen =  LocatesListenDao.getListenById(user.getListenId());
        // ���ݶ�λ����ID�鿴���еĶ�λ������ϸ �ۿ�¼��ʱ��/����ʱ�� �����
        detailList = LocatesListenDetailDao.queryDetailsByListenId(listen.getListenId());
        message = "����λ�ü�����ϸ�ɹ�!";
        writeResult();
        return null;
    }

    /**
     * ��д���
     */
    public void writeResult() throws Exception
    {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(getJson());
    }

    /**
     * ����Json����
     * @return
     */
    public String getJson()
    {
        String json;
        if(isError)
        {
            json = "{isError: " + isError + ", " +
                    "errorMsg: '" + StringUtils.trimToEmpty(errorMsg) + "'}";
        } else
        {
            boolean isPenOpen = user.isPenOpen();//�Ƿ�������Χ�� 1���� 0�ر�
            double penRadius = user.getPenRadius();//����Χ���뾶 ��λ��λ
            double penCenterLat = user.getPenCenterLat();//����Χ������ά��
            double penCenterLng = user.getPenCenterLng();//����Χ�����ľ���
            int listenId = user.getListenId();//��λ����ID

            String startDateTime = ServiceDataUtil.getCNDateTime(ServiceDataUtil.getDateTime
                    (listen.getStartDateTime()));//���μ�����ʼʱ��
            json = "{isError: " + isError + ", " +
                    "errorMsg: '" + StringUtils.trimToEmpty(errorMsg) + "', " +
                    "message: '" + message + "', " +
                    "imei: '" + imei + "', " +
                    "isPenOpen: " + isPenOpen + "," +
                    "penRadius: '" + (isPenOpen?penRadius:0) + "'," +
                    "penCenterLat: '" + (isPenOpen?penCenterLat:"-") + "'," +
                    "penCenterLng: '" + (isPenOpen?penCenterLng:"-") + "'," +
                    "listenId: '" + listenId + "'," +
                    "startDateTime: '" + startDateTime + "'," +
                    "lats: " + getDetailListLatsJson() + "," +
                    "lngs: " + getDetailListLngsJson() + "," +
                    "dateTimes: " + getDetailListDateTimesJson() +"}";
        }
        System.out.println(json);
        return json;
    }

    /**
     * ���춨λ��ϸά��Json����
     * @return
     */
    public String getDetailListLatsJson()
    {
        String json = "[";
        for(int i=0;i<detailList.size();i++)
        {
            if(i>0)
            {
                json += ",";
            }
            json += "'"
                    + detailList.get(i).getLat()
                    + "'";
        }
        json += "]";
        return json;
    }

    /**
     * ���춨λ��ϸ����Json����
     * @return
     */
    public String getDetailListLngsJson()
    {
        String json = "[";
        for(int i=0;i<detailList.size();i++)
        {
            if(i>0)
            {
                json += ",";
            }
            json += "'"
                    + detailList.get(i).getLng()
                    + "'";
        }
        json += "]";
        return json;
    }

    /**
     * ���춨λ��ϸά��Json����
     * @return
     */
    public String getDetailListDateTimesJson()
    {
        String json = "[";
        for(int i=0;i<detailList.size();i++)
        {
            if(i>0)
            {
                json += ",";
            }
            json += "'"
                    + ServiceDataUtil.getCNDateTime(ServiceDataUtil.getDateTime(detailList.get(i).getDateTime()))
                    + "'";
        }
        json += "]";
        return json;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public LocatesUser getUser() {
        return user;
    }

    public void setUser(LocatesUser user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocatesListen getListen() {
        return listen;
    }

    public void setListen(LocatesListen listen) {
        this.listen = listen;
    }

    public List<LocatesListenDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<LocatesListenDetail> detailList) {
        this.detailList = detailList;
    }

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
}
