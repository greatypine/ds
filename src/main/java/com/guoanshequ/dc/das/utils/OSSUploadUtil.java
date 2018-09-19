package com.guoanshequ.dc.das.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by sunning on 2018/9/10.
 */
public class OSSUploadUtil {
    private static final Logger logger = LogManager.getLogger(OSSUploadUtil.class);
    private static OSSConfig config = null;

    public static String uploadFile(File file,String fileType,String remotedir){
        config = config==null?new OSSConfig():config;
        config.setPicLocation(remotedir);
        String fileName = config.getPicLocation()+UUID.randomUUID().toString().toUpperCase().replace("-", "")+"."+fileType;
        return putObject(file,fileType,fileName);
    }


    public static String uploadFileResName(File file,String fileType,String remotedir){
        config = config==null?new OSSConfig():config;
        config.setPicLocation(remotedir);
        String fileName = config.getPicLocation()+file.getName();
        return putObject(file,fileType,fileName);
    }

    public static String updateFile(File file,String fileType,String oldUrl){
        String fileName = getFileName(oldUrl);
        if(fileName==null) return null;
        return putObject(file,fileType,fileName);
    }

   /* public static String replaceFile(File file,String fileType,String oldUrl){
        boolean flag = deleteFile(oldUrl);
        if(!flag){
        }
        return uploadFile(file, fileType);
    }  */

