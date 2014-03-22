/**
 * File Name:    CreateRecordAction.java
 *
 * File Desc:    ���������޸ļ�¼Action
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
 * ���������޸ļ�¼Action
 * @author Gxx
 * @version 1.0
 */
public class CreateRecordAction extends ActionSupport implements ServletRequestAware
{
    public String title;//80���ֽ�
    public String content;//10000���ֽ�
    public String groupsStr;//��� 20���ַ�
    public String tag;//5����ǩ ÿ��20���ֽ�
    public boolean isUpdate;//�Ƿ���� ����Ϊ����
    public String recordId;//���µļ�¼Id
    public String errorMsg;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        User user = (User) request.getSession().getAttribute(BaseInterface.USER);
        if(null == user)
        {
            errorMsg = "���ȵ�¼!";
            return ERROR;
        }

        // �����ж�
        if(isUpdate())
        {
            Record record = null;
            if(StringUtils.isNotBlank(recordId))
            {
                record = DBSelect.getRecordByRecordId(Integer.parseInt(recordId));
            }

            if(StringUtils.isBlank(recordId) || record == null || (user.getId() != record.getUserId()))
            {
                errorMsg = "���Ĳ�������������!";
                return ERROR;
            }
        }

        System.out.println("title=[" + title + "]" + ",content=[" + content + "]" + ",groupsStr=[" + groupsStr + "]"
                + ",tag=[" + tag + "]" + ",isUpdate=[" + isUpdate + "]" + ",recordId=[" + recordId + "]");
        
        // ����У��    
        title = StringUtils.trimToEmpty(title);
        title = BaseUtils.clearStrSpecialSign(title);
        //content = StringUtils.trimToEmpty(content); ��ȥ��ͷβ�ո�
        groupsStr = StringUtils.trimToEmpty(groupsStr);
        groupsStr = BaseUtils.clearStrSpecialSign(groupsStr);
        tag = StringUtils.trimToEmpty(tag);
        tag = BaseUtils.clearStrSpecialSign(tag);
        if(StringUtils.isBlank(title) || StringUtils.isBlank(content))
        {
            errorMsg = "��ǩ�������ݲ���Ϊ��!";
            return ERROR;
        }
        if(BaseUtils.getStrByteLength(title) > 80)
        {
            errorMsg = "��ǩ���ȹ���!";
            return ERROR;
        }
        if(BaseUtils.getStrByteLength(content) > 10000)
        {
            errorMsg = "���ݳ��ȹ���!";
            return ERROR;
        }
        if(BaseUtils.getStrByteLength(groupsStr) > 20)
        {
            errorMsg = "���鳤�ȹ���!";
            return ERROR;
        }
        if(StringUtils.isNotBlank(tag))
        {
            String[] tags = tag.split(",", -1);
            if(tags.length > 5)
            {
                errorMsg = "�������5����ǩ!";
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
                    errorMsg = "������ǩ���Ȳ��ܶ���20���ַ�!";
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

        // ��������
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
