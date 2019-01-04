package com.kt.managermode.bean.system;

import java.io.Serializable;

/**
 * @author lukai
 * @TODO TODO自定义描述
 * @create 2019-01-04 18:04
 */
public class ServerResponse implements Serializable
{
    /*结果码*/
    private String resultCode;
    
    /*结果提示信息*/
    private String resultMsg;

    public String getResultCode()
    {
        return resultCode;
    }
    /**
     * 创建默认的错误提示
     * @author lukai
     * @since 2019/1/4/004 18:43
     * 
     */
    public static ServerResponse createDefaultResult(String errorCode,String errorMsg)
    {
        ServerResponse result = new ServerResponse();
        result.setResultCode(errorCode);
        result.setResultMsg(errorMsg);
        return result;
    }
    public void setResultCode(String resultCode)
    {
        this.resultCode = resultCode;
    }

    public String getResultMsg()
    {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg)
    {
        this.resultMsg = resultMsg;
    }
}
