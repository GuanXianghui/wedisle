package locates;

import com.gxx.record.utils.ServiceDataUtil;
import com.opensymphony.xwork2.ActionSupport;
import locates.dao.LocatesListenDetailDao;
import locates.dao.LocatesUserDao;
import locates.entities.LocatesListenDetail;
import locates.entities.LocatesUser;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * �ϴ���λ
 * User: Gxx
 * Time: 2013-10-27 12:14
 */
public class UploadPositionAction extends ActionSupport
{
    String userName;
    String password;
    String lat;
    String lng;
    String message;


    public String execute() throws Exception
    {
        System.out.println("�ϴ���λ�û�~userName=[" + userName + "],password=[" + password + "]"
                + ",lat=[" + lat + "]" + ",lng=[" + lng + "]");

        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password) || StringUtils.isBlank(lat)
                || StringUtils.isBlank(lng))
        {
            message = "�û��������룬ά�ȣ����Ȳ���Ϊ��!";
            return ERROR;
        }

        double latDouble;
        double lngDouble;
        try
        {
            latDouble = Double.parseDouble(lat);
            lngDouble = Double.parseDouble(lng);
        } catch (Exception e)
        {
            message = "ά�ȣ����ȸ�ʽ����!";
            return ERROR;
        }

        LocatesUser user = LocatesUserDao.getUserByImei(userName);
        if(null == user)
        {
            message = "��δע��!";
            return ERROR;
        }

        if(!StringUtils.equals(user.getPwd(), password))
        {
            message = "������������!";
            return ERROR;
        }

        if(!user.isListening())
        {
            message = "δ�����켣����!";
            return ERROR;
        }

        String dateTime = ServiceDataUtil.getDateTime(new Date());
        LocatesListenDetail detail = new LocatesListenDetail(user.getListenId(), latDouble, lngDouble, dateTime, user.isPenOpen(),
                user.getPenRadius(), user.getPenCenterLat(), user.getPenCenterLng());
        LocatesListenDetailDao.createNewListenDetail(detail);

        message = "�ϴ���λ�ɹ�!";
        return SUCCESS;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
