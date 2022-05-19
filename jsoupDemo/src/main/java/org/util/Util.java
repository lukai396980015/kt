package org.util;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.nodes.Element;

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

    /**
     * html元素净化，去掉多余的div，script等信息
     * @param contentElement
     * @return
     */
    public static void htmlElementPurify(Element contentElement)
    {
        contentElement.getElementsByTag("div").remove();
        contentElement.getElementsByTag("script").remove();
    }
    /**
     * html文本净化，去掉多余的换行，段落等信息
     * @param content
     * @return
     */
    public static String htmlTextPurify(String content)
    {
        content = content.replaceAll("<br>","");
        content = content.replaceAll("<br/>","");
        content = content.replaceAll("<p>","");
        content = content.replaceAll("</p>","");
        content = content.replaceAll("&nbsp;","");
        content = content.replaceAll("&lt;/table&gt;","");
        return content;
    }
}
