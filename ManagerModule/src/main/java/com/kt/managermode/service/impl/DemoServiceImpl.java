package com.kt.managermode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.managermode.bean.DemoBean;
import com.kt.managermode.dao.IDemoDao;
import com.kt.managermode.service.IDemoService;

@Service("DemoService")
public class DemoServiceImpl implements IDemoService
{
    @Autowired
    private IDemoDao demoDao;
    
    public void queryDemo()
    {
        List<DemoBean> demoList = demoDao.queryDemo();
        for (int i = 0; i < demoList.size(); i++)
        {
            System.out.println(demoList.get(i).getDemoId()+":"+demoList.get(i).getDemoName()+":"+demoList.get(i).getDemoHigh());
        }
    }
    
}
