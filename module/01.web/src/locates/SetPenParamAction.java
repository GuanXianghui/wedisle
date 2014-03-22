package locates;

import com.opensymphony.xwork2.ActionSupport;
import locates.dao.LocatesUserDao;
import locates.entities.LocatesUser;
import org.apache.commons.lang.StringUtils;

/**
 * ���õ���Χ��
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
        System.out.println("���õ���Χ���û�~userName=[" + userName + "],password=[" + password + "],"
                + "penRadius=[" + penRadius + "]," + "penCenterLat=[" + penCenterLat + "],"
                + "penCenterLng=[" + penCenterLng + "]");

        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password) || StringUtils.isBlank(penRadius)
                || StringUtils.isBlank(penCenterLat) || StringUtils.isBlank(penCenterLng))
        {
            message = "�û��������룬�뾶��ά�ȣ����Ȳ���Ϊ��!";
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
            message = "�뾶��ά�ȣ����ȸ�ʽ����!";
            return ERROR;
        }

        if(penRadiusDouble < 500 || penRadiusDouble > 1000)
        {
            message = "�뾶����������[500,1000]��Χ��!";
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

        user.setPenOpen(true);
        user.setPenRadius(penRadiusDouble);
        user.setPenCenterLat(penCenterLatDouble);
        user.setPenCenterLng(penCenterLngDouble);
        LocatesUserDao.updateUser(user);
        message = "����Χ�����óɹ�!";
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
