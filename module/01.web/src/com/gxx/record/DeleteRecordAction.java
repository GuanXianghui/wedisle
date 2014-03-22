/**
 * File Name:    DeleteRecordAction.java
 *
 * File Desc:    删除记录Action
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
import com.gxx.record.entities.User;
import com.gxx.record.interfaces.BaseInterface;
import com.gxx.record.utils.BaseUtils;
import com.gxx.record.utils.ServiceDataUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 删除记录Action
 * @author Gxx
 * @version 1.0
 */
public class DeleteRecordAction extends ActionSupport implements ServletRequestAware
{
    public String rid;//80个字节
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
        System.out.println("rid=[" + rid + "]");

        Record record = DBSelect.getRecordByUserIdAndRecordId(user.getId(), Integer.parseInt(rid));
        if(null == record)
        {
            errorMsg = "找不到该记录!";
            return ERROR;
        }

        // 删除记录
        DBUpdate.deleteRecord(record);
        message = "记录删除成功！";
        return SUCCESS;
    }

    public String getRid()
    {
        return rid;
    }

    public void setRid(String rid)
    {
        this.rid = rid;
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

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
