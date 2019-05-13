package com.kt.managermode.controller.system;

import com.kt.managermode.bean.system.ServerResponse;
import com.kt.managermode.util.ResultCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kt.managermode.bean.ResultUtilBean;
import com.kt.managermode.bean.system.UserBean;
import com.kt.managermode.service.system.IUserManagerService;

import java.util.HashMap;
import java.util.Map;

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
    
    @ResponseBody
    @RequestMapping(value = "/listUserVue", method = RequestMethod.POST)
    public ServerResponse listUserVue(Model model, UserBean userBean)
    {
        ResultUtilBean result = ResultUtilBean.getResult(userBean.getPage(),
            userManagerService.getCount(userBean),
            userBean.getRows(),
            userManagerService.listUser(userBean));
        Map<String,Object> resultData = new HashMap<String,Object>();
        resultData.put("data",result);
        ServerResponse serverResponse = ServerResponse.createDefaultResult(ResultCodes.SUCCESS,"success",resultData);
        return serverResponse;
    }
}
