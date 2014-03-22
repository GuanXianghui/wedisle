/**
 * File Name:    AddRelaFriendAction.java
 *
 * File Desc:    新增亲友簿
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
 * History:      2013-11-2 created by Gxx
 */
package com.gxx.record.wedisle;

import com.gxx.record.dao.wedisle.WedisleRelaFriendDao;
import com.gxx.record.entities.wedisle.WedisleRelaFriend;
import com.gxx.record.entities.wedisle.WedisleUser;
import com.gxx.record.interfaces.BaseInterface;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;

/**
 * 新增亲友簿
 * @author Gxx
 * @version 1.0
 */
public class AddRelaFriendAction extends ActionSupport implements ServletRequestAware
{
    public String name;//亲友名字
    public String num;//人数
    public String errorMsg;
    public String message;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        System.out.println("name=[" + name + "]" + ",num=[" + num + "]");
        WedisleUser wedisleUser = (WedisleUser)request.getSession().getAttribute(BaseInterface.WEDISLE_USER);
        if(null == wedisleUser)
        {
            errorMsg = "请先做登录";
            System.out.println(errorMsg);
            return ERROR;
        }
        if(StringUtils.isBlank(name) || StringUtils.isBlank(num))
        {
            errorMsg = "亲友名字与人数不能为空";
            System.out.println(errorMsg);
            return ERROR;
        }
        if(name.indexOf(",") > -1)
        {
            errorMsg = "亲友名字中不能含有逗号";
            System.out.println(errorMsg);
            return ERROR;
        }
        try
        {
            Integer.parseInt(num);
        } catch (Exception e)
        {
            errorMsg = "人数格式有误";
            System.out.println(errorMsg);
            return ERROR;
        }
        // 判相同用户，相同名字是否存在
        if(WedisleRelaFriendDao.isExistSameUserIdAndName(wedisleUser.getId(), name))
        {
            errorMsg = "已经有相同名字的亲戚存在";
            System.out.println(errorMsg);
            return ERROR;
        }
        WedisleRelaFriend friend = null;//new WedisleRelaFriend(wedisleUser.getId(), name, Integer.parseInt(num)); todo
        WedisleRelaFriendDao.insertRelaFriend(friend);

        message = "新增亲友簿成功！";
        System.out.println(message);
        return SUCCESS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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
