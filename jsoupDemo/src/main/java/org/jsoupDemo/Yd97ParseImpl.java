package org.jsoupDemo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lukai
 * @TODO TODO自定义描述
 * @create 2019-06-06 9:43
 */
public class Yd97ParseImpl implements ParseHtml
{
    public List<Map<String,String>> CHAPTERLIST = new ArrayList<Map<String,String>>();
    public List<Map<String,String>> ERRORCHAPTERLIST = new ArrayList<Map<String,String>>();

    public String url;
    public String bookname;
    
    public Yd97ParseImpl(String url,String bookname)
    {
        this.url = url;
        this.bookname = bookname;
    }
    
    public void parseHtml()
        throws IOException
    {
        String resultPath = "E:/adesk/tempFile/"+bookname+".txt";
        String errorResultPath = "E:/adesk/tempFile/"+bookname+"_error.txt";
        String urltemp = url.substring(8,url.length());
        int index = url.indexOf("/",8);
        String uri = url.substring(0,index);
        String sy = url.substring(index,url.length());
        //获取所有章节列表
        this.getChapterList(uri,sy);
        for (int i = 0;i<CHAPTERLIST.size();i++)
        {
            CHAPTERLIST.get(i).forEach((String key, String value) -> {
                try
                {
                    getPageContent(value,key,resultPath);
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
                    getPageContent(uri+sy+value,key,errorResultPath);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
        }
        System.out.println(bookname+"获取完成");
    }

    /**
     * 获取章节列表
     * @author lukai
     * @since 2019/6/6/006 10:35
     * 
     */
    public void getChapterList(String uri,String path) throws IOException
    {

        System.out.println(uri+path);
        Document doc = Jsoup.connect(uri+path).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36").timeout(5000).get();
        //Elements tdElements = doc.body().getElementsByTag("td");
        //获取章节列表
        Element readerlistsDiv = doc.body().getElementById("readerlists");
        Elements ul_Elements  = readerlistsDiv.getElementsByTag("ul");
        Element ul2_Elements = ul_Elements.get(1);
        
        Elements li_Elements = ul2_Elements.getElementsByTag("li");
        for (Element li_Element:li_Elements)
        {
            Elements atags = li_Element.getElementsByTag("a");
            for (Element atag:atags)
            {
                String href = atag.attr("href");
                String chapterName = atag.ownText();
                Map<String,String> map = new HashMap<String,String>();
                map.put(chapterName,uri+href);
                this.CHAPTERLIST.add(map);
            }
        }

    }

    public void getPageContent(String url,String chapterName,String resultPath)
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
            content = content.replaceAll("<p>","");
            content = content.replaceAll("</p>","");
            content = content.replaceAll("&nbsp;","");
            String table = "&lt;/table&gt;";
            int endIndex = content.indexOf(table);
            if (endIndex!=-1)
            {
                content = content.substring(endIndex+table.length());
            }
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
            this.ERRORCHAPTERLIST.add(chapterError);
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
