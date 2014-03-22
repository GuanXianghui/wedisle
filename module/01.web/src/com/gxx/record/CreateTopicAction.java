/**
 * File Name:    CreateTopicAction.java
 *
 * File Desc:    ���������޸�����Action
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
import com.gxx.record.entities.Topic;
import com.gxx.record.entities.User;
import com.gxx.record.enums.ActionType;
import com.gxx.record.interfaces.BaseInterface;
import com.gxx.record.utils.BaseUtils;
import com.gxx.record.utils.ServiceDataUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * ���������޸�����Action
 * @author Gxx
 * @version 1.0
 */
public class CreateTopicAction extends ActionSupport implements ServletRequestAware
{
    public int sid;
    public String title;//80���ֽ�
    public String content;//10000���ֽ�
    public String tag;//5����ǩ ÿ��20���ֽ�
    public String errorMsg;
    public boolean isUpdate;//�Ƿ���� ����Ϊ����
    public String tid;//���µļ�¼Id
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
            Topic topic = null;
            if(StringUtils.isNotBlank(tid))
            {
                topic = DBSelect.getTopicById(Integer.parseInt(tid));
            }

            if(StringUtils.isBlank(tid) || topic == null || (user.getId() != topic.getUserId()))
            {
                errorMsg = "���Ĳ�������������!";
                return ERROR;
            }
        }

        System.out.println("title=[" + title + "]" + ",content=[" + content + "]" + ",tag=[" + tag + "]");
        
        // ����У��    
        title = StringUtils.trimToEmpty(title);
        title = BaseUtils.clearStrSpecialSign(title);
        //content = StringUtils.trimToEmpty(content); ��ȥ��ͷβ�ո�
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
        Topic topic = new Topic(sid, user.getId(), title, content, StringUtils.trimToEmpty(tag), dateStr, timeStr, false, 0, false);

        if(isUpdate)
        {
            topic.setId(Integer.parseInt(tid));
            // ���� id��userId ֻ�޸� title content tag dateStr timeStr ��������
            DBUpdate.updateTopic(topic);
        } else
        {
            DBUpdate.insertTopic(topic);

            int maxId = DBSelect.queryNewestTopic().getId();
            tid = new String("" + maxId);

            // ��¼�û�����������־
            ActionType actionType = ActionType.CREATE_TOPIC;
            int userId = user.getId();
            ActionLog actionLog = new ActionLog(actionType, userId, dateStr, timeStr, maxId, 0);
            DBUpdate.addActionLog(actionLog);

            /**
             * �������� �ӻ���
             * �ӻ��ֵĶ�������¼(һ��һ�� +2��) ����(һ�� +2��) ����(һ�� +1�� ���� +2��)
             */
            BaseUtils.addScore(actionLog);
        }

        return SUCCESS;
    }

    public int getSid()
    {
        return sid;
    }

    public void setSid(int sid)
    {
        this.sid = sid;
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

    public String getTid()
    {
        return tid;
    }

    public void setTid(String tid)
    {
        this.tid = tid;
    }


    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
}
