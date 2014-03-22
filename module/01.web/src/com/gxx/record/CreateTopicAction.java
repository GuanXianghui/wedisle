/**
 * File Name:    CreateTopicAction.java
 *
 * File Desc:    创建或者修改帖子Action
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
 * 创建或者修改帖子Action
 * @author Gxx
 * @version 1.0
 */
public class CreateTopicAction extends ActionSupport implements ServletRequestAware
{
    public int sid;
    public String title;//80个字节
    public String content;//10000个字节
    public String tag;//5个标签 每个20个字节
    public String errorMsg;
    public boolean isUpdate;//是否更新 否则为新增
    public String tid;//更新的记录Id
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
            Topic topic = null;
            if(StringUtils.isNotBlank(tid))
            {
                topic = DBSelect.getTopicById(Integer.parseInt(tid));
            }

            if(StringUtils.isBlank(tid) || topic == null || (user.getId() != topic.getUserId()))
            {
                errorMsg = "您的操作有误请重试!";
                return ERROR;
            }
        }

        System.out.println("title=[" + title + "]" + ",content=[" + content + "]" + ",tag=[" + tag + "]");
        
        // 数据校验    
        title = StringUtils.trimToEmpty(title);
        title = BaseUtils.clearStrSpecialSign(title);
        //content = StringUtils.trimToEmpty(content); 不去除头尾空格
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
        Topic topic = new Topic(sid, user.getId(), title, content, StringUtils.trimToEmpty(tag), dateStr, timeStr, false, 0, false);

        if(isUpdate)
        {
            topic.setId(Integer.parseInt(tid));
            // 根据 id和userId 只修改 title content tag dateStr timeStr 其他不变
            DBUpdate.updateTopic(topic);
        } else
        {
            DBUpdate.insertTopic(topic);

            int maxId = DBSelect.queryNewestTopic().getId();
            tid = new String("" + maxId);

            // 记录用户创建帖子日志
            ActionType actionType = ActionType.CREATE_TOPIC;
            int userId = user.getId();
            ActionLog actionLog = new ActionLog(actionType, userId, dateStr, timeStr, maxId, 0);
            DBUpdate.addActionLog(actionLog);

            /**
             * 动作发生 加积分
             * 加积分的动作：登录(一天一次 +2分) 发帖(一次 +2分) 回帖(一次 +1分 贴主 +2分)
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
