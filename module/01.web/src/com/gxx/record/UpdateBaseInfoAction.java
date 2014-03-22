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
public class UpdateBaseInfoAction extends ActionSupport implements ServletRequestAware
{
    public String sex;
    public String birthyear;
    public String birthmonth;
    public String birthday;
    public String email;
    public String errorMsg;
    public String message;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        User user = (User) request.getSession().getAttribute(BaseInterface.USER);
        if(null == user)
        {
            errorMsg = "请先登录!";
            return ERROR;
        }
        System.out.println("sex=[" + sex + "]" + ",birth=[" + birthyear+birthmonth+birthday + "]" + ",email=[" + email + "]");
        
        // 数据校验
        if(StringUtils.isBlank(sex))
        {
            errorMsg = "请选择性别!";
            return ERROR;
        }
        SexType sexType = SexType.MALE;
        if("0".equals(sex))
        {
            sexType = SexType.FEMALE;
        }
        if(StringUtils.isBlank(birthyear) || StringUtils.isBlank(birthmonth) || StringUtils.isBlank(birthday))
        {
            birthday = "";
            errorMsg = "请选择日期!";
            return ERROR;
        } else
        {
            birthday = birthyear+birthmonth+birthday;
        }
        email = StringUtils.trimToEmpty(email);
        if(StringUtils.isBlank(email))
        {
            errorMsg = "邮箱不能为空!";
            return ERROR;
        }
        if(BaseUtils.getStrByteLength(email) > 100)
        {
            errorMsg = "邮箱长度过长!";
            return ERROR;
        }

        // 修改用户基础资料
        DBUpdate.updateBaseInfo(user.getId(), sexType, birthday, email);

        // 更新缓存
        user.setSex(sexType.getSexTypeInt());
        user.setBirthday(birthday);
        user.setEmail(email);

        message = "修改用户基础资料完成!";
        return SUCCESS;
    }


    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getBirthyear()
    {
        return birthyear;
    }

    public void setBirthyear(String birthyear)
    {
        this.birthyear = birthyear;
    }

    public String getBirthmonth()
    {
        return birthmonth;
    }

    public void setBirthmonth(String birthmonth)
    {
        this.birthmonth = birthmonth;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
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

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
