package com.kt.managermode.dao.system;

import java.util.List;

import com.kt.managermode.bean.system.UserBean;

public interface IUserManagerDao
{
    public List<UserBean> listUser(UserBean userBean);
    
    public Integer getCount(UserBean userBean);
}
