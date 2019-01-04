package com.kt.managermode.controller.system;

import com.alibaba.fastjson.JSON;
import com.kt.managermode.bean.system.ServerResponse;
import com.kt.managermode.util.ResultCodes;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author lukai
 * 如果请求的是loginUrl，则调用AuthenticatingFilter.executeLogin()
 * @create 2019-01-04 17:56
 */
public class AuthenticationFilter extends FormAuthenticationFilter
{
    Logger logger = Logger.getLogger(AuthenticationFilter.class);
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
        throws Exception
    {
        if (this.isLoginRequest(request, response))
        {
            if (this.isLoginSubmission(request, response))
            {
                if (logger.isTraceEnabled())
                {
                    logger.trace("Login submission detected.  Attempting to execute login.");
                }

                return this.executeLogin(request, response);
            }
            else
            {
                if (logger.isTraceEnabled())
                {
                    logger.trace("Login page view.");
                }
                return true;
            }
        }
        else
        {
            if (logger.isTraceEnabled())
            {
                logger.trace(
                    "Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" +
                        this.getLoginUrl() + "]");
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();

            ServerResponse serverResponse = ServerResponse.createDefaultResult(ResultCodes.NOT_LOGIN_ERROR,"is not login");
            String result = JSON.toJSONString(serverResponse);
            //需要返回的json
            out.println(result);
            out.flush();
            out.close();
            return false;
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
        ServletResponse response)
        throws Exception
    {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try
        {
            out = response.getWriter();
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        /*
        HttpSession session = ((HttpServletRequest)request).getSession();
        User user = userMapper.selectByUserName(token.getPrincipal().toString());
        session.setAttribute(Const.CURRENT_USER, user);
        */
        ServerResponse serverResponse = ServerResponse.createDefaultResult(ResultCodes.SUCCESS,"login success");
        String result = JSON.toJSONString(serverResponse);
        out.println(result);
        out.flush();
        out.close();
        return true;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
        ServletResponse response)
    {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try
        {
            out = response.getWriter();
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        ServerResponse serverResponse = ServerResponse.createDefaultResult(ResultCodes.login_failure_error,"login is Failure");
        String result = JSON.toJSONString(serverResponse);
        out.println(result);
        out.flush();
        out.close();
        return false;
    }
}

