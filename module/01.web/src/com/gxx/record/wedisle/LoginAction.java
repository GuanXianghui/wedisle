/**
 * File Name:    LoginAction.java
 *
 * File Desc:    �û���½Action
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
 * �û���½Action
 * @author Gxx
 * @version 1.0
 */
public class LoginAction extends ActionSupport implements ServletRequestAware
{
    public String userName;//��¼��������һ��
    public String password;
    public String messageLoc;
    public String message;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        System.out.println("userName=[" + userName + "]" + ",password=[" + password + "]");
        if(StringUtils.isBlank(userName))
        {
            message = "��������Ϊ��";
            messageLoc = "1";
            System.out.println(message);
            return ERROR;
        }
        if(StringUtils.isBlank(password))
        {
            message = "���벻��Ϊ��";
            messageLoc = "2";
            System.out.println(message);
            return ERROR;
        }
        try
        {
            WedisleUser user = DBSelect.getWedisleUserByUserName(userName);
            if(null == user)
            {
                message = "�����ڸ��û�";
                messageLoc = "1";
                System.out.println(message);
                return ERROR;
            }

            // ���û�״̬ todo
            if(!StringUtils.equals(password, user.getPassword()))
            {
                message = "���������������";
                messageLoc = "2";
                // ���¿�� �������+1 �����涨���� �����û�
                System.out.println(message);
                return ERROR;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            message = "�û���¼ʧ�ܣ�";
            messageLoc = "1";
            System.out.println(message);
            return ERROR;
        }

        WedisleUser user = DBSelect.getWedisleUserByUserName(userName);
        request.getSession().setAttribute(BaseInterface.WEDISLE_USER, user);
        message = "��¼�ɹ���";
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageLoc() {
        return messageLoc;
    }

    public void setMessageLoc(String messageLoc) {
        this.messageLoc = messageLoc;
    }


    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
}
