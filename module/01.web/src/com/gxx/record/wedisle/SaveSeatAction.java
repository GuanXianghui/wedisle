/**
 * File Name:    AddRelaFriendAction.java
 *
 * File Desc:    新增亲友簿
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
import com.gxx.record.dao.wedisle.WedisleSeatInfoDao;
import com.gxx.record.entities.wedisle.WedisleRelaFriend;
import com.gxx.record.entities.wedisle.WedisleSeatInfo;
import com.gxx.record.entities.wedisle.WedisleUser;
import com.gxx.record.interfaces.BaseInterface;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 保存席位
 * @author Gxx
 * @version 1.0
 */
public class SaveSeatAction extends ActionSupport implements ServletRequestAware
{
    public String tableCount;//桌数
    public String numEveryTable;//每桌人数
    public String relaFriendsWithSeatNames;//安排座位亲友名字
    public String relaFriendsWithSeatNums;//安排座位人数
    public String relaFriendsWithSeatSeats;//安排座位席位
    public String relaFriendsWithoutSeatNames;//未安排座位亲友名字
    public String relaFriendsWithoutSeatNums;//未安排座位人数
    public String relaFriendsWithoutSeatSeats;//未安排座位席位
    public String errorMsg;
    public String message;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        System.out.println("tableCount=[" + tableCount + "]" + ",numEveryTable=[" + numEveryTable + "]"
                + ",relaFriendsWithSeatNames=[" + relaFriendsWithSeatNames + "]"
                + ",relaFriendsWithSeatNums=[" + relaFriendsWithSeatNums + "]"
                + ",relaFriendsWithSeatSeats=[" + relaFriendsWithSeatSeats + "]"
                + ",relaFriendsWithoutSeatNames=[" + relaFriendsWithoutSeatNames + "]"
                + ",relaFriendsWithoutSeatNums=[" + relaFriendsWithoutSeatNums + "]"
                + ",relaFriendsWithoutSeatSeats=[" + relaFriendsWithoutSeatSeats + "]");
        WedisleUser wedisleUser = (WedisleUser)request.getSession().getAttribute(BaseInterface.WEDISLE_USER);
        if(null == wedisleUser)
        {
            errorMsg = "请先做登录";
            System.out.println(errorMsg);
            return ERROR;
        }
        if(StringUtils.isBlank(tableCount) || StringUtils.isBlank(numEveryTable))
        {
            errorMsg = "桌数与每桌人数不能为空";
            System.out.println(errorMsg);
            return ERROR;
        }
        try
        {
            Integer.parseInt(tableCount);
            Integer.parseInt(numEveryTable);
        } catch (Exception e)
        {
            errorMsg = "桌数与每桌人数不合法";
            System.out.println(errorMsg);
            return ERROR;
        }

        // 保存亲友簿
        List friends = new ArrayList();
        if(StringUtils.isNotBlank(relaFriendsWithSeatNames))
        {
            for(int i=0;i<relaFriendsWithSeatNames.split(",").length;i++)
            {
                WedisleRelaFriend friend = WedisleRelaFriendDao.getRelaFriend(wedisleUser.getId(),
                        relaFriendsWithSeatNames.split(",")[i],
                        Integer.parseInt(relaFriendsWithSeatNums.split(",")[i]));
                if(null == friend)
                {
                    errorMsg = "找不到名字为[" + relaFriendsWithSeatNames.split(",")[i]
                            + "],人数为[" + relaFriendsWithSeatNums.split(",")[i] + "]的亲友信息";
                    System.out.println(errorMsg);
                    return ERROR;
                }
                friend.setSeat(Integer.parseInt(relaFriendsWithSeatSeats.split(",")[i]));
                friends.add(friend);
            }
        }
        if(StringUtils.isNotBlank(relaFriendsWithoutSeatNames))
        {
            for(int i=0;i<relaFriendsWithoutSeatNames.split(",").length;i++)
            {
                WedisleRelaFriend friend = WedisleRelaFriendDao.getRelaFriend(wedisleUser.getId(),
                        relaFriendsWithoutSeatNames.split(",")[i],
                        Integer.parseInt(relaFriendsWithoutSeatNums.split(",")[i]));
                if(null == friend)
                {
                    errorMsg = "找不到名字为[" + relaFriendsWithoutSeatNames.split(",")[i]
                            + "],人数为[" + relaFriendsWithoutSeatNums.split(",")[i] + "]的亲友信息";
                    System.out.println(errorMsg);
                    return ERROR;
                }
                friend.setSeat(Integer.parseInt(relaFriendsWithoutSeatSeats.split(",")[i]));
                friends.add(friend);
            }
        }
        for(int i=0;i<friends.size();i++)
        {
            WedisleRelaFriendDao.updateRelaFriend((WedisleRelaFriend)friends.get(i));
        }

        // 更新席位信息
        WedisleSeatInfo seatInfo = WedisleSeatInfoDao.getSeatInfoByUserId(wedisleUser.getId());
        seatInfo.setTableCount(Integer.parseInt(tableCount));
        seatInfo.setNumEveryTable(Integer.parseInt(numEveryTable));
        WedisleSeatInfoDao.updateSeatInfo(seatInfo);
        message = "保存席位成功！";
        System.out.println(message);
        return SUCCESS;
    }

    public String getTableCount() {
        return tableCount;
    }

    public void setTableCount(String tableCount) {
        this.tableCount = tableCount;
    }

    public String getNumEveryTable() {
        return numEveryTable;
    }

    public void setNumEveryTable(String numEveryTable) {
        this.numEveryTable = numEveryTable;
    }

    public String getRelaFriendsWithSeatNames() {
        return relaFriendsWithSeatNames;
    }

    public void setRelaFriendsWithSeatNames(String relaFriendsWithSeatNames) {
        this.relaFriendsWithSeatNames = relaFriendsWithSeatNames;
    }

    public String getRelaFriendsWithSeatNums() {
        return relaFriendsWithSeatNums;
    }

    public void setRelaFriendsWithSeatNums(String relaFriendsWithSeatNums) {
        this.relaFriendsWithSeatNums = relaFriendsWithSeatNums;
    }

    public String getRelaFriendsWithSeatSeats() {
        return relaFriendsWithSeatSeats;
    }

    public void setRelaFriendsWithSeatSeats(String relaFriendsWithSeatSeats) {
        this.relaFriendsWithSeatSeats = relaFriendsWithSeatSeats;
    }

    public String getRelaFriendsWithoutSeatNames() {
        return relaFriendsWithoutSeatNames;
    }

    public void setRelaFriendsWithoutSeatNames(String relaFriendsWithoutSeatNames) {
        this.relaFriendsWithoutSeatNames = relaFriendsWithoutSeatNames;
    }

    public String getRelaFriendsWithoutSeatNums() {
        return relaFriendsWithoutSeatNums;
    }

    public void setRelaFriendsWithoutSeatNums(String relaFriendsWithoutSeatNums) {
        this.relaFriendsWithoutSeatNums = relaFriendsWithoutSeatNums;
    }

    public String getRelaFriendsWithoutSeatSeats() {
        return relaFriendsWithoutSeatSeats;
    }

    public void setRelaFriendsWithoutSeatSeats(String relaFriendsWithoutSeatSeats) {
        this.relaFriendsWithoutSeatSeats = relaFriendsWithoutSeatSeats;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
}
