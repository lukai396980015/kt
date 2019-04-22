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
    public static List<Map<String,String>> CHAPTERLIST = new ArrayList<Map<String,String>>();
    public static List<Map<String,String>> ERRORCHAPTERLIST = new ArrayList<Map<String,String>>();
    
    public static void main(String[] args) throws Exception
    {
        String resultPath = "E:/adesk/tempFile/都市仙尊.txt";
        String errorResultPath = "E:/adesk/tempFile/都市仙尊_error.txt";
        //https://www.bxwx.la/b/274/274556/1457276.html
        String uri = "https://www.bxwx.la/";
        String sy = "b/274/274556/";
        //获取所有章节列表
        getChapterList(uri,sy);
        for (int i = 0;i<CHAPTERLIST.size();i++)
        {
            CHAPTERLIST.get(i).forEach((String key, String value) -> {
                try
                {
                    getPageContent(uri+value,key,resultPath);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
        }
        int errorChapterSize = ERRORCHAPTERLIST.size();
        for (int i = 0;i<ERRORCHAPTERLIST.size();i++)
        {
            ERRORCHAPTERLIST.get(i).forEach((String key, String value) -> {
                try
                {
                    getPageContent(uri+value,key,errorResultPath);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
        }
        
//        System.out.println(aElements.html());
        
//        Element liElement = liElements.get(0);
//        //获取所有a标签
//        Elements aElements = liElement.getElementsByTag("a");
//        String href = aElements.get(0).attributes().get("href");
//        //打开页面并获取页面中的文件内容
//        getPageContent(uri+href);
        
        
//        System.out.println(elements.html());
        
        
//        Document doc = Jsoup.connect("网址/")
//            .data("query", "Java") // 请求参数
//            .userAgent("I’mjsoup") // 设置User-Agent
//            .cookie("auth", "token") // 设置cookie
//            .timeout(3000) // 设置连接超时时间
//            .post(); // 使用POST方法访问URL
    }
       
    public static void getChapterList(String uri,String path) throws IOException
    {
        
        Document doc = Jsoup.connect(uri+path).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36").get();
        Elements ddElements = doc.body().getElementsByTag("dd");
        Elements dtElements = doc.body().getElementsByTag("dt");
        int firstDlIndex = 0;
        for (Element dtElement:dtElements)
        {
            String dtOwnTxt = dtElement.ownText();
            if(dtOwnTxt!=null&&dtOwnTxt.contains("正文"))
            {
                firstDlIndex = doc.body().getAllElements().indexOf(dtElement);
            }
        }
        Iterator ddElementsIt =  ddElements.iterator();
        while (ddElementsIt.hasNext())
        {
            Element ddElement = (Element)ddElementsIt.next();
            int ddIndex = doc.body().getAllElements().indexOf(ddElement);
            
            if(ddIndex>firstDlIndex)
            {
                Elements aElements = ddElement.getElementsByTag("a");
                Map<String,String> chapterinfoMap = new HashMap<String,String>();
                chapterinfoMap.put(aElements.first().ownText(),aElements.first().attr("href"));
                CHAPTERLIST.add(chapterinfoMap);
                
            }
        }
        
    }
    
    public static void getPageContent(String url,String chapterName,String resultPath)
        throws IOException

    {
        FileWriter fw = null;
        // 打开页面并获取页面中的文件内容
        Document docBookPage = null;
        for(int i=0;i<4;i++)
        {
            try
            {
                docBookPage = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36").get();
                break;
            }
            catch (IOException e)
            {
                if(i==3)
                {
                    System.out.println("error:"+url );
                }
            }
        }
        
        try
        {
            
            Element element = docBookPage.body().getElementById("content");
            element.getElementsByTag("script").remove();
            String content = element.html();
            content = content.replaceAll("<br>","");
            content = content.replaceAll("<br/>","");
            content = content.replaceAll("&nbsp;","");
            File outFile = new File(resultPath);
            fw = new FileWriter(outFile, true);
            if(content!=null&&content.trim().length()>0)
            {
                fw.write("\r\n"+chapterName+"\r\n");
                fw.write(content);
            }
            fw.flush();
        }catch (Exception e)
        {
            Map<String,String> chapterError = new HashMap<String,String>();
            chapterError.put("chapterName",url);
            ERRORCHAPTERLIST.add(chapterError);
            System.out.println(url);
        }
        finally
        {
            if(fw!=null)
            {
                fw.close();
            }
        }
    }
    
}
