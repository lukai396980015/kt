package org.jsoupDemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.util.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookParseImpl implements ParseHtml
{
    public List<Map<String,String>> CHAPTERLIST = new ArrayList<Map<String,String>>();
    public List<Map<String,String>> ERRORCHAPTERLIST = new ArrayList<Map<String,String>>();

    public JSONObject config;

    public BookParseImpl(JSONObject config)
    {
        this.config = config;
    }

    public void parseHtml()
        throws IOException
    {
        String bookname= config.getString("bookname");
        String resultRootPath = config.getString("resultRootPath");
        String errorResultRootPath = config.getString("errorResultRootPath");
        String resultPath = resultRootPath+bookname+".txt";
        String errorResultPath = errorResultRootPath+bookname+"_error.txt";
        String uri = config.getString("uri");
        String sy = config.getString("shouye");
        //获取所有章节列表
        this.getChapterList(uri,sy,config.getBoolean("isUseSY"),config.getInteger("chapterListIndex"));
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
                    getPageContent(value,key,errorResultPath);
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
    public void getChapterList(String uri,String path,boolean isUsesy,int chapterlistIndex) throws IOException
    {

        Document doc = Jsoup.connect(uri+path).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36").get();
        //Elements tdElements = doc.body().getElementsByTag("td");
        //获取章节列表
        //获取章节列表所在的div，id=list
        if(Util.isEmpty(config,"chapterListDiv"))
        {
            System.out.println("获取章节列表失败，没有配置章节列表所在的div域");
            return ;
        }
        Elements div_Elements = doc.body().getElementsByClass(config.getString("chapterListDiv"));
        if(div_Elements==null||div_Elements.size()<=0)
        {
            System.out.println("获取章节列表失败，没有找到章节列表所有再div域");
            return;
        }
        if(Util.isEmpty(config,"chapterListA"))
        {
            System.out.println("获取章节列表失败，没有配置章节列表对应的标签");
            return ;
        }
        Elements a_Elements = null;
        for (Element div_e:div_Elements)
        {
            if(a_Elements==null)
            {
                a_Elements = div_e.getElementsByTag(config.getString("chapterListA"));
            }
            else
            {
                a_Elements.addAll(div_e.getElementsByTag(config.getString("chapterListA")));
            }
        }
        if(a_Elements==null||"".equals(a_Elements))
        {
            System.out.println("error chapterlist dl is null");
            return ;
        }
        int firstDlIndex = 0;
        //不需要前面12个章节，前面12个章节为最近更新
        for (int i=chapterlistIndex;i<a_Elements.size();i++)
        {   Element atag = a_Elements.get(i);
            String href = atag.attr("href");
            String chapterName = atag.ownText();
            Map<String,String> map = new HashMap<String,String>();
            if(isUsesy)
            {
                map.put(chapterName,uri+path+href);
            }
            else if(config.getBoolean("notAdd"))
            {
                map.put(chapterName,href);
            }
            else
            {
                map.put(chapterName,uri+href);
            }
            this.CHAPTERLIST.add(map);
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
            Elements bomElements = null;
            if(Util.isNotEmpty(config,"nextChapterButtonDiv"))
            {
                bomElements = docBookPage.body().getElementsByClass(config.getString("nextChapterButtonDiv"));
            }
            Elements aElements = null;
            if(bomElements!=null)
            {
                if(bomElements.size()>0&&bomElements.get(0)!=null)
                {
                    aElements = bomElements.get(0).getElementsByTag("a");
                }
            }
            if(Util.isEmpty(config,"content"))
            {
                System.out.println("获取章节内容失败，没有配置内容数据节点" );
                return;
            }
            String contentDiv = config.getString("content");
            Element element = null;
            String[] contentDivConfig = contentDiv.split(":");
            if(contentDiv.startsWith("id:"))
            {
                element = docBookPage.body().getElementById(contentDivConfig[1]);
            }
            if(contentDiv.startsWith("class:"))
            {
                try
                {
                    Elements elements = docBookPage.body().getElementsByClass(contentDivConfig[1]);
                    element = elements.get(Integer.parseInt(contentDivConfig[2]));
                }
                catch (Exception e)
                {
                    System.out.println("获取内容所在div失败");
                }
            }
            if(element==null)
            {
                System.out.println("获取内容所在div失败");
            }
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
            if(aElements!=null)
            {
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
            }
            System.out.println("获取章节"+chapterName+"完成");
        }catch (Exception e)
        {
            Map<String,String> chapterError = new HashMap<String,String>();
            chapterError.put(chapterName,url);
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
