/**
 * File Name:    LoginAction.java
 *
 * File Desc:    用户登录Action
 *
 * Product AB:   PAYGATE_1_0_0
 *
 * Product Name: PAYGATE
 *
 * Module Name:  01.core
 *
 * Module AB:    01.core
 *
 * Author:       Gxx
 *
 * History:      2013-06-20 created by Gxx
 */
package com.gxx.record;

import com.gxx.record.dao.DBSelect;
import com.gxx.record.dao.DBUpdate;
import com.gxx.record.entities.ActionLog;
import com.gxx.record.entities.User;
import com.gxx.record.enums.ActionType;
import com.gxx.record.interfaces.BaseInterface;
import com.gxx.record.utils.BaseUtils;
import com.gxx.record.utils.IPAddressUtil;
import com.gxx.record.utils.ServiceDataUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 用户登录Action
 * @author Gxx
 * @version 1.0
 */
public class LoginAction extends ActionSupport implements ServletRequestAware
{
    public String username;
    public String password;
    public String errorMsg;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        System.out.println("username=[" + username + "]" + ",password=[" + password + "]");
        try{
            User user = DBSelect.getUserByUserName(username);
            if(null == user)
            {
                errorMsg = "该用户名不存在！";
                System.out.println("_____________>>errorMsg=" + errorMsg);
                return ERROR;
            }
            if(!StringUtils.equals(password, user.getPassword()))
            {
                errorMsg = "密码错误！";
                System.out.println("_____________>>errorMsg=" + errorMsg);
                return ERROR;
            }
            
            // 更新用户登录参数
            Date date = new Date();
            String visitDate = ServiceDataUtil.getDate(date);
            String visitTime = ServiceDataUtil.getTime(date);
            String visitIp = IPAddressUtil.getIPAddress(request);
            user.setVisitDate(visitDate);
            user.setVisitTime(visitTime);
            user.setVisitIp(visitIp);
            DBUpdate.updateLogin(user);
            
            // 记录用户登录动作日志
            ActionType actionType = ActionType.LOGIN;
            int userId = user.getId();
            ActionLog actionLog = new ActionLog(actionType, userId, visitDate, visitTime, 0, 0);
            DBUpdate.addActionLog(actionLog);

            /**
             * 动作发生 加积分
             * 加积分的动作：登录(一天一次 +2分) 发帖(一次 +2分) 回帖(一次 +1分 贴主 +2分)
             */
            BaseUtils.addScore(actionLog);
            request.getSession().setAttribute(BaseInterface.USER, user);
        }catch (Exception e)
        {
            e.printStackTrace();
            errorMsg = "用户登录失败！";
            return ERROR;
        }
        return SUCCESS;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }


    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
}
