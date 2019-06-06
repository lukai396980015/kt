package org.jsoupDemo;

import sun.net.www.http.HttpClient;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author lukai
 * @TODO TODO自定义描述
 * @create 2019-01-14 10:46
 */
public class ClientIe
{
    public static void main(String[] args) throws Exception
    {
        URL url = new URL("https://www.bxwx.la/b/274/274556/1457276.html");
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.addRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        urlConnection.connect();
        InputStream is = urlConnection.getInputStream();
        System.out.println(is);
    }
}
