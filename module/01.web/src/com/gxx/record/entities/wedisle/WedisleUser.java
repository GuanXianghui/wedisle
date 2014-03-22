package com.gxx.record.entities.wedisle;

/**
 * 用户表
 * Create by Gxx
 * Time: 2013-10-13 17:48
 */
public class WedisleUser
{
    int id;
    String userName;
    String password;
    int type;
    String email;
    boolean isEmailValidate;
    String mobile;
    boolean isMobileValidate;
    int userState;
    int errorPwdNum;
    String friendsType;
    String registerDate;
    String registerTime;
    String registerIp;

    /**
     * 注册准备数据时候用
     * @param userName
     * @param password
     * @param type
     * @param email
     * @param userState
     * @param errorPwdNum
     * @param friendsType
     * @param registerDate
     * @param registerTime
     * @param registerIp
     */
    public WedisleUser(String userName, String password, int type, String email, int userState, int errorPwdNum,
            String friendsType, String registerDate, String registerTime, String registerIp)
    {
        this.userName = userName;
        this.password = password;
        this.type = type;
        this.email = email;
        this.userState = userState;
        this.errorPwdNum = errorPwdNum;
        this.friendsType = friendsType;
        this.registerDate = registerDate;
        this.registerTime = registerTime;
        this.registerIp = registerIp;
    }

    /**
     * 查询时候用到
     * @param id
     * @param userName
     * @param password
     * @param type
     * @param email
     * @param emailValidate
     * @param mobile
     * @param mobileValidate
     * @param userState
     * @param errorPwdNum
     * @param friendsType
     * @param registerDate
     * @param registerTime
     * @param registerIp
     */
    public WedisleUser(int id, String userName, String password, int type, String email, boolean emailValidate,
                       String mobile, boolean mobileValidate, int userState, int errorPwdNum, String friendsType,
                       String registerDate, String registerTime, String registerIp) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.type = type;
        this.email = email;
        isEmailValidate = emailValidate;
        this.mobile = mobile;
        isMobileValidate = mobileValidate;
        this.userState = userState;
        this.errorPwdNum = errorPwdNum;
        this.friendsType = friendsType;
        this.registerDate = registerDate;
        this.registerTime = registerTime;
        this.registerIp = registerIp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailValidate() {
        return isEmailValidate;
    }

    public void setEmailValidate(boolean emailValidate) {
        isEmailValidate = emailValidate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isMobileValidate() {
        return isMobileValidate;
    }

    public void setMobileValidate(boolean mobileValidate) {
        isMobileValidate = mobileValidate;
    }

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public int getErrorPwdNum() {
        return errorPwdNum;
    }

    public void setErrorPwdNum(int errorPwdNum) {
        this.errorPwdNum = errorPwdNum;
    }

    public String getFriendsType() {
        return friendsType;
    }

    public void setFriendsType(String friendsType) {
        this.friendsType = friendsType;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }
}
