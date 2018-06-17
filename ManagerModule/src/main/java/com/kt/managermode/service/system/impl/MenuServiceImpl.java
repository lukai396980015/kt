package com.kt.managermode.service.system.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.managermode.bean.system.MenuBean;
import com.kt.managermode.bean.system.MenuBeanTree;
import com.kt.managermode.dao.system.IMenuDao;
import com.kt.managermode.service.system.IMenuService;

@Service("MenuService")
public class MenuServiceImpl implements IMenuService
{

    @Autowired
    private IMenuDao menuDao;
    
    public List<MenuBeanTree> loadMenu()
    {
        List<MenuBeanTree> menuBeans = menuDao.loadPermission(0);
        return this.forMenu(menuBeans);
    }
    
    public List<MenuBeanTree> forMenu(List<MenuBeanTree> menuBeans)
    {
        for (MenuBeanTree menu: menuBeans)
        {
            List<MenuBeanTree> childrenMenuBeans = null;
            if(menu!=null)
            {
                childrenMenuBeans = menuDao.loadPermission(menu.getPermissionId());
            }
            if(childrenMenuBeans!=null)
            {
                menu.setChildrenMenuBeans(childrenMenuBeans);;
                forMenu(childrenMenuBeans);
            }
        }
        return menuBeans;
    }
    
    public List<MenuBeanTree> getChildrenMenu(Integer parentMenuId)
    {
        return menuDao.loadPermission(parentMenuId);
    }
    
    /**
     * 查询菜单列表
     * {@inheritDoc}
     */
    public List<MenuBean> queryMenuList(MenuBean menuBean)
    {
        List<MenuBean> menuBeans = menuDao.queryMenuList(menuBean);
        return menuBeans;
    }
    
    /**
     * 查询菜单列表总数
     * {@inheritDoc}
     */
    public Integer getCount(MenuBean menuBean)
    {
        Integer menuBeans = menuDao.getCount(menuBean);
        return menuBeans;
    }
    
}
