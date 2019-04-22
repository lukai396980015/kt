package com.kt.managermode.bean.system;

import java.io.Serializable;
import java.util.Map;

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

    /*返回数据*/
    private Map<String,Object> resultData;
    
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
        return createDefaultResult(errorCode,errorMsg,null);
    }
    public static ServerResponse createDefaultResult(String errorCode,String errorMsg,Map<String,Object> resultData)
    {
        ServerResponse result = new ServerResponse();
        result.setResultCode(errorCode);
        result.setResultMsg(errorMsg);
        result.setResultData(resultData);
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

    public Map<String, Object> getResultData()
    {
        return resultData;
    }

    public void setResultData(Map<String, Object> resultData)
    {
        this.resultData = resultData;
    }
}
