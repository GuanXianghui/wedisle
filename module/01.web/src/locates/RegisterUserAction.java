package locates;

import com.opensymphony.xwork2.ActionSupport;
import locates.dao.LocatesUserDao;
import locates.entities.LocatesUser;
import org.apache.commons.lang.StringUtils;

/**
 * 注册用户
 * User: Gxx
 * Time: 2013-10-27 12:14
 */
public class RegisterUserAction extends ActionSupport
{
    String userName;
    String password;
    String message;


    public String execute() throws Exception
    {
        System.out.println("注册用户~userName=[" + userName + "],password=[" + password + "]");

        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password))
        {
            message = "用户名，密码不能为空!";
            return ERROR;
        }

        LocatesUser user = LocatesUserDao.getUserByImei(userName);
        if(null != user)
        {
            message = "您已注册过!";
            return ERROR;
        }

        user = new LocatesUser(userName, password);
        LocatesUserDao.registerUser(user);
        message = "注册成功!";
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
