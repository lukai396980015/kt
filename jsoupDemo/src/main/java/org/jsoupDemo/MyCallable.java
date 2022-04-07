package org.jsoupDemo;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.util.Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class MyCallable implements Callable {

    String value;
    String key;
    public JSONObject config;


    public MyCallable(String value,String key,JSONObject config)
    {
        this.value=value;
        this.key=key;
        this.config = config;
    }

    @Override
    public Object call() throws Exception {
        String contentStr = getPageContent(value,key);
        return contentStr;
    }

    public String getPageContent(String url,String chapterName)
            throws IOException

    {
        StringBuilder sb = new StringBuilder("");
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
            Element bomElement = null;
            if(Util.isNotEmpty(config,"nextChapterButton"))
            {
                String nextChapterButtonConfig = config.getString("nextChapterButton");
                String[] nextChapterButtonConfigArray = nextChapterButtonConfig.split(":");
                if(nextChapterButtonConfig.contains("id:"))
                {
                    bomElement = docBookPage.body().getElementById(nextChapterButtonConfigArray[1]);
                }
                else
                {
                    Elements elements = docBookPage.body().getElementsByClass(nextChapterButtonConfigArray[1]);
                    bomElement = elements.get(Integer.parseInt(nextChapterButtonConfigArray[2]));
                }
            }
            Elements aElements = null;
            if(bomElement!=null)
            {
                aElements = bomElement.getElementsByTag("a");
            }
            if(Util.isEmpty(config,"content"))
            {
                System.out.println("获取章节内容失败，没有配置内容数据节点" );
                return null;
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
            //File outFile = new File(resultPath);
            //sb = new FileWriter(outFile, true);
            if(content!=null&&content.trim().length()>0)
            {
                sb.append("\r\n"+chapterName+"\r\n");
                sb.append(content);
            }
            if(aElements!=null)
            {
                for (Element ahref :aElements)
                {
                    String aStr = ahref.text();
                    if(Util.isNotEmpty(config,"nextChapterButtonText"))
                    {
                        String nextChapterButtonText = config.getString("nextChapterButtonText");
                        if(nextChapterButtonText.equals(aStr==null?"":aStr.trim()))
                        {
                            String ahrefUrl = ahref.attr("href");
                            String nextUrl = url.substring(0,url.lastIndexOf("/"))+ahrefUrl.substring(ahrefUrl.lastIndexOf("/"),ahrefUrl.length());
                            String contentStr = getPageContent(nextUrl,"");
                            sb.append(contentStr);
                        }
                    }

                }
            }
            System.out.println("获取章节"+chapterName+"完成");
        }catch (Exception e)
        {
            Map<String,String> chapterError = new HashMap<String,String>();
            chapterError.put(chapterName,url);
            System.out.println(url);
        }
        finally
        {
        }
        return sb.toString();
    }
}
