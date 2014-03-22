package com.gxx.record.entities.wedisle;

/**
 * ���Ѳ�
 * User: Gxx
 * Time: 2013-11-02 09:08
 */
public class WedisleRelaFriend
{
    /**
     * �û�ID ��Ϊ��
     */
    int userId;
    /**
     * �������� ��Ϊ�� ������20����
     */
    String name;
    /**
     * ���� ��Ϊ�� >0
     */
    int num;
    /**
     * ��ϵ ��Ϊ�� ������15����
     */
    String relationship;
    /**
     * ���� ��Ϊ�� ������50����ĸ
     */
    String email;
    /**
     * �绰 ��Ϊ�� ������20������
     */
    String phone;
    /**
     * ��ַ ��Ϊ�� ������100����
     */
    String place;
    /**
     * ��ע ��Ϊ�� ������100����
     */
    String resv;
    /**
     * ����Ž�ɫ ��Ϊ�� ������20����
     */
    String helpGroup;
    /**
     * ������Ա��ɫ ��Ϊ�� ������20����
     */
    String worker;
    /**
     * ϯλ�� ��Ϊ�� =0��ʾδ���ţ�>0��ʾϯλ��
     */
    int seat;

    /**
     * ����ʱ����õ�
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
     * ��ѯʱ����õ�
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
