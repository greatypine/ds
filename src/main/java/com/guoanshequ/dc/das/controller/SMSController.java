package com.guoanshequ.dc.das.controller;

import com.alibaba.fastjson.JSONObject;
import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.model.SMSSendRecord;
import com.guoanshequ.dc.das.model.SMSService;
import com.guoanshequ.dc.das.service.SMSSendService;
import com.guoanshequ.dc.das.service.SMSServiceService;
import com.guoanshequ.dc.das.utils.HttpClientUtil;
import net.sf.json.JSONArray;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.controller
 * @Description: 短信发送接收
 * @Author: gbl
 * @CreateDate: 2018/11/22 16:22
 */
@RestController
public class SMSController {

    private static final Logger logger = LogManager.getLogger(SMSController.class);

    @Autowired
    SMSServiceService smsServiceService;

    @Autowired
    SMSSendService smsRecordService;

    /**
     * @Description requestBody 携带电话号码发送短信
     * @author gbl
     * @param channel_spell 短信通道名称首字母
     * @date 2018/11/23 15:18
     **/

    @RequestMapping(value = "/rest/sendSMSToPhone/{channel_spell}")
    public String sendSMSToPhone(@PathVariable("channel_spell") String channel_spell, @RequestBody Map<String, Object> param) {

        JSONObject result = new JSONObject();
        try {
            Object smsMobilePhone = param.get("mobilePhone");

            if (smsMobilePhone == null) {
                logger.info("短信手机号是空：" + param.toString());
                System.out.println("短信手机号是空：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.DATA_MOBILEPHONE_NULL.getCode());
                result.put("desc",EnumRespStatus.DATA_MOBILEPHONE_NULL.getMessage());
                return result.toJSONString();
            }

            if (smsMobilePhone.getClass().isArray()) {
                logger.info("手机号参数非数组格式：" + param.toString());
                System.out.println("手机号参数非数组格式：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.DATA_MOBILEPHONE_ERROR.getCode());
                result.put("desc",EnumRespStatus.DATA_MOBILEPHONE_ERROR.getMessage());
                return result.toJSONString();
            }


            JSONArray phoneja = JSONArray.fromObject(smsMobilePhone);
            if (phoneja.size() > 100) {
                logger.info("手机号参数多于100个：" + param.toString());
                System.out.println("手机号参数多于100个：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.DATA_MOBILEPHONE_LIMIT.getCode());
                result.put("desc",EnumRespStatus.DATA_MOBILEPHONE_LIMIT.getMessage());
                return result.toJSONString();
            }

            Object smsContent = param.get("content");//短信内容
            if (smsContent == null||"".equals(smsContent)) {
                logger.info("短信内容是空：" + param.toString());
                System.out.println("短信内容是空：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.DATA_CONTENT_NULL.getCode());
                result.put("desc",EnumRespStatus.DATA_CONTENT_NULL.getMessage());
                return result.toJSONString();
            }


            //根据通道查询短信服务
            SMSService ss = smsServiceService.findSMSServiceByChannelSpell(channel_spell);
            if (ss == null) {
                logger.info("短信服务不存在：" + param.toString());
                System.out.println("短信服务不存在：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.SMSSERVICE_ERROR.getCode());
                result.put("desc",EnumRespStatus.SMSSERVICE_ERROR.getMessage());
                return result.toJSONString();
            }



            String smsContent_gb2312 = URLEncoder.encode(smsContent.toString(), "gb2312");
            Date createTime = new Date();
            for(int i=0;i<phoneja.size();i++){
                String smsurl = ss.getUrl().concat("username=").concat(ss.getUserName()).concat("&password=").concat(ss.getPassWord()).concat("&message=").concat(smsContent_gb2312)
                        .concat("&phone=").concat(phoneja.getString(i)).concat("&epid=").concat(ss.getChannelId()).concat("&linkid=&subcode=");

                //发送短信
                String smsResult = HttpClientUtil.sendGet(smsurl);
                System.out.println("发送短信结果"+smsurl+">>>>>>>>>>>"+smsResult);
                logger.info("发送短信结果："+smsurl+">>>>>>>>>>>"+smsResult);

                //保存短信发送记录
                SMSSendRecord smsRecord = new SMSSendRecord();
//                smsRecord.setAppKey(app_key);
//                smsRecord.setChannelId(channel_id);
//                smsRecord.setCreateTime(createTime);
//                smsRecord.setExeResult(result);
                smsRecord.setMobilePhone(phoneja.getString(i));
                smsRecordService.addSMSRecordService(smsRecord);


            }





        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "fail";
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }

        return "success";
    }
}