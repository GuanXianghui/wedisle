/**
 * File Name:    RegisterAction.java
 *
 * File Desc:    用户注册Action
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
 * History:      2013-10-13 created by Gxx
 */
package com.gxx.record.wedisle;

import com.gxx.record.dao.DBSelect;
import com.gxx.record.dao.DBUpdate;
import com.gxx.record.entities.wedisle.WedisleUser;
import com.gxx.record.enums.wedisle.UserState;
import com.gxx.record.enums.wedisle.UserType;
import com.gxx.record.interfaces.BaseInterface;
import com.gxx.record.interfaces.WedisleBaseInterface;
import com.gxx.record.utils.IPAddressUtil;
import com.gxx.record.utils.ServiceDataUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 用户注册Action
 * @author Gxx
 * @version 1.0
 */
public class SignInAction extends ActionSupport implements ServletRequestAware
{
    public String userName;//登录名与密码一致
    public String password;
    public String password2;
    public String messageLoc;
    public String message;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        System.out.println("userName=[" + userName + "]" + ",password=[" + password + "]" + ",password2=[" + password2 + "]");
        if(StringUtils.isBlank(userName))
        {
            message = "姓名不能为空";
            messageLoc = "1";
            System.out.println(message);
            return ERROR;
        }
        if(StringUtils.isBlank(password))
        {
            message = "密码不能为空";
            messageLoc = "2";
            System.out.println(message);
            return ERROR;
        }
        if(StringUtils.isBlank(password2))
        {
            message = "密码不能为空";
            messageLoc = "3";
            System.out.println(message);
            return ERROR;
        }
        if(!StringUtils.equals(password, password2))
        {
            message = "两次密码不一致";
            messageLoc = "3";
            System.out.println(message);
            return ERROR;
        }
        userName = StringUtils.trimToEmpty(userName);
        try{
            WedisleUser user = DBSelect.getWedisleUserByUserName(userName);
            if(null != user)
            {
                message = "该邮箱已被注册！";
                messageLoc = "1";
                System.out.println(message);
                return ERROR;
            }
            Date date = new Date();
            String registerDate = ServiceDataUtil.getDate(date);
            String registerTime = ServiceDataUtil.getTime(date);
            String registerIp = IPAddressUtil.getIPAddress(request);
            String friendsType = WedisleBaseInterface.DEFAULT_FRIENDS_TYPE;

            user = new WedisleUser(userName, password, UserType.NORMAL.getUserTypeInt(), userName,
                    UserState.NORMAL.getUserStateInt(), WedisleBaseInterface.DEFAULT_ERROR_PWD_NUM,
                    friendsType, registerDate, registerTime, registerIp);
            DBUpdate.insertWedisleUser(user);
        }catch (Exception e)
        {
            e.printStackTrace();
            message = "用户注册失败！";
            messageLoc = "1";
            System.out.println(message);
            return ERROR;
        }
        WedisleUser user = DBSelect.getWedisleUserByUserName(userName);
        request.getSession().setAttribute(BaseInterface.WEDISLE_USER, user);
        message = "注册成功！";
        System.out.println(message);
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

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getMessageLoc() {
        return messageLoc;
    }

    public void setMessageLoc(String messageLoc) {
        this.messageLoc = messageLoc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
}
