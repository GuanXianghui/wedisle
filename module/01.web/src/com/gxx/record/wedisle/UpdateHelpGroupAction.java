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

import com.gxx.record.dao.wedisle.WedisleRelaFriendDao;
import com.gxx.record.entities.wedisle.WedisleRelaFriend;
import com.gxx.record.entities.wedisle.WedisleUser;
import com.gxx.record.interfaces.BaseInterface;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;

/**
 * �޸İ����/������Ա
 * @author Gxx
 * @version 1.0
 */
public class UpdateHelpGroupAction extends ActionSupport implements ServletRequestAware
{
    public String names;
    public String updateType;
    public String helpGroup;
    public String message;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        System.out.println("names=[" + names + "]," + "helpGroup=[" + helpGroup + "]" + ",updateType=[" + updateType + "]");
        WedisleUser wedisleUser = (WedisleUser)request.getSession().getAttribute(BaseInterface.WEDISLE_USER);
        if(null == wedisleUser)
        {
            message = "��������¼";
            System.out.println(message);
            return ERROR;
        }

        if(StringUtils.isBlank(names))
        {
            message = "��û��ѡ������Ŷ����";
            System.out.println(message);
            return ERROR;
        }

        if(StringUtils.isBlank(updateType) || (!"help_group".equals(updateType) && !"worker".equals(updateType)))
        {
            message = "�����ύ��ʽ����Ŷ����";
            System.out.println(message);
            return ERROR;
        }

        if(StringUtils.isBlank(helpGroup))
        {
            message = "��û���������Ž�ɫŶ����";
            System.out.println(message);
            return ERROR;
        }

        String[] nameArray = names.split(",");
        for(int i=0;i<nameArray.length;i++){
            WedisleRelaFriend friend = WedisleRelaFriendDao.getRelaFriend(wedisleUser.getId(), nameArray[i]);
            if("help_group".equals(updateType)){
                friend.setHelpGroup(helpGroup);
                message = "�޸İ���ųɹ���";
            }
            if("worker".equals(updateType)){
                friend.setWorker(helpGroup);
                message = "�޸Ĺ�����Ա�ɹ���";
            }
            WedisleRelaFriendDao.updateRelaFriend(friend);
        }

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

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getNames() {

        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getHelpGroup() {
        return helpGroup;
    }

    public void setHelpGroup(String helpGroup) {
        this.helpGroup = helpGroup;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
