/**
 * File Name:    CreateTopicAction.java
 *
 * File Desc:
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
import com.gxx.record.entities.Reply;
import com.gxx.record.entities.Topic;
import com.gxx.record.entities.User;
import com.gxx.record.enums.ActionType;
import com.gxx.record.interfaces.BaseInterface;
import com.gxx.record.utils.BaseUtils;
import com.gxx.record.utils.PropertyUtil;
import com.gxx.record.utils.ServiceDataUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Gxx
 * @version 1.0
 */
public class ReplyTopicAction extends ActionSupport implements ServletRequestAware
{
    public int tid;
    public String content;
    public String errorMsg;
    public String lastPage;
    public String pageNum;
    public boolean isUpdate;//是否更新 否则为新增
    public String rid;//更新的回帖Id
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
            Reply reply = null;
            if(StringUtils.isNotBlank(rid))
            {
                reply = DBSelect.getReplyById(Integer.parseInt(rid));
            }

            if(StringUtils.isBlank(rid) || reply == null || (user.getId() != reply.getUserId()))
            {
                errorMsg = "您的操作有误请重试!";
                return ERROR;
            }
        }

        System.out.println("topicId=[" + tid + "]" + ",content=[" + content + "]" + ",lastPage=[" + lastPage + "]"
                + ",isUpdate=[" + isUpdate + "]" + ",rid=[" + rid + "]");

        //content = StringUtils.trimToEmpty(content); 不去除头尾空格
        // 数据校验
        if(BaseUtils.getStrByteLength(content) > 10000)
        {
            errorMsg = "内容长度过长!";
            return ERROR;
        }

        // 创建回帖
        Date date = new Date();
        String dateStr = ServiceDataUtil.getDate(date);
        String timeStr = ServiceDataUtil.getTime(date);
        Reply reply = new Reply(tid, user.getId(), content, dateStr, timeStr, false);

        if(isUpdate)
        {
            reply.setId(Integer.parseInt(rid));
            // 根据 id和userId 只修改 content dateStr timeStr 其他不变
            DBUpdate.updateReply(reply);
        } else
        {
            DBUpdate.insertReply(reply);

            // 记录用户创建回帖日志
            ActionType actionType = ActionType.REPLY_TOPIC;
            int userId = user.getId();
            ActionLog actionLog = new ActionLog(actionType, userId, dateStr, timeStr, tid, DBSelect.queryNewestReply().getId());
            DBUpdate.addActionLog(actionLog);

            /**
             * 动作发生 加积分
             * 加积分的动作：登录(一天一次 +2分) 发帖(一次 +2分) 回帖(一次 +1分 贴主 +2分)
             */
            BaseUtils.addScore(actionLog);
        }

        int size = DBSelect.queryRepliesByTopicId(tid, "", "").size();
        if(!"on".equalsIgnoreCase(lastPage) || (1 >= size))
        {
            pageNum = "1";
        } else
        {
            int pageSize = Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.REPLY_PAGE_SIZE));
            int maxPageNum = (size-1)/pageSize+1;
            pageNum = "" + maxPageNum;
        }

        return SUCCESS;
    }

    public int getTid()
    {
        return tid;
    }

    public void setTid(int tid)
    {
        this.tid = tid;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public String getLastPage()
    {
        return lastPage;
    }

    public void setLastPage(String lastPage)
    {
        this.lastPage = lastPage;
    }

    public String getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(String pageNum)
    {
        this.pageNum = pageNum;
    }

    public boolean isUpdate()
    {
        return isUpdate;
    }

    public void setUpdate(boolean update)
    {
        isUpdate = update;
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
}
