package org.jsoupDemo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FanqianxsParseImpl implements ParseHtml
{
    public List<Map<String,String>> CHAPTERLIST = new ArrayList<Map<String,String>>();
    public List<Map<String,String>> ERRORCHAPTERLIST = new ArrayList<Map<String,String>>();

    public String url;
    public String bookname;

    public FanqianxsParseImpl(String url,String bookname)
    {
        this.url = url;
        this.bookname = bookname;
    }

    public void parseHtml()
        throws IOException
    {
        String resultPath = "E:/adesk/tempFile/"+bookname+".txt";
        String errorResultPath = "E:/adesk/tempFile/"+bookname+"_error.txt";
        int index = url.indexOf("/",8);
        String uri = url.substring(0,index+1);
        String sy = url.substring(index+1,url.length());
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

        Document doc = Jsoup.connect(uri+path).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36").get();
        //Elements tdElements = doc.body().getElementsByTag("td");
        //获取章节列表
        //获取章节列表所在的div，id=list
        Element div_Elements = doc.body().getElementById("list");
        Elements li_Elements = div_Elements.getElementsByTag("dl");
        if(li_Elements==null||"".equals(li_Elements))
        {
            System.out.println("error chapterlist dl is null");
            return ;
        }
        int firstDlIndex = 0;
        for (Element dl_Element:li_Elements)
        {
            Elements atags = dl_Element.getElementsByTag("a");
            //不需要前面12个章节，前面12个章节为最近更新
            for (int i=15;i<atags.size();i++)
            {   Element atag = atags.get(i);
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
            Elements bomElements = docBookPage.body().getElementsByClass("bottem2");
            Elements aElements = null;
            if(bomElements!=null)
            {
                if(bomElements.get(0)!=null)
                {
                    aElements = bomElements.get(0).getElementsByTag("a");
                }
            }
            Element element = docBookPage.body().getElementById("content");
            element.getElementsByTag("div").remove();
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

            for (Element ahref :aElements)
            {
                String aStr = ahref.text();
                if("下一页".equals(aStr==null?"":aStr.trim()))
                {
                    String ahrefUrl = ahref.attr("href");
                    String nextUrl = url.substring(0,url.lastIndexOf("/"))+ahrefUrl.substring(ahrefUrl.lastIndexOf("/"),ahrefUrl.length());
                    getPageContent(nextUrl,"",resultPath);
                }
            }
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
