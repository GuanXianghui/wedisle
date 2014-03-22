package locates;

import com.opensymphony.xwork2.ActionSupport;
import locates.dao.LocatesUserDao;
import locates.entities.LocatesUser;
import org.apache.commons.lang.StringUtils;

/**
 * 设置电子围栏
 * User: Gxx
 * Time: 2013-10-27 12:14
 */
public class SetPenParamAction extends ActionSupport
{
    String userName;
    String password;
    String penRadius;
    String penCenterLat;
    String penCenterLng;
    String message;


    public String execute() throws Exception
    {
        System.out.println("设置电子围栏用户~userName=[" + userName + "],password=[" + password + "],"
                + "penRadius=[" + penRadius + "]," + "penCenterLat=[" + penCenterLat + "],"
                + "penCenterLng=[" + penCenterLng + "]");

        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password) || StringUtils.isBlank(penRadius)
                || StringUtils.isBlank(penCenterLat) || StringUtils.isBlank(penCenterLng))
        {
            message = "用户名，密码，半径，维度，经度不能为空!";
            return ERROR;
        }

        double penRadiusDouble;
        double penCenterLatDouble;
        double penCenterLngDouble;
        try
        {
            penRadiusDouble = Double.parseDouble(penRadius);
            penCenterLatDouble = Double.parseDouble(penCenterLat);
            penCenterLngDouble = Double.parseDouble(penCenterLng);
        } catch (Exception e)
        {
            message = "半径，维度，经度格式有误!";
            return ERROR;
        }

        if(penRadiusDouble < 500 || penRadiusDouble > 1000)
        {
            message = "半径必须设置在[500,1000]范围内!";
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

        user.setPenOpen(true);
        user.setPenRadius(penRadiusDouble);
        user.setPenCenterLat(penCenterLatDouble);
        user.setPenCenterLng(penCenterLngDouble);
        LocatesUserDao.updateUser(user);
        message = "电子围栏设置成功!";
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPenRadius() {
        return penRadius;
    }

    public void setPenRadius(String penRadius) {
        this.penRadius = penRadius;
    }

    public String getPenCenterLat() {
        return penCenterLat;
    }

    public void setPenCenterLat(String penCenterLat) {
        this.penCenterLat = penCenterLat;
    }

    public String getPenCenterLng() {
        return penCenterLng;
    }

    public void setPenCenterLng(String penCenterLng) {
        this.penCenterLng = penCenterLng;
    }
}
