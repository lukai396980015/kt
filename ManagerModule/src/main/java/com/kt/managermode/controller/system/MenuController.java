package com.kt.managermode.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kt.managermode.bean.ResultUtilBeanSingle;
import com.kt.managermode.util.ResultCodes;
import com.kt.managermode.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kt.managermode.bean.ResultUtilBean;
import com.kt.managermode.bean.system.MenuBean;
import com.kt.managermode.bean.system.MenuBeanTree;
import com.kt.managermode.service.system.IMenuService;

@Controller
@RequestMapping("/menuController")
public class MenuController
{
    @Autowired
    private IMenuService menuService;
    
    @ResponseBody
    @RequestMapping(value = "/loadMenu",method = RequestMethod.POST)
    public List<MenuBeanTree> loadMenu(Model model)
    {
        return menuService.loadMenu();
    }
    @RequestMapping(value = "/getChildrenMenu",method = RequestMethod.POST)
    public List<MenuBeanTree> getChildrenMenu(Integer permissionId, Model model)
    {
        return menuService.getChildrenMenu(permissionId);
    }
    @ResponseBody
    @RequestMapping(value = "/queryMenuList",method = RequestMethod.POST)
    public ResultUtilBean queryMenuList(Model model,MenuBean menuBean)
    {
        ResultUtilBean result = ResultUtilBean.getResult(menuBean.getPage(),
            menuService.getCount(menuBean),
            menuBean.getRows(),
            menuService.queryMenuList(menuBean));
        return result;
    }
    @RequestMapping(value = "/showAllMenu")
    public String showAllMenu(Model model)
    {
        return "system/menuManager";
    }

    @ResponseBody
    @RequestMapping(value = "/getMenuNameByid")
    public ResultUtilBeanSingle getMenuNameByid(Model model,MenuBean menuBean)
    {
        if(menuBean==null|| StringUtil.isEmpty(menuBean.getPermissionId()))
        {
            return ResultUtilBeanSingle.getResult(ResultCodes.PARAM_ERROR,"参数异常",null);
        }
        MenuBean resultMenuBean = menuService.getMenuNameByid(menuBean);
        ResultUtilBeanSingle result = ResultUtilBeanSingle.getResult(ResultCodes.SUCCESS,"成功",resultMenuBean);
        return result;
    }
}
