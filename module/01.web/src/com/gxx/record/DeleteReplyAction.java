/**
 * File Name:    DeleteReplyAction.java
 *
 * File Desc:    ɾ������Action
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
 * History:      2013-07-01 created by Gxx
 */
package com.gxx.record;

import com.gxx.record.dao.DBSelect;
import com.gxx.record.dao.DBUpdate;
import com.gxx.record.entities.Reply;
import com.gxx.record.entities.User;
import com.gxx.record.enums.UserType;
import com.gxx.record.interfaces.BaseInterface;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;

/**
 * ɾ������Action
 * @author Gxx
 * @version 1.0
 */
public class DeleteReplyAction extends ActionSupport implements ServletRequestAware
{
    public String rid;
    public String tid;
    public String errorMsg;
    public String message;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        User user = (User) request.getSession().getAttribute(BaseInterface.USER);
        if(null == user)
        {
            errorMsg = "���ȵ�¼!";
            return ERROR;
        }
        System.out.println("tid=[" + tid + "]");

        Reply reply = null;
        if(StringUtils.isNotBlank(rid))
        {
            reply = DBSelect.getReplyById(Integer.parseInt(rid));
        }

        if(StringUtils.isBlank(rid) || reply == null)
        {
            errorMsg = "���Ĳ�������������!";
            return ERROR;
        }
        
        if(reply.getUserId() != user.getId() && user.getUserType().getUserTypeInt() != UserType.SUPER_ADMIN.getUserTypeInt())
        {
            errorMsg = "����Ȩɾ�����˵�����!";
            return ERROR;
        }

        // ɾ������
        DBUpdate.deleteReply(reply.getId());
        message = "����ɾ���ɹ���";

        tid = "" + reply.getTopicId();

        return SUCCESS;
    }

    public String getTid()
    {
        return tid;
    }

    public void setTid(String tid)
    {
        this.tid = tid;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public String getRid()
    {
        return rid;
    }

    public void setRid(String rid)
    {
        this.rid = rid;
    }


    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
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
