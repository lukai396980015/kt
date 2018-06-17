package org.jsoupDemo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 *
 */
public class App
{
    public static Elements chapterElements = new Elements();
    
    public static void main(String[] args) throws Exception
    {
        String uri = "https://m.88dus.com/";
        String sy = "mulu/472-1/";
        //获取所有章节列表
        getChapterList(uri,sy);
        
        if(chapterElements.size()>0)
        {
            int flag = 1;
//            Elements liElements = chapterElements;
            for (int i = 0; i < chapterElements.size(); i++)
            {
                Element liElement = chapterElements.get(i);
                //获取所有a标签
                Elements aElements = liElement.getElementsByTag("a");
                for (int j = 0; j < aElements.size(); j++)
                {
                    //获取href节点的值
                    String href = aElements.get(j).attributes().get("href");
                    String hrefTitle = aElements.get(j).html();
                    hrefTitle=hrefTitle.replace("&nbsp;", "");
                    hrefTitle=hrefTitle.replaceAll("<.*>", "");
                    hrefTitle=hrefTitle.replaceAll("</.*>", "");
                    //打开页面并获取页面中的文件内容
                    getPageContent(uri+href,"第"+flag+"章 "+hrefTitle);
                    flag = flag +1;
                }
            }
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
        List<Elements> elements = new ArrayList<Elements>();
        Document doc = Jsoup.connect(uri+path).get();
        Elements aElements = doc.body().getElementsByTag("a");
        Elements liElements = doc.body().getElementsByTag("li");
        chapterElements.addAll(liElements);
        for (int i = 0; i < aElements.size(); i++)
        {
            Element aElement = aElements.get(i);
            String a = aElement.html();
            if(a.contains("下一页"))
            {
                String nextPaht = aElement.attributes().get("href");
                getChapterList(uri,nextPaht);
            }
        }
    }
    
    public static void getPageContent(String url,String chapterName)
        throws Exception
    {
        FileWriter fw = null;
        try
        {
            
            // 打开页面并获取页面中的文件内容
            Document docBookPage = Jsoup.connect(url).get();
            Element element = docBookPage.body().getElementById("nr1");
            element.removeClass("nr_page");
            element.removeClass("my-ad");
            element.removeAttr("_blank");
            element.getElementsByTag("script").remove();
            Elements fontElements = element.getElementsByTag("font");
            for (int i = 0; i < fontElements.size(); i++)
            {
                Element fontElement = fontElements.get(i);
                String font = fontElement.html();
                if(font.contains("m.88dus.com"))
                {
                    fontElement.remove();
                }
            }
            Elements aElements = element.getElementsByTag("a");
            for (int i = 0; i < aElements.size(); i++)
            {
                Element aElement = aElements.get(i);
                String a = aElements.html();
                if(a!=null&&a.contains("www.69zw.com"))
                {
                    aElement.remove();
                }
            }
            String content = element.html();
            File outFile = new File("D:/adesk/tempFile/a.txt");
            fw = new FileWriter(outFile, true);
            content=content.replace("&nbsp;", "");
            content=content.replaceAll("<.*>", "");
            content=content.replaceAll("</.*>", "");
            if(content!=null&&content.trim().length()>0)
            {
                fw.write("\r\n"+chapterName+"\r\n");
                fw.write(content);
            }
//            System.out.println(content);
            fw.flush();
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
