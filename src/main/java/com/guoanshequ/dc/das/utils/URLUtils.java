package com.guoanshequ.dc.das.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by sunning on 2018/9/12.
 */
public class URLUtils {
    public static HttpsURLConnection getHttpsURLConnection(String fileurl) throws IOException {
        String net_url="https://cdn.guoanshuju.com/";
        fileurl=net_url+URLEncoder.encode(fileurl,"utf-8");
        URL realUrl = new URL(fileurl);
        HttpsURLConnection conn = (HttpsURLConnection)realUrl.openConnection();
        //设置通用的请求属性
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent",
                URLEncoder.encode("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)","utf-8"));
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(30*60*1000);
        conn.setReadTimeout(30*60*1000);
        return conn;
    }



}
