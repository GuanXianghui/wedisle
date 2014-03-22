/**
 * File Name:    User.java
 *
 * File Desc:    用户实体
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
 * History:      2013-06-23 created by Gxx
 */
package com.gxx.record.entities;

import com.gxx.record.enums.UserState;
import com.gxx.record.enums.UserType;

/**
 * 用户实体
 * @author Gxx
 * @version 1.0
 */
public class User
{
    int id;
    String userName;
    String password;
    String email;
    String headPhoto;
    UserType userType;
    UserState userState;
    int sex;
    String birthday;
    int score;
    String registerDate;
    String registerTime;
    String registerIp;
    String visitDate;
    String visitTime;
    String visitIp;

    public User(int id, String userName, String password, String email, String headPhoto, UserType userType, UserState userState, int sex, String birthday, int score, String registerDate, String registerTime, String registerIp, String visitDate, String visitTime, String visitIp)
    {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.headPhoto = headPhoto;
        this.userType = userType;
        this.userState = userState;
        this.sex = sex;
        this.birthday = birthday;
        this.score = score;
        this.registerDate = registerDate;
        this.registerTime = registerTime;
        this.registerIp = registerIp;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.visitIp = visitIp;
    }

    public User(String userName, String password, String email, String headPhoto, UserType userType, UserState userState, int sex, String birthday, int score, String registerDate, String registerTime, String registerIp, String visitDate, String visitTime, String visitIp)
    {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.headPhoto = headPhoto;
        this.userType = userType;
        this.userState = userState;
        this.sex = sex;
        this.birthday = birthday;
        this.score = score;
        this.registerDate = registerDate;
        this.registerTime = registerTime;
        this.registerIp = registerIp;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.visitIp = visitIp;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public UserState getUserState()
    {
        return userState;
    }

    public void setUserState(UserState userState)
    {
        this.userState = userState;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public String getRegisterDate()
    {
        return registerDate;
    }

    public void setRegisterDate(String registerDate)
    {
        this.registerDate = registerDate;
    }

    public String getRegisterTime()
    {
        return registerTime;
    }

    public void setRegisterTime(String registerTime)
    {
        this.registerTime = registerTime;
    }

    public String getRegisterIp()
    {
        return registerIp;
    }

    public void setRegisterIp(String registerIp)
    {
        this.registerIp = registerIp;
    }

    public String getVisitDate()
    {
        return visitDate;
    }

    public void setVisitDate(String visitDate)
    {
        this.visitDate = visitDate;
    }

    public String getVisitTime()
    {
        return visitTime;
    }

    public void setVisitTime(String visitTime)
    {
        this.visitTime = visitTime;
    }

    public String getVisitIp()
    {
        return visitIp;
    }

    public void setVisitIp(String visitIp)
    {
        this.visitIp = visitIp;
    }

    public String getHeadPhoto()
    {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto)
    {
        this.headPhoto = headPhoto;
    }

    public UserType getUserType()
    {
        return userType;
    }

    public void setUserType(UserType userType)
    {
        this.userType = userType;
    }
}
