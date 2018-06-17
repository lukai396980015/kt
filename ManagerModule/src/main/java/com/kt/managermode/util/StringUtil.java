package com.kt.managermode.util;

public class StringUtil
{
    /**
     * 
     * 判断对象是否为空
     *
     * @author 鲁凯
     * @param str
     * @return
     */
    public static boolean isEmpty(Object str)
    {
        if(str==null||"".equals(str))
        {
            return true;
        }else
        {
            return false;
        }
    }
    /**
     * 
     * 判断对象是否非空
     *
     * @author 鲁凯
     * @param str
     * @return
     */
    public static boolean isNotEmpty(Object str)
    {
        return !isEmpty(str);
    }
}
