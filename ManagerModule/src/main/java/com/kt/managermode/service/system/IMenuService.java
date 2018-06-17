package com.kt.managermode.service.system;

import java.util.List;

import com.kt.managermode.bean.system.MenuBean;
import com.kt.managermode.bean.system.MenuBeanTree;

public interface IMenuService
{
    public List<MenuBeanTree> loadMenu();
    
    public List<MenuBeanTree> getChildrenMenu(Integer parentMenuId);
    
    public List<MenuBean> queryMenuList(MenuBean menuBean);
    
    public Integer getCount(MenuBean menuBean);
}
