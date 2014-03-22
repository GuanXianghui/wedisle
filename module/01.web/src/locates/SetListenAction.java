package locates;

import com.gxx.record.utils.ServiceDataUtil;
import com.opensymphony.xwork2.ActionSupport;
import locates.dao.LocatesListenDao;
import locates.dao.LocatesUserDao;
import locates.entities.LocatesListen;
import locates.entities.LocatesUser;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * 轨迹监听开关设置
 * User: Gxx
 * Time: 2013-10-27 12:14
 */
public class SetListenAction extends ActionSupport
{
    String userName;
    String password;
    String flag;
    String message;


    public String execute() throws Exception
    {
        System.out.println("轨迹监听开关设置用户~userName=[" + userName + "],password=[" + password + "]"
                + ",flag=[" + flag + "]");

        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password))
        {
            message = "用户名，密码不能为空!";
            return ERROR;
        }

        if(!"on".equalsIgnoreCase(flag) && !"off".equalsIgnoreCase(flag))
        {
            message = "开关标示不合法!";
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

        if("on".equalsIgnoreCase(flag) && user.isListening())
        {
            message = "轨迹监听已经开启!";
            return ERROR;
        }

        if("off".equalsIgnoreCase(flag) && !user.isListening())
        {
            message = "轨迹监听已经关闭!";
            return ERROR;
        }

        // 开启轨迹监听
        if("on".equalsIgnoreCase(flag))
        {
            LocatesListen listen = new LocatesListen(user.getImei());
            LocatesListenDao.createNewListen(listen);

            user.setListening(true);
            user.setListenId(listen.getListenId());
            LocatesUserDao.updateUser(user);

            message = "开启轨迹监听成功!";
        } else // 关闭轨迹监听
        {
            LocatesListen listen = LocatesListenDao.getListenById(user.getListenId());
            listen.setListening(false);
            listen.setEndDateTime(ServiceDataUtil.getDateTime(new Date()));
            LocatesListenDao.updateListen(listen);

            user.setListening(false);
            LocatesUserDao.updateUser(user);

            message = "关闭轨迹监听成功!";
        }

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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
