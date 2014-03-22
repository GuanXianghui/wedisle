/**
 * File Name:    DeleteTopicAction.java
 *
 * File Desc:    删除帖子Action
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
import com.gxx.record.entities.Record;
import com.gxx.record.entities.Topic;
import com.gxx.record.entities.User;
import com.gxx.record.enums.UserType;
import com.gxx.record.interfaces.BaseInterface;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;

/**
 * 删除帖子Action
 * @author Gxx
 * @version 1.0
 */
public class DeleteTopicAction extends ActionSupport implements ServletRequestAware
{
    public String tid;//80个字节
    public String sid;
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
        System.out.println("tid=[" + tid + "]");

        Topic topic = DBSelect.getTopicById(Integer.parseInt(tid));
        if(null == topic)
        {
            errorMsg = "找不到该帖子!";
            return ERROR;
        }
        
        if(topic.getUserId() != user.getId() && user.getUserType().getUserTypeInt() != UserType.SUPER_ADMIN.getUserTypeInt())
        {
            errorMsg = "你无权删除别人的帖子!";
            return ERROR;
        }

        // 删除帖子
        DBUpdate.deleteTopic(topic);
        // 删除回帖
        DBUpdate.deleteReplies(topic.getId());
        message = "帖子删除成功！";

        sid = "" + topic.getSectionId();

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

    public String getSid()
    {
        return sid;
    }

    public void setSid(String sid)
    {
        this.sid = sid;
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
