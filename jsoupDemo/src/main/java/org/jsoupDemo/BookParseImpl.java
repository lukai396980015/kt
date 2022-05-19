package org.jsoupDemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.concurrent.Task;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoupDemo.impl.BookInfoImpl;
import org.util.Util;
import sun.nio.ch.ThreadPool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class BookParseImpl implements ParseHtml
{
    //public List<Map<String,String>> CHAPTERLIST = new ArrayList<Map<String,String>>();
    public List<Map<String,String>> ERRORCHAPTERLIST = new ArrayList<Map<String,String>>();

    public static JSONObject config;

    public BookParseImpl(JSONObject config)
    {
        this.config = config;
    }

    final int THREAD_NUM = 20;
    public ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_NUM);
    public ExecutorService writerThreadPool = Executors.newSingleThreadExecutor();


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
        String chapterlistPage = config.getString("shouyeName");

        BookInfo bookInfo = new BookInfoImpl();
        //初始化图书信息
        boolean bookloadsuccess = bookInfo.initBook(config);
        if(!bookloadsuccess)
        {
            System.out.println("图书配置文件加载失败，返回");
            return;
        }
        //加载图书章节列表
        bookInfo.buildChapterList();

        //this.getChapterList(uri,sy,config.getBoolean("isUseSY"),config.getInteger("chapterListIndex"));
        //获取图书章节列表
        List<ChapterInfo> chapterList = bookInfo.getChapterList();
        if(chapterList==null||chapterList.size()==0)
        {
            return;
        }
        int chapterCount = chapterList.size();
        //获取所有章节列表
        //每次跳过THREAD_NUM个循环
        for (int i = 0;i<chapterCount;i=i+THREAD_NUM)
        {
            int lastIndex = chapterCount>(i+THREAD_NUM)?(i+THREAD_NUM):chapterCount;
            List<ChapterInfo> subList = chapterList.subList(i,lastIndex);
            List array = new ArrayList();
            System.out.println("循环sublist"+subList);
            for(ChapterInfo sub :subList)
            {
                System.out.println("循环key"+sub);
                try
                {
                    Callable callable = new MyCallable(sub);
                    array.add(callable);
                    //threadPool.submit(callable);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            try {
                List<Future<String>> result = threadPool.invokeAll(array);
                for(int x=0;x<result.size();x++)
                {
                    //获取到所有结果后才继续请求剩余章节，如果直接把Future放到线程 会导致请求过快刷死对方服务器
                    String content = result.get(x).get();
                    BookWriterTask bwt = new BookWriterTask(content,resultPath);
                    //写数据使用单线程池，保证所有处理依次执行避免章节错乱
                    writerThreadPool.submit(bwt);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //所有需要提交的任务已经提交给线程池，停止线程池（使线程池不在接收信息的任务，但是还是会继续完成已经排队中的线程）
        threadPool.shutdown();
        writerThreadPool.shutdown();
        //等待线程执行结束或者超时，超时时间默认设置1小时
        System.out.println(bookname+"获取完成");
    }

    /**
     * 获取章节列表
     * @author lukai
     * @since 2019/6/6/006 10:35
     *
     */
    /*
    public void getChapterList(String uri,String path,boolean isUsesy,int chapterlistIndex) throws IOException
    {
        String chapterlistPage = config.getString("shouyeName");
        Document doc = null;
        try {

            doc = Jsoup.connect(uri+path+chapterlistPage).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36").get();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(doc==null)
        {
            System.out.println("获取章节列表失败,url="+uri+path+chapterlistPage);
            return ;

        }
        //Elements tdElements = doc.body().getElementsByTag("td");
        //获取章节列表
        //获取章节列表所在的div，id=list
        if(Util.isEmpty(config,"chapterListDiv"))
        {
            System.out.println("获取章节列表失败，没有配置章节列表所在的div域");
            return ;
        }
        String chapterListConfig = config.getString("chapterListDiv");
        String[] chapterListConfigArray = chapterListConfig.split(":");
        Element div_Element = null;
        if(chapterListConfig.contains("id:"))
        {
            div_Element = doc.body().getElementById(chapterListConfigArray[1]);
        }
        else
        {
            Elements div_Elements = doc.body().getElementsByClass(chapterListConfigArray[1]);
            div_Element = div_Elements.get(Integer.parseInt(chapterListConfigArray[2]));
        }
        //Elements div_Elements = doc.body().getElementsByClass(config.getString("chapterListDiv"));
        if(div_Element==null)
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
        if(a_Elements==null)
        {
            a_Elements = div_Element.getElementsByTag(config.getString("chapterListA"));
        }
        else
        {
            a_Elements.addAll(div_Element.getElementsByTag(config.getString("chapterListA")));
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

     */
    /*
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
                    if(Util.isNotEmpty(config,"nextChapterButtonText"))
                    {
                        String nextChapterButtonText = config.getString("nextChapterButtonText");
                        if(nextChapterButtonText.equals(aStr==null?"":aStr.trim()))
                        {
                            String ahrefUrl = ahref.attr("href");
                            String nextUrl = url.substring(0,url.lastIndexOf("/"))+ahrefUrl.substring(ahrefUrl.lastIndexOf("/"),ahrefUrl.length());
                            getPageContent(nextUrl,"",resultPath);
                        }
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
    */

}
