/**
 * File Name:    AddRelaFriendAction.java
 *
 * File Desc:    �������Ѳ�
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

import com.gxx.record.dao.DBUpdate;
import com.gxx.record.dao.wedisle.WedisleRelaFriendDao;
import com.gxx.record.entities.wedisle.WedisleRelaFriend;
import com.gxx.record.entities.wedisle.WedisleUser;
import com.gxx.record.interfaces.BaseInterface;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;

/**
 * ɾ������
 * @author Gxx
 * @version 1.0
 */
public class DeleteRelaFriendAction extends ActionSupport implements ServletRequestAware
{
    public String names;
    public String message;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        System.out.println("names=[" + names + "]");
        WedisleUser wedisleUser = (WedisleUser)request.getSession().getAttribute(BaseInterface.WEDISLE_USER);
        if(null == wedisleUser)
        {
            message = "��������¼";
            System.out.println(message);
            return ERROR;
        }

        if(StringUtils.isBlank(names))
        {
            message = "��û��ѡ��Ҫɾ������������Ŷ����";
            System.out.println(message);
            return ERROR;
        }

        names = "'" + names.replaceAll(",","','") + "'";
        System.out.println("names=[" + names + "]");

        WedisleRelaFriendDao.deleteRelaFriends(wedisleUser.getId(), names);
        message = "ɾ�����ѳɹ���";
        System.out.println(message);
        return SUCCESS;
    }


    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNames() {

        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
