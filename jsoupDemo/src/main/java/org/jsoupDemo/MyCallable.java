package org.jsoupDemo;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.util.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class MyCallable implements Callable {

    ChapterInfo chapterInfo;

    public MyCallable(ChapterInfo chapterInfo)
    {
        this.chapterInfo = chapterInfo;
    }

    @Override
    public Object call() throws Exception {
        System.out.println(chapterInfo.getChapterName()+"---------------------------章节名称");
        //加载章节内容
        chapterInfo.buildChapterContent();
        //获取章节类容
        StringBuffer content = chapterInfo.getChapterContent();
        return content.toString();
    }
}
