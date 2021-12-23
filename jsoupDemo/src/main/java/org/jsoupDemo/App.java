package org.jsoupDemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.util.FileUtil;
import org.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App
{
    public static final Map<String,ParseHtml> URL_MAP = new HashMap<String,ParseHtml>();
    
    static
    {
        //都市仙尊
        //URL_MAP.put("bxwx",new BxwxParse("https://www.bxwx.la/b/274/274556/","都市仙尊"));
        //斗魔传承
        //URL_MAP.put("piaotianzw",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/76549/index.html","斗魔传承"));
        //URL_MAP.put("最强医圣",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/2285/index.html","最强医圣"));
        //URL_MAP.put("都市最强仙尊",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/75132/index.html","都市最强仙尊"));
        //URL_MAP.put("都市逍遥仙尊",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/56966/index.html","都市逍遥仙尊"));
        //URL_MAP.put("超级鉴宝师",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/5685/index.html","超级鉴宝师"));
        //URL_MAP.put("麻衣相士",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/75996/index.html","麻衣相士"));
        //URL_MAP.put("都市仙尊",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/62572/index.html","都市仙尊"));
        //URL_MAP.put("疯狂炼妖系统",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/72418/index.html","疯狂炼妖系统"));
        //URL_MAP.put("真龙",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/72433/index.html","真龙"));
        //URL_MAP.put("北斗帝尊",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/51253/index.html","北斗帝尊"));
        //URL_MAP.put("修罗帝尊",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/68805/index.html","修罗帝尊"));
        //URL_MAP.put("绝世药神",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/17942/index.html","绝世药神"));
        //URL_MAP.put("都市圣医",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/48447/index.html","都市圣医"));
        //URL_MAP.put("天道驱魔师",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/41527/index.html","天道驱魔师"));
        //URL_MAP.put("我的老婆是狐狸",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/77083/index.html","我的老婆是狐狸"));
        //URL_MAP.put("超级吞噬系统",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/36278/index.html","超级吞噬系统"));
        //URL_MAP.put("陆压道君异界游",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/60214/index.html","陆压道君异界游"));
        //URL_MAP.put("异世厨神",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/53718/index.html","异世厨神"));
        //URL_MAP.put("绝世符神",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/41665/index.html","绝世符神"));
        //URL_MAP.put("逆仙",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/70183/index.html","逆仙"));
        //URL_MAP.put("史上最强炼气期",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/75990/index.html","史上最强炼气期"));
        //URL_MAP.put("召唤千军",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/38525/index.html","召唤千军"));
        //URL_MAP.put("异世灵武天下",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/37085/index.html","异世灵武天下"));
        //URL_MAP.put("最强修真弃少",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/77368/index.html","最强修真弃少"));
        //URL_MAP.put("剑神纵横异界",new PiaotianzwParseImpl("https://www.piaotianzw.com/book_38947/","剑神纵横异界"));
        //URL_MAP.put("全能装逼系统",new Yd97ParseImpl("https://www.97yd.com/menu/651.html","全能装逼系统"));
        //URL_MAP.put("养兽十万亿，我的女妖荡平了诸天",new NcjyParseImpl("http://www.ncjy.net/bxwx/10834/","养兽十万亿，我的女妖荡平了诸天"));
        //URL_MAP.put("新店开业：神兽神器，亿点点多",new Yyq7ParseImpl("https://www.7yyq.com/news/73493/","新店开业：神兽神器，亿点点多"));
        JSONArray jsonArray = JSON.parseArray(FileUtil.getContents(App.class.getResource("/").getPath()+File.separator+"config.json","UTF-8"));
        for(int i=0;i<jsonArray.size();i++)
        {
            JSONObject jsonObject = (JSONObject)jsonArray.get(i);
            if(Util.isNotEmpty(jsonObject,"bookname"))
            {
                URL_MAP.put(jsonObject.getString("bookname"),new BookParseImpl(jsonObject));
            }
        }
        //URL_MAP.put("我养的狗什么时候无敌了",new FanqianxsParseImpl("http://www.fanqianxs.com/book/woyangdegoushenmeshihouwudile/","我养的狗什么时候无敌了"));
        //URL_MAP.put("回到地球当神棍",new LstxtParseImpl("https://www.lstxt.cc/ebook/39808.html","回到地球当神棍"));
    }
    
    public static void main(String[] args) throws Exception
    {
        for (Map.Entry<String,ParseHtml> entry : URL_MAP.entrySet())
        {
            Runnable runnable = new Runnable()
            {
                @Override
                public void run()
                {
                    ParseHtml bp = entry.getValue();
                    try
                    {
                        bp.parseHtml();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            new Thread(runnable).start();
        }
        
    }
       
    
    
}
