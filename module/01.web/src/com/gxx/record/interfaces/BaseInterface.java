/**
 * File Name:    BaseInterface.java
 *
 * File Desc:    基础接口
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
 * History:      2013-06-24 created by Gxx
 */
package com.gxx.record.interfaces;

/**
 * 基础接口
 * @author Gxx
 * @version 1.0
 */
public class BaseInterface
{
    /**
     * 论坛首页链接头
     */
    public static final String BASE_URL = "base_url";
    /**
     * ppCall首页链接头
     */
    public static final String PP_CALL_BASE_URL = "pp_call_base_url";
    /**
     * wed isle首页链接头
     */
    public static final String WED_ISLE_BASE_URL = "wed_isle_base_url";
    /**
     * locates首页链接头
     */
    public static final String LOCATES_BASE_URL = "locates_base_url";
    /**
     * mysql数据库链接
     */
    public static final String MYSQL_CONNECTION = "mysql_connection";
    /**
     * 帖子每页显示条数
     */
    public static final String TOPIC_PAGE_SIZE = "topic_page_size";
    /**
     * 回帖每页显示条数
     */
    public static final String REPLY_PAGE_SIZE = "reply_page_size";
    /**
     * 登录(一天一次)加积分数
     */
    public static final String LOGIN_ADD_SCORE = "login_add_score";
    /**
     * 发帖(一次)加积分数
     */
    public static final String CREATE_TOPIC_ADD_SCORE = "create_topic_add_score";
    /**
     * 回帖(一次)加积分数
     */
    public static final String REPLY_TOPIC_ADD_SCORE = "reply_topic_add_score";
    /**
     * 回帖(一次)帖主加积分数
     */
    public static final String REPLY_TOPIC_USER_ADD_SCORE = "reply_topic_user_add_score";
    /**
     * 用户对象
     */
    public static final String USER = "user";
    /**
     * 用户对象
     */
    public static final String WEDISLE_USER = "wedisle_user";
    /**
     * 默认头像地址 前需要加base_url
     */
    public static final String DEFAULT_HEAD_PHOTO = "default_head_photo";
}
