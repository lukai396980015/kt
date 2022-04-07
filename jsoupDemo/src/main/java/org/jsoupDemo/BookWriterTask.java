package org.jsoupDemo;

import org.util.FileUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class BookWriterTask implements Callable {

    String content;
    String path;
    public BookWriterTask(String content,String path)
    {
        this.content = content;
        this.path = path;
    }
    @Override
    public Object call() throws Exception {
        try {
            FileUtil.write(path,true,content);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
