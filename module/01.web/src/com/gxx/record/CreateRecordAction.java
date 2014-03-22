/**
 * File Name:    CreateRecordAction.java
 *
 * File Desc:    创建或者修改记录Action
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
 * 创建或者修改记录Action
 * @author Gxx
 * @version 1.0
 */
public class CreateRecordAction extends ActionSupport implements ServletRequestAware
{
    public String title;//80个字节
    public String content;//10000个字节
    public String groupsStr;//组别 20个字符
    public String tag;//5个标签 每个20个字节
    public boolean isUpdate;//是否更新 否则为新增
    public String recordId;//更新的记录Id
    public String errorMsg;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        User user = (User) request.getSession().getAttribute(BaseInterface.USER);
        if(null == user)
        {
            errorMsg = "请先登录!";
            return ERROR;
        }

        // 更新判断
        if(isUpdate())
        {
            Record record = null;
            if(StringUtils.isNotBlank(recordId))
            {
                record = DBSelect.getRecordByRecordId(Integer.parseInt(recordId));
            }

            if(StringUtils.isBlank(recordId) || record == null || (user.getId() != record.getUserId()))
            {
                errorMsg = "您的操作有误请重试!";
                return ERROR;
            }
        }

        System.out.println("title=[" + title + "]" + ",content=[" + content + "]" + ",groupsStr=[" + groupsStr + "]"
                + ",tag=[" + tag + "]" + ",isUpdate=[" + isUpdate + "]" + ",recordId=[" + recordId + "]");
        
        // 数据校验    
        title = StringUtils.trimToEmpty(title);
        title = BaseUtils.clearStrSpecialSign(title);
        //content = StringUtils.trimToEmpty(content); 不去除头尾空格
        groupsStr = StringUtils.trimToEmpty(groupsStr);
        groupsStr = BaseUtils.clearStrSpecialSign(groupsStr);
        tag = StringUtils.trimToEmpty(tag);
        tag = BaseUtils.clearStrSpecialSign(tag);
        if(StringUtils.isBlank(title) || StringUtils.isBlank(content))
        {
            errorMsg = "标签或者内容不能为空!";
            return ERROR;
        }
        if(BaseUtils.getStrByteLength(title) > 80)
        {
            errorMsg = "标签长度过长!";
            return ERROR;
        }
        if(BaseUtils.getStrByteLength(content) > 10000)
        {
            errorMsg = "内容长度过长!";
            return ERROR;
        }
        if(BaseUtils.getStrByteLength(groupsStr) > 20)
        {
            errorMsg = "分组长度过长!";
            return ERROR;
        }
        if(StringUtils.isNotBlank(tag))
        {
            String[] tags = tag.split(",", -1);
            if(tags.length > 5)
            {
                errorMsg = "最多允许5个标签!";
                return ERROR;
            }
            String newTag = "";
            for(int i=0;i<tags.length;i++)
            {
                String tag = tags[i].trim();
                if(StringUtils.isBlank(tag))
                {
                    continue;
                }
                if(BaseUtils.getStrByteLength(tag) > 20)
                {
                    errorMsg = "单个标签长度不能多于20个字符!";
                    return ERROR;
                }
                if(("," + newTag + ",").indexOf("," + tag + ",") == -1)
                {
                    if(StringUtils.isNotBlank(newTag))
                    {
                        newTag += ",";
                    }
                    newTag += tag;
                }
            }
            tag = newTag;
        }

        // 创建帖子
        Date date = new Date();
        String dateStr = ServiceDataUtil.getDate(date);
        String timeStr = ServiceDataUtil.getTime(date);
        Record record = new Record(user.getId(), groupsStr, title, content, StringUtils.trimToEmpty(tag), dateStr, timeStr, false);
        if(isUpdate)
        {
            record.setId(Integer.parseInt(recordId));
            DBUpdate.updateRecord(record);
        } else
        {
            record = DBUpdate.insertRecord(record);
            System.out.println("1_" + record.getId());
            recordId = "" + record.getId();
        }
        return SUCCESS;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getGroupsStr()
    {
        return groupsStr;
    }

    public void setGroupsStr(String groupsStr)
    {
        this.groupsStr = groupsStr;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public boolean isUpdate()
    {
        return isUpdate;
    }

    public void setUpdate(boolean update)
    {
        isUpdate = update;
    }

    public String getRecordId()
    {
        return recordId;
    }

    public void setRecordId(String recordId)
    {
        this.recordId = recordId;
    }


    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
}
