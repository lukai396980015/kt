package com.kt.managermode.service.system;

import java.util.List;
import java.util.Map;

import com.kt.managermode.bean.system.MenuBean;
import com.kt.managermode.bean.system.MenuBeanTree;

public interface IMenuService
{
    public List<MenuBeanTree> loadMenu();
    
    public Map<String,Object> userinfoAndPermission();
    
    public List<MenuBeanTree> getChildrenMenu(Integer parentMenuId);
    
    public List<MenuBean> queryMenuList(MenuBean menuBean);
    
    public Integer getCount(MenuBean menuBean);

    public MenuBean getMenuNameByid(MenuBean menuBean);
}
