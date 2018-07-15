package com.kt.managermode.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kt.managermode.bean.ResultUtilBean;
import com.kt.managermode.bean.system.UserBean;
import com.kt.managermode.service.system.IUserManagerService;

@Controller
@RequestMapping("/userManagerController")
public class UserManagerController
{
    @Autowired
    private IUserManagerService userManagerService;
    
    @RequestMapping(value = "/openUserManager")
    public String userManager(Model model)
    {
        return "system/userManager";
    }
    
    @ResponseBody
    @RequestMapping(value = "/listUser", method = RequestMethod.POST)
    public ResultUtilBean listUser(Model model, UserBean userBean)
    {
        ResultUtilBean result = ResultUtilBean.getResult(userBean.getPage(),
            userManagerService.getCount(userBean),
            userBean.getRows(),
            userManagerService.listUser(userBean));
        return result;
    }
}
