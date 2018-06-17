package com.kt.managermode.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kt.managermode.service.IDemoService;

@Controller
@RequestMapping("/Demo")
public class DemoAction
{
    @Autowired
    private IDemoService demoServiceImpl;
    
    @RequestMapping("/queryDemo")
    public String queryDemo(HttpServletRequest request, Model model)
    {
        demoServiceImpl.queryDemo();
        return "demo";
    }
}
