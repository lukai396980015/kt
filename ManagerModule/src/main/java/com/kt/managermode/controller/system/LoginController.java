package com.kt.managermode.controller.system;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import com.kt.managermode.bean.system.ServerResponse;
import com.kt.managermode.bean.util.StriUtil;
import com.kt.managermode.util.ResultCodes;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/loginController")
public class LoginController
{
    Logger logger = Logger.getLogger(LoginController.class);
    /**
     * 如果登录成功，跳转至首页；登录失败，则将失败信息反馈对用户
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/login")
    public String doLogin(HttpServletRequest request, Model model) {
        String msg = "";
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        Subject subject = SecurityUtils.getSubject();
        // 如果当前已有用户被认证
        if (subject.isAuthenticated()) {
            // 使当前用户登出
            subject.logout();
        }
        
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(true);
        
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                return "main";
            } else {
                return "main";
            }
        } catch (IncorrectCredentialsException e) {
            msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";
            model.addAttribute("message", msg);
            logger.error(msg);
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
            model.addAttribute("message", msg);
            logger.error(msg);
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
            model.addAttribute("message", msg);
            logger.error(msg);
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
            model.addAttribute("message", msg);
            logger.error(msg);
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
            model.addAttribute("message", msg);
            logger.error(msg);
        } catch (UnknownAccountException e) {
            msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
            model.addAttribute("message", msg);
            logger.error(msg);
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！" + e.getMessage();
            model.addAttribute("message", msg);
            logger.error(msg);
        }
        return "../../index";
    }

    /**
     * 如果登录成功，跳转至首页；登录失败，则将失败信息反馈对用户
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/loginAjax",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse loginAjax(HttpServletRequest request, Model model) {
        //{"code":20000,"data":{"token":"admin"}}
        String msg = "";
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        try
        {
            if(StriUtil.isEmpty(userName)||StriUtil.isEmpty(password))
            {
                BufferedReader br = request.getReader();
                JSONReader reader = new JSONReader(br);

                reader.startObject();
                Map<String,String> paramMap = new HashMap<String,String>();
                while (reader.hasNext())
                {
                    String key = reader.readString();
                    String value = reader.readString();
                    paramMap.put(key,value);
                    
                }
                reader.endObject();
                reader.close();
                userName = paramMap.get("username");
                password = paramMap.get("password");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        
        Subject subject = SecurityUtils.getSubject();
        // 如果当前已有用户被认证
        if (subject.isAuthenticated()) {
            // 使当前用户登出
            subject.logout();
        }

        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(true);

        Map<String,Object> resultData = new HashMap<String,Object>();
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                resultData.put("token","admin");
                ServerResponse serverResponse = ServerResponse.createDefaultResult(ResultCodes.SUCCESS,"success",resultData);
                return serverResponse;
            } else {
                resultData.put("token","admin");
                ServerResponse serverResponse = ServerResponse.createDefaultResult(ResultCodes.SUCCESS,"success",resultData);
                return serverResponse;
            }
        } catch (IncorrectCredentialsException e) {
            msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";
            model.addAttribute("message", msg);
            logger.error(msg);
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
            model.addAttribute("message", msg);
            logger.error(msg);
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
            model.addAttribute("message", msg);
            logger.error(msg);
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
            model.addAttribute("message", msg);
            logger.error(msg);
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
            model.addAttribute("message", msg);
            logger.error(msg);
        } catch (UnknownAccountException e) {
            msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
            model.addAttribute("message", msg);
            logger.error(msg);
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！" + e.getMessage();
            model.addAttribute("message", msg);
            logger.error(msg);
        }
        ServerResponse serverResponse = ServerResponse.createDefaultResult(ResultCodes.NOT_LOGIN_ERROR,msg,null);
        return serverResponse;
    }
    
}
