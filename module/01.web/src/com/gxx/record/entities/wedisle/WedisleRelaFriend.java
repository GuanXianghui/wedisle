package com.gxx.record.entities.wedisle;

/**
 * 亲友簿
 * User: Gxx
 * Time: 2013-11-02 09:08
 */
public class WedisleRelaFriend
{
    /**
     * 用户ID 不为空
     */
    int userId;
    /**
     * 亲友名称 不为空 不超过20个字
     */
    String name;
    /**
     * 人数 不为空 >0
     */
    int num;
    /**
     * 关系 不为空 不超过15个字
     */
    String relationship;
    /**
     * 邮箱 可为空 不超过50个字母
     */
    String email;
    /**
     * 电话 可为空 不超过20个数字
     */
    String phone;
    /**
     * 地址 可为空 不超过100个字
     */
    String place;
    /**
     * 备注 可为空 不超过100个字
     */
    String resv;
    /**
     * 帮帮团角色 可为空 不超过20个字
     */
    String helpGroup;
    /**
     * 工作人员角色 可为空 不超过20个字
     */
    String worker;
    /**
     * 席位号 不为空 =0表示未安排，>0表示席位号
     */
    int seat;

    /**
     * 新增时候会用到
     * @param userId
     * @param name
     * @param num
     * @param relationship
     * @param email
     * @param phone
     * @param place
     * @param resv
     * @param helpGroup
     * @param worker
     */
    public WedisleRelaFriend(int userId, String name, int num, String relationship, String email, String phone,
                             String place, String resv, String helpGroup, String worker) {
        this.userId = userId;
        this.name = name;
        this.num = num;
        this.relationship = relationship;
        this.email = email;
        this.phone = phone;
        this.place = place;
        this.resv = resv;
        this.helpGroup = helpGroup;
        this.worker = worker;
        this.seat = 0;
    }

    /**
     * 查询时候会用到
     * @param userId
     * @param name
     * @param num
     * @param relationship
     * @param email
     * @param phone
     * @param place
     * @param resv
     * @param helpGroup
     * @param worker
     * @param seat
     */
    public WedisleRelaFriend(int userId, String name, int num, String relationship, String email, String phone,
                             String place, String resv, String helpGroup, String worker, int seat) {
        this.userId = userId;
        this.name = name;
        this.num = num;
        this.relationship = relationship;
        this.email = email;
        this.phone = phone;
        this.place = place;
        this.resv = resv;
        this.helpGroup = helpGroup;
        this.worker = worker;
        this.seat = seat;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
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

    public String getHelpGroup() {
        return helpGroup;
    }

    public void setHelpGroup(String helpGroup) {
        this.helpGroup = helpGroup;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}
