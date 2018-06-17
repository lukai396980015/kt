package com.kt.managermode.bean.system;

import com.kt.managermode.bean.util.PageParam;

public class UserBean extends PageParam
{
    /**
     * TODO 添加字段注释
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private int userId;
    
    /**
     * 用户名称
     */
    private String userName;
    
    /**
     * 用户昵称
     */
    private String userNick;
    
    /**
     * 电话
     */
    private String userPhone;
    
    /**
     * 年龄
     */
    private String userAge;
    
    /**
     * 性别
     */
    private String userSex;
    
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 状态
     */
    private String userStatus;
    /**
     * 备用
     */
    private String userSalt;
    /**
     * 密码
     */
    private String userPassword;
    public int getUserId()
    {
        return userId;
    }
    public void setUserId(int userId)
    {
        this.userId = userId;
    }
    public String getUserName()
    {
        return userName;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    public String getUserNick()
    {
        return userNick;
    }
    public void setUserNick(String userNick)
    {
        this.userNick = userNick;
    }
    public String getUserPhone()
    {
        return userPhone;
    }
    public void setUserPhone(String userPhone)
    {
        this.userPhone = userPhone;
    }
    public String getUserAge()
    {
        return userAge;
    }
    public void setUserAge(String userAge)
    {
        this.userAge = userAge;
    }
    public String getUserSex()
    {
        return userSex;
    }
    public void setUserSex(String userSex)
    {
        this.userSex = userSex;
    }
    public String getUserAccount()
    {
        return userAccount;
    }
    public void setUserAccount(String userAccount)
    {
        this.userAccount = userAccount;
    }
    public String getUserStatus()
    {
        return userStatus;
    }
    public void setUserStatus(String userStatus)
    {
        this.userStatus = userStatus;
    }
    public String getUserSalt()
    {
        return userSalt;
    }
    public void setUserSalt(String userSalt)
    {
        this.userSalt = userSalt;
    }
    public String getUserPassword()
    {
        return userPassword;
    }
    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }
    
}
