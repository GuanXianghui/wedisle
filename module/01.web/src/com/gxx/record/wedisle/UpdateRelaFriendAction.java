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

import com.gxx.record.dao.DBUpdate;
import com.gxx.record.dao.wedisle.WedisleRelaFriendDao;
import com.gxx.record.entities.wedisle.WedisleRelaFriend;
import com.gxx.record.entities.wedisle.WedisleUser;
import com.gxx.record.interfaces.BaseInterface;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;

/**
 * 修改亲友簿
 * @author Gxx
 * @version 1.0
 */
public class UpdateRelaFriendAction extends ActionSupport implements ServletRequestAware
{
    public String userName;
    public String relationship;
    public String newGroup;
    public String num;
    public String email;
    public String phone;
    public String place;
    public String resv;
    public String message;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        System.out.println("userName=[" + userName + "]" + ",relationship=[" + relationship + "]" + ",newGroup=[" + newGroup + "]"
                + ",num=[" + num + "]" + ",email=[" + email + "]" + ",phone=[" + phone + "]" + ",place=[" + place + "]"
                + ",resv=[" + resv + "]");
        WedisleUser wedisleUser = (WedisleUser)request.getSession().getAttribute(BaseInterface.WEDISLE_USER);
        if(null == wedisleUser)
        {
            message = "请先做登录";
            System.out.println(message);
            return ERROR;
        }

        /**
         * 校验不够完善参考js校验
         */
        if(StringUtils.isBlank(userName) || StringUtils.isBlank(relationship) || StringUtils.isBlank(num))
        {
            message = "必输项不能为空";
            System.out.println(message);
            return ERROR;
        }
        if("自定义分组".equals(relationship)){
            relationship = newGroup;
            if(("," + wedisleUser.getFriendsType() + ","). indexOf("," + relationship + ",") == -1){
                wedisleUser.setFriendsType(wedisleUser.getFriendsType() + "," + relationship);
                DBUpdate.updateWedisleUserFriendType(wedisleUser);
                request.getSession().setAttribute(BaseInterface.WEDISLE_USER, wedisleUser);
            }
        }

        WedisleRelaFriend friend = WedisleRelaFriendDao.getRelaFriend(wedisleUser.getId(), userName);
        if(friend == null){
            friend = new WedisleRelaFriend(wedisleUser.getId(), userName, Integer.parseInt(num), relationship, email, phone,
                    place, resv, StringUtils.EMPTY, StringUtils.EMPTY);
            WedisleRelaFriendDao.insertRelaFriend(friend);
            message = "新增好友成功！";
            System.out.println(message);
            return SUCCESS;
        }

        friend.setRelationship(relationship);
        friend.setNum(Integer.parseInt(num));
        friend.setEmail(email);
        friend.setPhone(phone);
        friend.setPlace(place);
        friend.setResv(resv);
        WedisleRelaFriendDao.updateRelaFriend(friend);
        message = "修改好友成功！";
        System.out.println(message);
        return SUCCESS;
    }


    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getNewGroup() {
        return newGroup;
    }

    public void setNewGroup(String newGroup) {
        this.newGroup = newGroup;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getResv() {
        return resv;
    }

    public void setResv(String resv) {
        this.resv = resv;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
