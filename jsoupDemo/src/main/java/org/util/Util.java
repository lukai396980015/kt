package org.util;

import com.alibaba.fastjson.JSONObject;

public class Util
{
    public static boolean isEmpty(JSONObject object,String key)
    {
        return (!object.containsKey(key))||isEmpty(object.getString(key));
    }
    public static boolean isNotEmpty(JSONObject object,String key)
    {
        return !isEmpty(object,key);
    }

    public static boolean isEmpty(String str)
    {
        return str==null||"".equals(str);
    }
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }
}
