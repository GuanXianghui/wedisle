package locates;

import com.opensymphony.xwork2.ActionSupport;
import locates.dao.LocatesUserDao;
import locates.entities.LocatesUser;
import org.apache.commons.lang.StringUtils;

/**
 * 关闭电子围栏
 * User: Gxx
 * Time: 2013-10-27 12:14
 */
public class SetPenStatusAction extends ActionSupport
{
    String userName;
    String password;
    String message;


    public String execute() throws Exception
    {
        System.out.println("关闭电子围栏用户~userName=[" + userName + "],password=[" + password + "]");

        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password))
        {
            message = "用户名，密码不能为空!";
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

        if(!user.isPenOpen())
        {
            message = "电子围栏已经关闭!";
            return ERROR;
        }

        user.setPenOpen(false);
        LocatesUserDao.updateUser(user);
        message = "电子围栏关闭成功!";
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
}
