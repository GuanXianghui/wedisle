/**
 * File Name:    UpdateBaseInfoAction.java
 *
 * File Desc:    修改基础资料Action
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
 * History:      2013-06-30 created by Gxx
 */
package com.gxx.record;

import com.gxx.record.dao.DBUpdate;
import com.gxx.record.entities.User;
import com.gxx.record.enums.SexType;
import com.gxx.record.interfaces.BaseInterface;
import com.gxx.record.utils.BaseUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;

/**
 * 修改用户基础资料Action
 * @author Gxx
 * @version 1.0
 */
public class UpdatePasswordAction extends ActionSupport implements ServletRequestAware
{
    public String password;
    public String message;
    public String errorMsg;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        User user = (User) request.getSession().getAttribute(BaseInterface.USER);
        if(null == user)
        {
            errorMsg = "请先登录!";
            return ERROR;
        }
        System.out.println("password=[" + password + "]");

        if(StringUtils.isBlank(password))
        {
            errorMsg = "密码不能为空或者全由空格组成!";
            return ERROR;
        }

        // 数据校验
        if(BaseUtils.getStrByteLength(password) > 32)
        {
            errorMsg = "密码长度过长!";
            return ERROR;
        }

        // 修改用户密码
        DBUpdate.updatePassword(user.getId(), password);

        // 更新缓存
        user.setPassword(password);

        message = "修改用户密码完成！";
        return SUCCESS;
    }


    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
}
