/**
 * File Name:    LogOutAction.java
 *
 * File Desc:    用户登出Action
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

import com.gxx.record.dao.DBUpdate;
import com.gxx.record.entities.ActionLog;
import com.gxx.record.entities.User;
import com.gxx.record.enums.ActionType;
import com.gxx.record.interfaces.BaseInterface;
import com.gxx.record.utils.OnlineUtils;
import com.gxx.record.utils.ServiceDataUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 用户登出Action
 * @author Gxx
 * @version 1.0
 */
public class LogOutAction extends ActionSupport implements ServletRequestAware
{
    HttpServletRequest request;


    public String execute() throws Exception
    {
        User user = (User) request.getSession().getAttribute(BaseInterface.USER);
        if(null != user)
        {
            // 用户登出 并 记录用户登出动作日志
            OnlineUtils.getInstance().logOutUser(user.getId());

            // 清空session缓存
            request.getSession().setAttribute(BaseInterface.USER, user);
        }

        // session用户对象置空
        request.getSession().setAttribute(BaseInterface.USER, null);
        return SUCCESS;
    }


    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
}
