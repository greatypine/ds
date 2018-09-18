package com.guoanshequ.dc.das.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by sunning on 2018/9/12.
 */
public class TestOSS {
    public static final String SHEQU = "1-社区信息";
    public static final String LOUFANG = "2-房屋信息(楼房)";
    public static final String SHANGYE = "2-房屋信息(商业楼宇)";
    public static final String PINGFANG = "2-房屋信息(平房)";
    public static final String GUANGCHANG = "2-房屋信息(公园、广场)";
    public static final String OTHER = "2-房屋信息(其它)";
    public static final String HUXING = "3-楼房户型信息";
    public static final String ZHENGLI = "整理表";

    public static void testRead(){
        try{
            List<String> file_names = OSSUploadUtil.queryOssListByPath("daqWeb/house/");
            String url="https://cdn.guoanshuju.com/";
            for(String file_name:file_names) {
                System.out.println(file_name);
                String fileurl=url+URLEncoder.encode(file_name,"utf-8");
                URL realUrl = new URL(fileurl);
                //打开和URL之间的连接
                HttpsURLConnection conn = (HttpsURLConnection)realUrl.openConnection();
                //设置通用的请求属性
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("user-agent",
                        URLEncoder.encode("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)","utf-8"));
                conn.setRequestMethod("GET");
                //发送POST请求必须设置如下两行
                /*conn.setDoOutput(true);
                conn.setDoInput(true);*/
                InputStream inputStream = conn.getInputStream();
                Workbook workbook = WorkbookFactory.create(inputStream);
                Sheet sheet = workbook.getSheet(SHEQU);
                Row row = sheet.getRow(1);
                row.getCell(0);
                System.out.println(row.getCell(0)+"--------"+row.getCell(1));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {
        try{
            List<String> file_names = OSSUploadUtil.queryOssListByPath("daqWeb/house/");
            String url="https://cdn.guoanshuju.com/";
            for(String file_name:file_names) {
                System.out.println(file_name);
               String fileurl=url+file_name;
                URL realUrl = new URL(fileurl);
                //打开和URL之间的连接
                HttpsURLConnection conn = (HttpsURLConnection)realUrl.openConnection();
                //设置通用的请求属性
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("user-agent",
                        URLEncoder.encode("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)","utf-8"));
                //发送POST请求必须设置如下两行
                conn.setDoOutput(true);
                conn.setDoInput(true);
                InputStream inputStream = conn.getInputStream();
                Workbook workbook = WorkbookFactory.create(inputStream);
                Sheet sheet = workbook.getSheet(SHEQU);
                Row row = sheet.getRow(1);
                row.getCell(0);
                System.out.println(row.getCell(0)+"--------"+row.getCell(1));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }




}
