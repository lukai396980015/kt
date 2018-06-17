package com.kt.managermode.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.managermode.bean.system.UserBean;
import com.kt.managermode.dao.system.IUserManagerDao;
import com.kt.managermode.service.system.IUserManagerService;

@Service("UserManagerService")
public class UserManagerServiceImpl implements IUserManagerService
{
    @Autowired
    private IUserManagerDao userManagerDao;
    
    public List<UserBean> listUser(UserBean userBean)
    {
        return userManagerDao.listUser(userBean);
    }
    
    public Integer getCount(UserBean userBean)
    {
        return userManagerDao.getCount(userBean);
    }
}
