package org.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil
{
    /**
     * 根据路径获取输入流
     * @author lukai
     * @since 2019/4/24/024 9:45
     * 
     */
    public static BufferedReader getFileSpecificBufferedReader(String filename,String charsetName) throws IOException
    {
        if(charsetName==null)
        {
            charsetName = "UTF-8";
        }
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), charsetName));
        return br;
    }
    /**
     * 根据文件获取输入流
     * @author lukai
     * @since 2019/4/24/024 9:45
     * 
     */
    public static BufferedReader getFileSpecificBufferedReader(File file,String charsetName) throws IOException
    {
        if(charsetName==null)
        {
            charsetName = "UTF-8";
        }
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
        return br;
    }

    /**
     * 根据路径获取文件类容
     * @author lukai
     * @since 2019/4/24/024 9:45
     * 
     */
    public static String getContents(String filename,String charsetName)
    {
        String str = "";
        try
        {
            BufferedReader br = getFileSpecificBufferedReader(filename,charsetName);
            str = getContents(br);
            if(br!=null)
            {
                br.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return str;
    }
    
    /**
     * 根据文件获取文件内容
     * @author lukai
     * @since 2019/4/24/024 9:46
     * 
     */
    public static String getContents(File file,String charsetName)
    {
        String str = "";
        try
        {
            BufferedReader br = getFileSpecificBufferedReader(file,charsetName);
            str = getContents(br);
            if(br!=null)
            {
                br.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return str;
    }
    
    public static List<String> getContentsArray(String filename,String charsetName)
    {
        List<String> str = null;
        try
        {
            str = getContentsArray(getFileSpecificBufferedReader(filename,charsetName));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return str;
    }

    public static String getContents(BufferedReader br) throws IOException
    {
        if (br == null)
        {
            return null;
        }

        String fileContents = "";
        String line = br.readLine();
        while (line != null)
        {
            fileContents += line + "\n";
            line = br.readLine();
        }
        br.close();
        return fileContents;
    }
    /**
     * 
     * 将文件内容读成list
     *
     * @author Administrator
     * @param br
     * @return
     * @throws IOException
     */
    public static List<String> getContentsArray(BufferedReader br) throws IOException
    {
        if (br == null)
        {
            return null;
        }
        List<String> result = new ArrayList<String>();
        String line = br.readLine();
        while (line != null)
        {
            result.add(line);
            line = br.readLine();
        }
        br.close();
        return result;
    }
    /**
     * 
     * 输出文件到指定文件
     *
     * @author Administrator
     * @param path  输出文件路径
     * @param flag  是否添加到文件结尾
     * @param msg  内容
     * @throws Exception
     */
    public static void write(String path,boolean flag,String msg) throws Exception
    {
        if (path == null)
        {
            return;
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(path,flag));
        bw.write(msg);
        bw.flush();
        bw.close();
    }
}
