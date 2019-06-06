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
        URL_MAP.put("bxwx",new BxwxParse("https://www.bxwx.la/b/274/274556/","都市仙尊"));
        //斗魔传承
        URL_MAP.put("piaotianzw",new PiaotianzwParseImpl("https://www.piaotianzw.com/book/76549/index.html","斗魔传承"));
    }
    
    public static void main(String[] args) throws Exception
    {
        ParseHtml bp = URL_MAP.get("piaotianzw");
        bp.parseHtml();
        
    }
       
    
    
}
