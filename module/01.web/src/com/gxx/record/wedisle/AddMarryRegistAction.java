/**
 * File Name:    AddRelaFriendAction.java
 *
 * File Desc:    �������Ѳ�
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

import com.gxx.record.dao.wedisle.WedisleMarryRegistDao;
import com.gxx.record.entities.wedisle.WedisleMarryRegist;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;

/**
 * ���������ǼǴ�
 * @author Gxx
 * @version 1.0
 */
public class AddMarryRegistAction extends ActionSupport implements ServletRequestAware
{
    public String id;
    public String type;
    public String name;
    public String place;
    public String phone;
    public String time;
    public String bigImageSrc;
    public String imageSrc;
    public String message;
    HttpServletRequest request;


    public String execute() throws Exception
    {
        System.out.println("name=[" + name + "]" + ",place=[" + place + "]" + ",phone=[" + phone + "]"
                + ",time=[" + time + "]" + ",bigImageSrc=[" + bigImageSrc + "]" + ",imageSrc=[" + imageSrc + "]");
        if(StringUtils.isBlank(name) || StringUtils.isBlank(place) || StringUtils.isBlank(phone)
                || StringUtils.isBlank(time) || StringUtils.isBlank(bigImageSrc) || StringUtils.isBlank(imageSrc))
        {
            if(!"delete".equals(type)){
                message = "�������Ϊ��";
                System.out.println(message);
                return ERROR;
            }
            WedisleMarryRegist regist = WedisleMarryRegistDao.getWedisleMarryRegistById(Integer.parseInt(id));
            WedisleMarryRegistDao.deleteWedisleMarryRegist(regist);
            message = "ɾ�������ǼǴ��ɹ���";
        } else {
            WedisleMarryRegist regist = WedisleMarryRegistDao.getWedisleMarryRegistByName(name);
            if(regist == null){
                regist = new WedisleMarryRegist(name, place, phone, time, bigImageSrc, imageSrc);
                WedisleMarryRegistDao.insertWedisleMarryRegist(regist);
                message = "���������ǼǴ��ɹ���";
            } else {
                regist.setPlace(place);
                regist.setPhone(phone);
                regist.setTime(time);
                regist.setBigImageSrc(bigImageSrc);
                regist.setImageSrc(imageSrc);
                WedisleMarryRegistDao.updateWedisleMarryRegist(regist);
                message = "�޸Ļ����ǼǴ��ɹ���";
            }
        }
        System.out.println(message);
        return SUCCESS;
    }


    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBigImageSrc() {
        return bigImageSrc;
    }

    public void setBigImageSrc(String bigImageSrc) {
        this.bigImageSrc = bigImageSrc;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
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
