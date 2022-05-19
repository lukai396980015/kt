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

public abstract class ChapterInfo {
    /**
     * 章节名称
     */
    private String chapterName;
    /**
     * 章节内容
     */
    private StringBuffer chapterContent = new StringBuffer("");
    /**
     * 下一章按钮处理
     */
    private String nextChapterButton;
    /**
     * 下一章按钮标记字段
     */
    private String nextChapterButtonText;
    /**
     * 获取章节的url
     */
    private String chapterUrl;
    /**
     * 章节内容元素标记信息
     */
    private String chapterContentElement;
    /**
     * 下一页的url
     */
    private String nextPageUrl;

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public StringBuffer getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(StringBuffer chapterContent) {
        this.chapterContent = chapterContent;
    }

    public String getChapterUrl() {
        return chapterUrl;
    }

    public void setChapterUrl(String chapterUrl) {
        this.chapterUrl = chapterUrl;
    }

    public String getNextChapterButton() {
        return nextChapterButton;
    }

    public void setNextChapterButton(String nextChapterButton) {
        this.nextChapterButton = nextChapterButton;
    }

    public String getNextChapterButtonText() {
        return nextChapterButtonText;
    }

    public void setNextChapterButtonText(String nextChapterButtonText) {
        this.nextChapterButtonText = nextChapterButtonText;
    }

    public String getChapterContentElement() {
        return chapterContentElement;
    }

    public void setChapterContentElement(String chapterContentElement) {
        this.chapterContentElement = chapterContentElement;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public boolean initChapterInfo(JSONObject config)
    {
        boolean result = true;
        if(Util.isNotEmpty(config,"nextChapterButton"))
        {
            this.nextChapterButton = config.getString("nextChapterButton");
        }
        //内容标签必须有
        if(Util.isEmpty(config,"content"))
        {
            System.out.println("获取章节内容失败，没有配置内容数据节点" );
            result = false;
            return result;
        }else
        {
            this.chapterContentElement = config.getString("content");
            if(Util.isEmpty(chapterContentElement))
            {
                System.out.println("获取章节内容失败，没有配置内容数据节点" );
                result = false;
                return result;
            }
        }
        if(Util.isNotEmpty(config,"nextChapterButtonText"))
        {
            this.nextChapterButtonText = config.getString("nextChapterButtonText");
        }
        return result;
    }
    /**
     * 构建章节内容信息
     */
    public void buildChapterContent()
    {
        // 打开页面并获取页面中的文件内容
        Document docBookPage = null;
        String requestChapterUrl = this.getChapterUrl();
        //下一页非空时请求下一页信息
        if(Util.isNotEmpty(this.getNextPageUrl()))
        {
            requestChapterUrl = this.getNextPageUrl();
        }else
        {
            chapterContent.append("\r\n"+chapterName+"\r\n");
        }
        for(int i=0;i<4;i++)
        {
            try
            {
                docBookPage = Jsoup.connect(requestChapterUrl).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36").get();
                break;
            }
            catch (IOException e)
            {
                if(i==3)
                {
                    System.out.println("error:"+requestChapterUrl );
                }
            }
        }

        try
        {
            Element bomElement = null;
            if(Util.isNotEmpty(this.nextChapterButton))
            {
                String[] nextChapterButtonConfigArray = this.nextChapterButton.split(":");
                if(this.nextChapterButton.contains("id:"))
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
            Element element = null;
            String[] contentDivConfig = this.chapterContentElement.split(":");
            if(this.chapterContentElement.startsWith("id:"))
            {
                element = docBookPage.body().getElementById(contentDivConfig[1]);
            }
            if(this.chapterContentElement.startsWith("class:"))
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
            //移除无用的标签，文本等
            Util.htmlElementPurify(element);
            String content = element.html();
            content = Util.htmlTextPurify(content);
            //File outFile = new File(resultPath);
            //sb = new FileWriter(outFile, true);
            if(content!=null&&content.trim().length()>0)
            {
                chapterContent.append(content+"\r\n");
            }
            //如果页面上一章分成多页展示，用来处理下一页的信息
            if(aElements!=null)
            {
                for (Element ahref :aElements)
                {
                    String aStr = ahref.text();
                    if(Util.isNotEmpty(this.nextChapterButtonText))
                    {
                        if(this.nextChapterButtonText.equals(aStr==null?"":aStr.trim()))
                        {
                            String ahrefUrl = ahref.attr("href");
                            String nextUrl = requestChapterUrl.substring(0,requestChapterUrl.lastIndexOf("/"))+ahrefUrl.substring(ahrefUrl.lastIndexOf("/"),ahrefUrl.length());
                            this.nextPageUrl=nextUrl;
                            buildChapterContent();
                        }
                    }

                }
            }
            System.out.println("获取章节"+chapterName+"完成");
        }catch (Exception e)
        {
            Map<String,String> chapterError = new HashMap<String,String>();
            chapterError.put(chapterName,requestChapterUrl);
            System.out.println(requestChapterUrl);
        }
        finally
        {
        }
    }

}
