/**
 * File Name:    LoginAction.java
 *
 * File Desc:    �û���¼Action
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
 * �û���¼Action
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
                errorMsg = "���û��������ڣ�";
                System.out.println("_____________>>errorMsg=" + errorMsg);
                return ERROR;
            }
            if(!StringUtils.equals(password, user.getPassword()))
            {
                errorMsg = "�������";
                System.out.println("_____________>>errorMsg=" + errorMsg);
                return ERROR;
            }
            
            // �����û���¼����
            Date date = new Date();
            String visitDate = ServiceDataUtil.getDate(date);
            String visitTime = ServiceDataUtil.getTime(date);
            String visitIp = IPAddressUtil.getIPAddress(request);
            user.setVisitDate(visitDate);
            user.setVisitTime(visitTime);
            user.setVisitIp(visitIp);
            DBUpdate.updateLogin(user);
            
            // ��¼�û���¼������־
            ActionType actionType = ActionType.LOGIN;
            int userId = user.getId();
            ActionLog actionLog = new ActionLog(actionType, userId, visitDate, visitTime, 0, 0);
            DBUpdate.addActionLog(actionLog);

            /**
             * �������� �ӻ���
             * �ӻ��ֵĶ�������¼(һ��һ�� +2��) ����(һ�� +2��) ����(һ�� +1�� ���� +2��)
             */
            BaseUtils.addScore(actionLog);
            request.getSession().setAttribute(BaseInterface.USER, user);
        }catch (Exception e)
        {
            e.printStackTrace();
            errorMsg = "�û���¼ʧ�ܣ�";
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
