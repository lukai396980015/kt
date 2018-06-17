package com.kt.managermode.dao.system;

import java.util.List;

import com.kt.managermode.bean.system.MenuBean;
import com.kt.managermode.bean.system.MenuBeanTree;


public interface IMenuDao
{
    public List<MenuBeanTree> loadPermission(Integer parentId);
    
    public List<MenuBean> queryMenuList(MenuBean menuBean);
    
    public Integer getCount(MenuBean menuBean);
}
