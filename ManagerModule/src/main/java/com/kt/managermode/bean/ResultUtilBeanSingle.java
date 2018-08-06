package com.kt.managermode.bean;

public class ResultUtilBeanSingle<T> {


    private String code;

    private String message;

    private T result;

    /**
     *
     * @param code   结果码
     * @param message 提示消息
     * @param result   结果集
     * @return
     */
    public static ResultUtilBeanSingle getResult(String code,String message,Object result)
    {
        ResultUtilBeanSingle resultUtilBeanSing = new ResultUtilBeanSingle();
        resultUtilBeanSing.code=code;
        resultUtilBeanSing.message=message;
        resultUtilBeanSing.result=result;
        return resultUtilBeanSing;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
