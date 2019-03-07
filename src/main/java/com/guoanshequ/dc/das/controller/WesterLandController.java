package com.guoanshequ.dc.das.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guoanshequ.dc.das.service.WesterLandService;
import com.guoanshequ.dc.das.utils.HttpClientUtil;
import org.apache.commons.net.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.controller
 * @Description:
 * @Author: gbl
 * @CreateDate: 2019/1/29 15:10
 */
@RestController
@org.springframework.context.annotation.PropertySource("classpath:/config/common.properties")
public class WesterLandController {

    private static final Logger logger = LogManager.getLogger(WesterLandController.class);

    @Autowired
    private WesterLandService westerLandService;

    @Value("${wester.land.aeskey}")
    private String aeskey;
    @Value("${wester.land.md5key}")
    private String md5key;
    @Value("${wester.land.transaction}")
    private String transaction;
    @Value("${wester.land.user}")
    private String user;
    @Value("${wester.land.product}")
    private String product;


    /**
     * @Description 获取保险交易信息
     * @author gbl
     * @date 2019/1/29 15:24
     **/

    @RequestMapping("/rest/getWesterLandTransaction")
    public void getWesterLandTransaction(){

        logger.info("开始获取东方大地保险交易信息");
        String result = null;
        try {
            result = HttpClientUtil.sendPost(transaction,null);
            if(result==null||"".equals(result)){
                logger.info("获取的东方大地保险交易数据返回值异常");

            }else{
                JSONObject jsonObject = JSONObject.parseObject(result);
                Object res = jsonObject.get("res");
                Object code = jsonObject.get("code");
                if(res!=null){
                    Integer resultCode = JSONObject.parseObject(String.valueOf(res)).getInteger("resultCode");
                    if(resultCode==10041&&Integer.parseInt(String.valueOf(code))==20000){

                        JSONObject pageData = jsonObject.getJSONObject("pageData");
                        String dfddSign = pageData.getString("dfddSign");//签名
                        String content = pageData.getString("content");
                        String localSign = getMd5(content);//获取签名

                        if(dfddSign.equals(localSign)){//验证签名
                            String decryptInfo = decryptAES(content);
                            System.out.println(decryptInfo);
                            JSONArray ja = JSONArray.parseArray(decryptInfo);
                            if(ja.size()==0){
                                logger.info("东方大地保险暂时无交易数据");
                            }else{

                                westerLandService.addWesterLandTransaction(ja);

                            }

                        }else{
                            logger.info("获取的东方大地保险交易数据签名不匹配:"+dfddSign+"!="+localSign);
                        }
                    }else{
                        logger.info("获取的东方大地保险交易数据返回："+JSONObject.parseObject(String.valueOf(res)).getString("resultMsg"));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取的东方大地保险交易数据失败："+e.getMessage());
        }


    }


    /**
     * @Description 获取保险产品信息
     * @author gbl
     * @date 2019/1/29 15:24
     **/

    @RequestMapping("/rest/getWesterLandProduct")
    public void getWesterLandProduct(){

        logger.info("开始获取东方大地保险产品信息");
        String result = null;
        try {
            result = HttpClientUtil.sendPost(product,null);
            if(result==null||"".equals(result)){
                logger.info("获取的东方大地保险产品数据返回值异常");

            }else{
                JSONObject jsonObject = JSONObject.parseObject(result);
                Object res = jsonObject.get("res");
                Object code = jsonObject.get("code");
                if(res!=null){
                    Integer resultCode = JSONObject.parseObject(String.valueOf(res)).getInteger("resultCode");
                    if(resultCode==10041&&Integer.parseInt(String.valueOf(code))==20000){

                        JSONObject pageData = jsonObject.getJSONObject("pageData");
                        String dfddSign = pageData.getString("dfddSign");//签名
                        String content = pageData.getString("content");
                        String localSign = getMd5(content);//获取签名

                        if(dfddSign.equals(localSign)){//验证签名
                            String decryptInfo = decryptAES(content);
                            System.out.println(decryptInfo);
                            JSONArray ja = JSONArray.parseArray(decryptInfo);
                            if(ja.size()==0){
                                logger.info("东方大地保险暂时无产品数据");
                            }else{

                                westerLandService.addProduct(ja);

                            }
                        }else{
                            logger.info("获取的东方大地保险产品数据签名不匹配:"+dfddSign+"!="+localSign);
                        }
                    }else{
                        logger.info("获取的东方大地保险产品数据返回："+JSONObject.parseObject(String.valueOf(res)).getString("resultMsg"));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取的东方大地保险产品数据失败:"+e.getMessage());
        }


    }


    /**
     * @Description 获取保险用户
     * @author gbl
     * @date 2019/1/29 15:24
     **/

    @RequestMapping("/rest/getWesterLandUser")
    public void getWesterLandUser(){


        logger.info("开始获取东方大地保险用户信息");
        String result = null;
        try {
            result = HttpClientUtil.sendPost(user,null);
            if(result==null||"".equals(result)){
                logger.info("获取的东方大地保险用户数据返回值异常");

            }else{
                JSONObject jsonObject = JSONObject.parseObject(result);
                Object res = jsonObject.get("res");
                Object code = jsonObject.get("code");
                if(res!=null){
                    Integer resultCode = JSONObject.parseObject(String.valueOf(res)).getInteger("resultCode");
                    if(resultCode==10041&&Integer.parseInt(String.valueOf(code))==20000){

                        JSONObject pageData = jsonObject.getJSONObject("pageData");
                        String dfddSign = pageData.getString("dfddSign");//签名
                        String content = pageData.getString("content");
                        String localSign = getMd5(content);//获取签名

                        if(dfddSign.equals(localSign)){//验证签名
                            String decryptInfo = decryptAES(content);
                            System.out.println(decryptInfo);
                            JSONArray ja = JSONArray.parseArray(decryptInfo);
                            if(ja.size()==0){
                                logger.info("东方大地保险暂时无用户数据");
                            }else{

                                westerLandService.addUser(ja);

                            }
                        }else{
                            logger.info("获取的东方大地保险用户数据签名不匹配:"+dfddSign+"!="+localSign);
                        }
                    }else{
                        logger.info("获取的东方大地保险用户数据返回："+JSONObject.parseObject(String.valueOf(res)).getString("resultMsg"));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取的东方大地保险用户数据签名失败:"+e.getMessage());
        }


    }

    /**
     * @Description 获取验证签名
     * @author gbl
     * @date 2019/1/29 16:06
     **/

    public String getMd5(String content ) {
        try {

            String mdContent = content+md5key;
            return Base64.encodeBase64String(MessageDigest.getInstance("MD5").digest(mdContent.getBytes("utf-8"))).replaceAll("\r", "").replaceAll("\n","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @Description 解密数据
     * @author gbl
     * @date 2019/1/29 16:08
     **/

    public  String decryptAES(String content) {
        try {

            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(aeskey.getBytes());
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(Base64.decodeBase64(content));
            return new String(encrypted, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
