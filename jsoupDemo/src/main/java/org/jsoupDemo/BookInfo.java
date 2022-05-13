package org.jsoupDemo;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图书对象接口
 */
public abstract class BookInfo {
    /**
     * 图书名称
     */
    private String bookName;

    /**
     * 章节列表uri，ip+端口
     */
    private String uri;
    /**
     * 工程名和路径
     */
    private String path;
    /**
     * 具体页面名称章节列表
     */
    private String chapterlistPage;

    /**
     * 图书章节列表所在的div
     */
    private String chapterListDiv;

    /**
     * 图书章节列表所在的标签
     */
    private String chapterListA;

    /**
     * 起始index
     */
    private int chapterlistIndex;
    /**
     * 是否添加路径
     */
    private boolean notAdd;
    /**
     * 是否添加首页路径
     */
    private boolean isUsesy;

    /**
     * 章节信息列表
     */
    private List<Map<String,String>> chapterList = new ArrayList<Map<String,String>>();

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getChapterlistPage() {
        return chapterlistPage;
    }

    public void setChapterlistPage(String chapterlistPage) {
        this.chapterlistPage = chapterlistPage;
    }

    public String getChapterListDiv() {
        return chapterListDiv;
    }

    public void setChapterListDiv(String chapterListDiv) {
        this.chapterListDiv = chapterListDiv;
    }

    public String getChapterListA() {
        return chapterListA;
    }

    public void setChapterListA(String chapterListA) {
        this.chapterListA = chapterListA;
    }

    public int getChapterlistIndex() {
        return chapterlistIndex;
    }

    public void setChapterlistIndex(int chapterlistIndex) {
        this.chapterlistIndex = chapterlistIndex;
    }

    public void setNotAdd(boolean notAdd) {
        this.notAdd = notAdd;
    }

    public boolean isNotAdd() {
        return notAdd;
    }

    public boolean isUsesy() {
        return isUsesy;
    }

    public void setUsesy(boolean usesy) {
        isUsesy = usesy;
    }

    /**
     * 图书主页列表，用来获取章节列表
     */
    public String bookChapterListUrl;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public List<Map<String, String>> getChapterList() {
        return chapterList;
    }
    /*
    public void setChapterList(List<Map<String, ChapterInfo>> chapterList) {
        this.chapterList = chapterList;
    }
    */
    public String getBookChapterListUrl() {
        return bookChapterListUrl = this.uri+this.path+this.chapterlistPage;
    }

    public void setBookChapterListUrl(String bookChapterListUrl) {
        this.bookChapterListUrl = bookChapterListUrl;
    }

    /**
     * 构建图书章节列表信息
     */
    public void buildChapterList()
    {
        String chapterlistPage = this.getChapterlistPage();
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
        String chapterListConfig = this.getChapterListDiv();
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
        Elements a_Elements = null;
        if(a_Elements==null)
        {
            a_Elements = div_Element.getElementsByTag(this.getChapterListA());
        }
        else
        {
            a_Elements.addAll(div_Element.getElementsByTag(this.getChapterListA()));
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
            if(this.isUsesy())
            {
                map.put(chapterName,uri+path+href);
            }
            else if(this.isNotAdd())
            {
                map.put(chapterName,href);
            }
            else
            {
                map.put(chapterName,uri+href);
            }

            this.chapterList.add(map);
        }
    }

    /**
     * 默认使用json格式配置文件初始化图书基本属性，uri，path等
     * @param config
     * @return 加载成功返回true，否则返回false
     */
    public boolean initBook(JSONObject config)
    {
        boolean result = false;
        if(config.containsKey("bookname"))
        {
            System.out.println("没有适配到配置文件中的bookname");
            result = true;
            return result;
        }
        if(config.containsKey("chapterListA"))
        {
            System.out.println("没有适配到配置文件中的chapterListA");
            result = true;
            return result;
        }
        if(config.containsKey("chapterListDiv"))
        {
            System.out.println("没有适配到配置文件中的chapterListDiv");
            result = true;
            return result;
        }
        if(config.containsKey("uri"))
        {
            System.out.println("没有适配到配置文件中的uri");
            result = true;
            return result;
        }
        if(config.containsKey("chapterlistIndex"))
        {
            System.out.println("没有适配到配置文件中的chapterlistIndex");
            result = true;
            return result;
        }
        this.bookName = config.getString("bookname");
        this.path = config.getString("shouye");
        this.chapterListA = config.getString("chapterListA");
        this.chapterListDiv = config.getString("chapterListDiv");
        this.uri = config.getString("uri");
        this.chapterlistIndex = config.getInteger("chapterListIndex");
        this.chapterlistPage = config.getString("shouyeName");
        this.notAdd = config.getBoolean("notAdd");
        this.isUsesy = config.getBoolean("isUseSY");
        return result;
    }

}
