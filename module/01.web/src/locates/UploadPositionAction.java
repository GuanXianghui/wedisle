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
 * 上传定位
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
        System.out.println("上传定位用户~userName=[" + userName + "],password=[" + password + "]"
                + ",lat=[" + lat + "]" + ",lng=[" + lng + "]");

        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password) || StringUtils.isBlank(lat)
                || StringUtils.isBlank(lng))
        {
            message = "用户名，密码，维度，经度不能为空!";
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
            message = "维度，经度格式有误!";
            return ERROR;
        }

        LocatesUser user = LocatesUserDao.getUserByImei(userName);
        if(null == user)
        {
            message = "您未注册!";
            return ERROR;
        }

        if(!StringUtils.equals(user.getPwd(), password))
        {
            message = "您的密码有误!";
            return ERROR;
        }

        if(!user.isListening())
        {
            message = "未开启轨迹监听!";
            return ERROR;
        }

        String dateTime = ServiceDataUtil.getDateTime(new Date());
        LocatesListenDetail detail = new LocatesListenDetail(user.getListenId(), latDouble, lngDouble, dateTime, user.isPenOpen(),
                user.getPenRadius(), user.getPenCenterLat(), user.getPenCenterLng());
        LocatesListenDetailDao.createNewListenDetail(detail);

        message = "上传定位成功!";
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
