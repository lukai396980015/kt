package org.jsoupDemo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author lukai
 * @TODO TODO自定义描述
 * @create 2019-04-24 9:18
 */
public class ParseCHMToTxt
{
    //一级路径
    private static String path = "E:\\Program Files (x86)\\CHM Editor\\test\\00.-《网络玄幻小说合集》精品精选集v2.1.0\\";
    private static String outputPath = "E:\\小说\\";
    
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String systeminpath = scanner.nextLine();
        
        while (systeminpath==null)
        {
            System.out.println("请输入正确的目录");
            systeminpath = scanner.nextLine();
        }
        path = systeminpath+"\\";
        String bookFilePath = path+"bbb.htm";
        //获取图书列表
        List<String> hrefPahtList = getBookList(bookFilePath);
        //循环获取章节列表
        for (String bookHrefPaht:hrefPahtList)
        {
            String tmp = path.substring(0,path.length()-1);
            tmp = tmp.substring(tmp.lastIndexOf("\\")+1,tmp.length());
            String bookFile = outputPath+tmp+"\\"+bookHrefPaht.substring(bookHrefPaht.indexOf(path)+path.length(),bookHrefPaht.lastIndexOf("/"))+".txt";
            File file = new File(bookFile.substring(0,bookFile.lastIndexOf("/")>0?bookFile.lastIndexOf("/"):bookFile.lastIndexOf("\\")));
            file.mkdirs();
            //二级路径
            String secondPath = bookHrefPaht.substring(0,bookHrefPaht.lastIndexOf("/"));
            List<String> chapterList = getChapterlist(bookHrefPaht);
            for (String chapterPath:chapterList)
            {
                String chapterContent = getChapterContent(secondPath+"/"+chapterPath);
                try
                {
                    //System.out.println(bookFile);
                    FileUtil.write(bookFile,true,chapterContent);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        
    }
    /**
     * 获取章节内容
     * @author lukai
     * @since 2019/4/25/025 10:19
     * 
     */
    public static String getChapterContent(String chapterFilePath)
    {
        StringBuffer sb = new StringBuffer();
        //chapterList
        List<String> chapterPathList = new ArrayList<String>();
        Document chapterFileDocument = getDocumentByFilePath(chapterFilePath);
        //章节名称
        Elements elements = chapterFileDocument.getElementsByTag("b");
        //章节名称
        for (Element bElement:elements)
        {
            Elements fontElements = bElement.getElementsByTag("font");
            if(fontElements!=null&&fontElements.size()!=0)
            {
                String chapterName=fontElements.get(0).ownText();
                sb.append(chapterName+"\r\n");
            }
        }
        
        Elements pElements = chapterFileDocument.getElementsByTag("p");
        for (Element element:pElements)
        {
            if(element.outerHtml().contains("HTMLBUILERPART0"))
            {
                sb.append(element.ownText()+"\r\n");
            }
        }
        //章节内容
        return sb.toString();
    }
    
    /**
     * TODO 添加类的描述
     * @author lukai
     * @param  bookPath 图书文件路径
     * @since 2019/4/25/025 9:23
     * 
     */
    public static List<String> getChapterlist(String bookPath)
    {
        List<String> chapterPathList = new ArrayList<String>();
        Document bookFile = getDocumentByFilePath(bookPath);
        Elements elements = bookFile.getElementsByTag("a");
        for (Element element:elements)
        {
            String hrefPath = element.attr("href");
            if(hrefPath.contains("/")||chapterPathList.contains(hrefPath)||hrefPath.contains("#"))
            {
                continue;
            }
            
            chapterPathList.add(hrefPath);
        }
        return chapterPathList;
    }
    
    /**
     * 获取图书列表
     * @author lukai
     * @param  filePath 图书列表文件路径
     * @since 2019/4/25/025 9:12
     * 
     */
    public static List<String> getBookList(String filePath)
    {
        Document bookFile = getDocumentByFilePath(filePath);
        Elements elements = bookFile.getElementsByTag("a");
        List<String> hrefPaht = new ArrayList<String>();
        for (Element element:elements)
        {
            String hrefPath = element.attr("href");
            if(hrefPath.contains("/")||hrefPath.contains("#"))
            {
                hrefPaht.add(path+hrefPath);
            }
        }
        return hrefPaht;
    }
    
    /**
     * 查看是否有table子节点
     * @author lukai
     * @since 2019/4/24/024 15:16
     * 
     */
    public static Document getDocumentByFilePath(String filePath)
    {
        File file = new File(filePath);
        String htmlStr = FileUtil.getContents(file,"gb2312");
        Document html = Jsoup.parse(htmlStr);
        return html;
    }
}
