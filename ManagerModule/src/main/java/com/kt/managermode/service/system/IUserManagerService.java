package com.kt.managermode.service.system;

import java.util.List;

import com.kt.managermode.bean.system.UserBean;

public interface IUserManagerService
{
    public List<UserBean> listUser(UserBean userBean);
    
    public Integer getCount(UserBean userBean);
}