    public static boolean deleteFile(String fileUrl){
        config = config==null?new OSSConfig():config;

        String bucketName = OSSUploadUtil.getBucketName(fileUrl);
        String fileName = OSSUploadUtil.getFileName(fileUrl);
        if(bucketName==null||fileName==null) return false;
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            GenericRequest request = new DeleteObjectsRequest(bucketName).withKey(fileName);
            ossClient.deleteObject(request);
        } catch (Exception oe) {
            oe.printStackTrace();
            return false;
        } finally {
            ossClient.shutdown();
        }
        return true;
    }
    public static int deleteFile(List<String> fileUrls){
        int deleteCount = 0;
        String bucketName = OSSUploadUtil.getBucketName(fileUrls.get(0));
        List<String> fileNames = OSSUploadUtil.getFileName(fileUrls);
        if(bucketName==null||fileNames.size()<=0) return 0;
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName).withKeys(fileNames);
            DeleteObjectsResult result = ossClient.deleteObjects(request);
            deleteCount = result.getDeletedObjects().size();
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return deleteCount;

    }
    public static int deleteFiles(List<String> fileUrls){
        int count = 0;
        for (String url : fileUrls) {
            if(deleteFile(url)){
                count++;
            }
        }
        return count;
    }

    private static String putObject(File file,String fileType,String fileName){
        config = config==null?new OSSConfig():config;
        String url = null;
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            InputStream input = new FileInputStream(file);
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(OSSUploadUtil.contentType(fileType));
            meta.setCacheControl("no-cache");
            PutObjectRequest request = new PutObjectRequest(config.getBucketName(), fileName,input,meta);
            ossClient.putObject(request);
            url = config.getEndpoint().replaceFirst("http://","http://"+config.getBucketName()+".")+"/"+fileName;
        } catch (OSSException oe) {
            oe.printStackTrace();
            return null;
        } catch (ClientException ce) {
            ce.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            ossClient.shutdown();
        }
        return url;
    }

    private static String contentType(String fileType){
        fileType = fileType.toLowerCase();
        String contentType = "";

        if(fileType== "bmp"){
            contentType = "image/bmp";
        }
        else if(fileType== "gif"){
            contentType = "image/gif";
        }else if(fileType== "png"){
            contentType = "image/jpeg";
        }else if(fileType== "jpeg"){
            contentType = "image/jpeg";
        }else if(fileType== "jpg"){
            contentType = "image/jpeg";
        }else if(fileType== "html"){
            contentType = "text/html";
        }else if(fileType== "txt"){
            contentType = "text/plain";
        }else if(fileType== "vsd"){
            contentType = "application/vnd.visio";
        }else if(fileType== "ppt"){
            contentType = "application/vnd.ms-powerpoint";
        }else if(fileType== "pptx"){
            contentType = "application/vnd.ms-powerpoint";
        }else if(fileType== "doc"){
            contentType = "application/msword";
        }else if(fileType== "docx"){
            contentType = "application/msword";
        }else if(fileType== "xml"){
            contentType = "text/xml";
        }else if(fileType== "mp4"){
            contentType = "video/mp4";
        }else{
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    private static String getBucketName(String fileUrl){
        String http = "http://";
        String https = "https://";
        int httpIndex = fileUrl.indexOf(http);
        int httpsIndex = fileUrl.indexOf(https);
        int startIndex  = 0;
        if(httpIndex==-1){
            if(httpsIndex==-1){
                return null;
            }else{
                startIndex = httpsIndex+https.length();
            }
        }else{
            startIndex = httpIndex+http.length();
        }
        int endIndex = fileUrl.indexOf(".oss-");
        return fileUrl.substring(startIndex, endIndex);
    }

    private static String getFileName(String fileUrl){
        String str = "aliyuncs.com/";
        int beginIndex = fileUrl.indexOf(str);
        if(beginIndex==-1) return null;
        return fileUrl.substring(beginIndex+str.length());
    }

    private static List<String> getFileName(List<String> fileUrls){
        List<String> names = new ArrayList<String>();
        for (String url : fileUrls) {
            names.add(getFileName(url));
        }
        return names;
    }

    public static String buildClientUrl(String rt){
        String fileUrl = rt.replace("oss-cn-beijing.aliyuncs.com", "https://cdn.guoanshuju.com");
        return fileUrl;
    }

    //方法会重新生成文件名 随机码
    public static String uploadOssFile(File f,String suffix,String urlLocation) {
        String rt = uploadFile(f, suffix , urlLocation);
        return buildClientUrl(rt);
    }

    //方法不会重新生成文件名
    public static String uploadOssFileResName(File f,String suffix,String urlLocation) {
        String rt = uploadFileResName(f, suffix , urlLocation);
        return buildClientUrl(rt);
    }



    public static List<String> queryOssListByPath(String rootPath) {
        List<String> result = new ArrayList<String>();
        if(rootPath.indexOf("house")>-1&&rootPath.indexOf("house_type")==-1) {
            config = config==null?new OSSConfig():config;
            OSSClient ossClient =null;
            try {
                logger.info("开始请求OSS服务");
                ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
                logger.info("获取OSSClient成功");
                // 构造ListObjectsRequest请求
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest("guoanshuju");
                logger.info("构造ListObjectsRequest请求");
            // "/" 为文件夹的分隔符
            listObjectsRequest.setDelimiter("/");
            // 列出fun目录下的所有文件和文件夹
            listObjectsRequest.setPrefix(rootPath);
            ObjectListing listing = ossClient.listObjects(listObjectsRequest);
                logger.info("列出fun目录下的所有文件和文件夹："+listing);
            // 遍历所有Object
            for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
                if(objectSummary.getKey()!=null&&!objectSummary.getKey().equals(rootPath)) {
                    result.add(objectSummary.getKey());
                }
            }
            return result;
            } catch (OSSException oe) {
                logger.info("OSSException："+oe.getMessage());
                oe.printStackTrace();
                return result;
            } catch (ClientException ce) {
                logger.info("ClientException："+ce.getMessage());
                return result;
            }catch (Exception e) {
                logger.info("OtherException："+e.getMessage());
                return result;
            }finally {
                ossClient.shutdown();
            }
        }
        return result;
    }



    /**
     * 根据文件路径 删除的方法
     * @param url  daqWeb/house/xxxxxxx.xls
     */
    public static void deleteObjectByUrl(String url) {
        if(url.indexOf("house")>-1&&url.indexOf("house_type")==-1) {
            // 初始化OSSClient
            config = config==null?new OSSConfig():config;
            OSSClient ossClient =null;
            try {
                logger.info("开始请求OSS服务");
                ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            // 删除Object
            if(url.indexOf(".")>-1) {
                ossClient.deleteObject("guoanshuju", url);
            }
            } catch (OSSException oe) {
                logger.info("OSSException："+oe.getMessage());
                oe.printStackTrace();
            } catch (ClientException ce) {
                logger.info("ClientException："+ce.getMessage());
            }catch (Exception e) {
                logger.info("OtherException："+e.getMessage());
            }finally {
                ossClient.shutdown();
            }

        }
    }



}
