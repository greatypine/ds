package com.guoanshequ.dc.das.controller;

import com.alibaba.fastjson.JSONObject;
import com.guoanshequ.dc.das.utils.HttpClientUtil;
import com.guoanshequ.dc.das.utils.SignUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.controller
 * @Description: 公积金
 * @Author: gbl
 * @CreateDate: 2018/12/24 16:14
 */
@RestController
@org.springframework.context.annotation.PropertySource("classpath:/config/common.properties")
public class ProvidentFundController {

    private static final Logger logger = LogManager.getLogger(ProvidentFundController.class);


    @Value("${provident.fund.domain}")
    private String domain;

    @Value("${provident.fund.cityifno}")
    private String cityInfo;

    @Value("${provident.fund.token}")
    private String token;

    @Value("${provident.fund.orderSn}")
    private String orderSn;

    @Value("${provident.fund.verificationCode}")
    private String verificationCode;

    @Value("${provident.fund.queryParam}")
    private String queryParam;

    @Value("${provident.fund.data}")
    private String data;

    @Value("${provident.fund.appkey}")
    private String appkey;

    @Value("${provident.fund.appsecret}")
    private String appsecret;

    /**
     * @Description 获取公积金城市配置信息
     * @author gbl
     * @date 2018/12/20 14:31
     **/

    @RequestMapping(value = "/getCityInfo")
    public String getCityInfo(){
        String result = HttpClientUtil.sendGet(domain+cityInfo);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer code = jsonObject.getInteger("code");
        String msg = jsonObject.getString("msg");

        return result;
    }


    /**
     * @Description 获取token
     * @author gbl
     * @date 2018/12/20 16:45
     **/

    @RequestMapping(value = "/getToken")
    public String getToken(){
        JSONObject param = new JSONObject();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("appKey",appkey);
        map.put("timestamp",timestamp);
        String sign = SignUtil.generateSign(map,appsecret);
        param.put("params",map);
        param.put("sign",sign);
        String url = domain+token;
        String result = HttpClientUtil.sendPost(url,param);
        return result;
    }

    /**
     * @Description 获取订单号
     * @author gbl
     * @date 2018/12/21 17:57
     **/

    @RequestMapping(value = "/getOrderSn")
    public String getOrderSn(@RequestBody Map<String,Object> param){
        Object token = param.get("token");
        Object cityId = param.get("cityId");
        JSONObject jsonObject = new JSONObject();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("appKey",appkey);
        map1.put("timestamp",timestamp);
        map1.put("token",String.valueOf(token));
        String sign = SignUtil.generateSign(map1,appsecret);
        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("params",map1);
        map2.put("sign",sign);

        jsonObject.put("signParams",map2);
        jsonObject.put("cityId",cityId);
        System.out.println(jsonObject.toJSONString());
        String url = domain+orderSn;
        String result = HttpClientUtil.sendPost(url,jsonObject);
        return result;
    }

    /**
     * @Description 获取验证码
     * @author gbl
     * @date 2018/12/21 16:56
     **/

    @RequestMapping(value = "/getVerificationCode")
    public String getVerificationCode(@RequestBody Map<String,Object> param){
        Object orderSn = param.get("orderSn");
        Object code = param.get("code");
        JSONObject jsonObject = new JSONObject();
        String timestamp = String.valueOf(System.currentTimeMillis());

        jsonObject.put("orderSn",String.valueOf(orderSn));
        jsonObject.put("type",String.valueOf(code));
        System.out.println(jsonObject.toJSONString());
        String url = domain+verificationCode;
        String result = HttpClientUtil.sendPost(url,jsonObject);
        return result;
    }

    /**
     * @Description 提交请求参数
     * @author gbl
     * @date 2018/12/21 17:57
     **/

    @RequestMapping(value = "/submitQueryParam")
    public String submitQueryParam(@RequestBody Map<String,Object> param){


        String url = domain+queryParam;
        String result = HttpClientUtil.sendPost(url,param);
        return result;
    }

    /**
     * @Description 查询任务结果
     * @author gbl
     * @date 2018/12/21 18:01
     **/

    @RequestMapping(value = "/getData")
    public String getData(@RequestBody Map<String,Object> param){
        Object token = param.get("token");
        Object orderSn = param.get("orderSn");
        JSONObject jsonObject = new JSONObject();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("appKey",appkey);
        map1.put("timestamp",timestamp);
        map1.put("token",String.valueOf(token));
        map1.put("orderSn",String.valueOf(orderSn));
        String sign = SignUtil.generateSign(map1,appsecret);

        jsonObject.put("params",map1);
        jsonObject.put("sign",sign);


        System.out.println(jsonObject.toJSONString());
        String url = domain+data;
        String result = HttpClientUtil.sendPost(url,jsonObject);
        return result;
    }

    /**
     * @Description 接收结果
     * @author gbl
     * @date 2018/12/21 16:30
     **/

    @RequestMapping(value = "/reciveData")
    @ResponseBody
    public String saveOrderSn(@RequestBody Map<String, Object> param){

        System.out.println("接收结果>>>"+param);
        logger.info("接收结果>>>"+param);
        JSONObject result = new JSONObject();
        result.put("code",0);
        result.put("msg","success");
        return result.toJSONString();
    }

}
