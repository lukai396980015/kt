package org.jsoupDemo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

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
        URL_MAP.put("都市仙尊",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/62572/index.html","都市仙尊"));
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
