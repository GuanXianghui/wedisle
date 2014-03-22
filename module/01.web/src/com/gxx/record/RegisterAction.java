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
 * History:      2013-06-20 created by Gxx
 */
package com.gxx.record;

import com.gxx.record.dao.DBSelect;
import com.gxx.record.dao.DBUpdate;
import com.gxx.record.entities.User;
import com.gxx.record.enums.SexType;
import com.gxx.record.enums.UserState;
import com.gxx.record.enums.UserType;
import com.gxx.record.interfaces.BaseInterface;
import com.gxx.record.utils.IPAddressUtil;
import com.gxx.record.utils.PropertyUtil;
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
public class RegisterAction extends ActionSupport implements ServletRequestAware
{
    public String name;
    public String password;
    public String email;
    public String errorMsg;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        System.out.println("name=[" + name + "]" + ",password=[" + password + "]" + ",email=[" + email + "]");
        if(StringUtils.isBlank(name) || StringUtils.isBlank(password) || StringUtils.isBlank(email))
        {
            errorMsg = "姓名，密码与Email不能为空";
            return ERROR;
        }
        name = StringUtils.trimToEmpty(name);
        //password = StringUtils.trimToEmpty(password); password不管是否带空格
        email = StringUtils.trimToEmpty(email);
        try{
            User user = DBSelect.getUserByUserName(name);
            if(null != user)
            {
                errorMsg = "该用户名已被注册！";
                System.out.println("_____________>>errorMsg=" + errorMsg);
                return ERROR;
            }
            int score = 0;
            Date date = new Date();
            String registerDate = ServiceDataUtil.getDate(date);
            String registerTime = ServiceDataUtil.getTime(date);
            String registerIp = IPAddressUtil.getIPAddress(request);
            String defaultHeadPhoto = PropertyUtil.getInstance().getProperty(BaseInterface.BASE_URL) +
                    PropertyUtil.getInstance().getProperty(BaseInterface.DEFAULT_HEAD_PHOTO);
            user = new User(name, password, email, defaultHeadPhoto, UserType.NORMAL, UserState.NORMAL, SexType.MALE.getSexTypeInt(),
                    StringUtils.EMPTY, score, registerDate, registerTime, registerIp, registerDate, registerTime, registerIp);
            DBUpdate.insertUser(user);
        }catch (Exception e)
        {
            e.printStackTrace();
            errorMsg = "用户注册失败！";
            return ERROR;
        }
        User user = DBSelect.getUserByUserName(name);
        request.getSession().setAttribute(BaseInterface.USER, user);
        errorMsg = "注册成功！";
        return SUCCESS;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
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
