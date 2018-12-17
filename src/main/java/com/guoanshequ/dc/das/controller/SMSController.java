package com.guoanshequ.dc.das.controller;


import com.guoanshequ.dc.das.dao.master.SMSPhoneMapper;
import com.guoanshequ.dc.das.domain.EnumRespStatus;
import com.guoanshequ.dc.das.domain.ExecutorProcessPool;

import com.guoanshequ.dc.das.domain.SMSSendTask;
import com.guoanshequ.dc.das.dto.SMS;
import com.guoanshequ.dc.das.model.SMSPhone;
import com.guoanshequ.dc.das.model.SMSPhoneGroup;
import com.guoanshequ.dc.das.model.SMSSendRecord;
import com.guoanshequ.dc.das.model.SMSService;
import com.guoanshequ.dc.das.service.SMSPhoneGroupService;
import com.guoanshequ.dc.das.service.SMSPhoneService;
import com.guoanshequ.dc.das.service.SMSSendService;
import com.guoanshequ.dc.das.service.SMSServiceService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.RejectedExecutionException;


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
    SMSSendService smsSendService;

    @Autowired
    SMSPhoneMapper smsPhoneMapper;

    @Autowired
    SMSPhoneService smsPhoneService;

    @Autowired
    SMSPhoneGroupService smsPhoneGroupService;





    /**
     * @Description requestBody 通过电话号码发送短信
     * @author gbl
     * @param channel_spell 短信通道名称首字母
     * @date 2018/11/23 15:18
     **/

    @RequestMapping(value = "/rest/sendSMSToPhone/{channel_spell}")
    public String sendSMSToPhone(@PathVariable("channel_spell") String channel_spell, @RequestBody Map<String, Object> param) {

        JSONObject result = new JSONObject();
        JSONArray smsResultJA = new JSONArray();

        try {

            Object smsMobilePhone  = param.get("mobilePhone");
            if (smsMobilePhone == null) {

                logger.info("手机号参数不存在：" + param.toString());
                System.out.println("手机号参数缺少：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.DATA_MOBILEPHONE_NO_EXIST.getCode());
                result.put("desc",EnumRespStatus.DATA_MOBILEPHONE_NO_EXIST.getMessage());
                result.put("result",null);
                return result.toString();
            }

            if (smsMobilePhone.getClass().isArray()) {
                logger.info("手机号参数非数组格式：" + param.toString());
                System.out.println("手机号参数非数组格式：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.DATA_MOBILEPHONE_ERROR.getCode());
                result.put("desc",EnumRespStatus.DATA_MOBILEPHONE_ERROR.getMessage());
                result.put("result",null);
                return result.toString();
            }


            JSONArray phoneja = JSONArray.fromObject(smsMobilePhone);
            if (phoneja.size() > 100) {
                logger.info("手机号多于100个：" + param.toString());
                System.out.println("手机号参数多于100个：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.DATA_MOBILEPHONE_LIMIT.getCode());
                result.put("desc",EnumRespStatus.DATA_MOBILEPHONE_LIMIT.getMessage());
                result.put("result",null);
                return result.toString();
            }

            if (phoneja.size()==0) {
                logger.info("手机号为空：" + param.toString());
                System.out.println("手机号为空：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.DATA_MOBILEPHONE_NULL.getCode());
                result.put("desc",EnumRespStatus.DATA_MOBILEPHONE_NULL.getMessage());
                result.put("result",null);
                return result.toString();
            }

            Object smsContent = param.get("content");//短信内容
            if (smsContent == null||"".equals(smsContent)) {
                logger.info("短信内容是空：" + param.toString());
                System.out.println("短信内容是空：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.DATA_CONTENT_NULL.getCode());
                result.put("desc",EnumRespStatus.DATA_CONTENT_NULL.getMessage());
                result.put("result",null);
                return result.toString();
            }


            //根据通道查询短信服务
            SMSService ss = smsServiceService.findSMSServiceByChannelSpell(channel_spell);
            if (ss == null) {
                logger.info("短信服务不存在：" + param.toString());
                System.out.println("短信服务不存在：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.SMSSERVICE_ERROR.getCode());
                result.put("desc",EnumRespStatus.SMSSERVICE_ERROR.getMessage());
                result.put("result",null);
                return result.toString();
            }

            Object appKey = param.get("app_key");
            String serialNo =String.valueOf(System.currentTimeMillis());//流水号
            //保存发送短信请求
            SMSSendRecord ssr  = new SMSSendRecord();
            ssr.setAppKey(appKey.toString());
            ssr.setChannelId(ss.getChannelId());
            ssr.setSerialNo(serialNo);
            ssr.setSmsType(1);
            ssr.setContent(String.valueOf(smsContent));
            ssr.setCreateTime(new Date());
            smsSendService.addSMSSendRecordService(ssr);


            //发送短信
            for(int i=0;i<phoneja.size();i++){
                SMS sms = new SMS();
                sms.setMobilePhone(phoneja.get(i)==null?"":phoneja.getString(i));
                sms.setSerialNo(serialNo);
                sms.setSmsType(1);
                sms.setAppKey(String.valueOf(appKey));
                sms.setUrl(ss.getUrl());
                sms.setUserName(ss.getUserName());
                sms.setPassWord(ss.getPassWord());
                sms.setChannelId(ss.getChannelId());
                sms.setContent(String.valueOf(smsContent));
                JSONObject sendResult = smsSendService.sendSMS(sms);
                smsResultJA.add(sendResult);
            }

            result.put("status","success");
            result.put("code",EnumRespStatus.SMS_SEND_OK.getCode());
            result.put("desc",EnumRespStatus.SMS_SEND_OK.getMessage());
            result.put("serialNo",serialNo);
            result.put("result",smsResultJA);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("短信发送请求失败"+param.toString()+">>>by:"+e.getMessage());
            logger.info("短信发送请求失败"+param.toString()+">>>by:"+e.getMessage());
            result.put("status","fail");
            result.put("code",EnumRespStatus.SMS_SEND_ERROR.getCode());
            result.put("desc",EnumRespStatus.SMS_SEND_ERROR.getMessage());
            result.put("result",null);
            return result.toString();
        }

        return result.toString();
    }


    /**
     * @Description  通过电话群组发送短信
     * @author gbl
     * @date 2018/12/7 13:45
     **/

    @RequestMapping(value = "/rest/sendSMSToGroup/{channel_spell}")
    public String  sendSMSToGroup(@PathVariable("channel_spell") String channel_spell, @RequestBody Map<String, Object> param){

        JSONObject result = new JSONObject();
        try {
            Object groupCode = param.get("groupCode");
            if(groupCode==null){
                logger.info("电话群组编号缺少：" + param.toString());
                System.out.println("电话群组编号缺少：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.DATA_PHONE_GROUPCODE__NO_EXIST.getCode());
                result.put("desc",EnumRespStatus.DATA_PHONE_GROUPCODE__NO_EXIST.getMessage());
                result.put("result",null);
                return result.toString();
            }

            List<SMSPhone> phoneList = smsPhoneMapper.querySMSPhone(String.valueOf(groupCode));

            if(phoneList==null||phoneList.size()==0){
                logger.info("电话群组没有电话：" + param.toString());
                System.out.println("电话群组没有编号：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.SMS_GROUP_ERROR.getCode());
                result.put("desc",EnumRespStatus.SMS_GROUP_ERROR.getMessage());
                result.put("result",null);
                return result.toString();
            }


            //根据通道查询短信服务
            SMSService ss = smsServiceService.findSMSServiceByChannelSpell(channel_spell);
            if (ss == null) {
                logger.info("短信服务不存在：" + param.toString());
                System.out.println("短信服务不存在 ：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.SMSSERVICE_ERROR.getCode());
                result.put("desc",EnumRespStatus.SMSSERVICE_ERROR.getMessage());
                result.put("result",null);
                return result.toString();
            }





            Object smsContent = param.get("content");//短信内容
            if (smsContent == null||"".equals(smsContent)) {
                logger.info("短信内容是空：" + param.toString());
                System.out.println("短信内容是空 ：" + param.toString());
                result.put("status","fail");
                result.put("code",EnumRespStatus.DATA_CONTENT_NULL.getCode());
                result.put("desc",EnumRespStatus.DATA_CONTENT_NULL.getMessage());
                result.put("result",null);
                return result.toString();
            }

            Object appKey = param.get("app_key");
            String serialNo =String.valueOf(System.currentTimeMillis());//流水号
            //保存发送短信请求获取OSSClient成功
            SMSSendRecord ssr = new SMSSendRecord();
            ssr.setSerialNo(serialNo);
            ssr.setGroupCode(String.valueOf(groupCode));
            ssr.setAppKey(String.valueOf(appKey));
            ssr.setChannelId(ss.getChannelId());
            ssr.setCreateTime(new Date());
            ssr.setContent(String.valueOf(smsContent));
            ssr.setSmsType(0);
            smsSendService.addSMSSendRecordService(ssr);

            //短信内容
            SMS sms = new SMS();
            sms.setGroupCode(String.valueOf(groupCode));
            sms.setSerialNo(serialNo);
            sms.setSmsType(0);
            sms.setAppKey(String.valueOf(appKey));
            sms.setUrl(ss.getUrl());
            sms.setUserName(ss.getUserName());
            sms.setPassWord(ss.getPassWord());
            sms.setChannelId(ss.getChannelId());
            sms.setContent(String.valueOf(smsContent));

            ExecutorProcessPool executorProcessPool = ExecutorProcessPool.getInstance();
            String taskName = "短信任务线程别名-"+serialNo+"---";
            executorProcessPool.execute(new SMSSendTask(taskName,sms,smsSendService,smsPhoneMapper));
            logger.info("线程池对象：" +executorProcessPool.hashCode());
            logger.info("短信正在发送：" + param.toString());
            System.out.println("短信正在发送 ：" + param.toString());
            result.put("status","success");
            result.put("code",EnumRespStatus.SMS_SEND_OK.getCode());
            result.put("desc",EnumRespStatus.SMS_SEND_OK.getMessage());
            result.put("serialNo",serialNo);
            result.put("result",null);
        }catch(RejectedExecutionException e){
            e.printStackTrace();
            logger.info("当前发送短信任务太多，请稍后重新请求"+param.toString()+">>>by:"+e.getMessage());
            result.put("status","fail");
            result.put("code",EnumRespStatus.SMS_SEND_LIMIT.getCode());
            result.put("desc",EnumRespStatus.SMS_SEND_LIMIT.getMessage());
            result.put("result",null);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("发送短信失败"+param.toString()+">>>by:"+e.getMessage());
            result.put("status","fail");
            result.put("code",EnumRespStatus.SMS_SEND_ERROR.getCode());
            result.put("desc",EnumRespStatus.SMS_SEND_ERROR.getMessage());
            result.put("result",null);
            return result.toString();
        }
        return result.toString();
    }

    /**
     * @Description 保存群组电话（暂时不启用）
     * @author gbl
     * @date 2018/12/13 10:43
     **/

    @RequestMapping(value = "/rest/saveSMSPhone")
    public String saveSMSPhone(@RequestBody Map<String, Object> param){

        return "";
    }

    /**
     * @Description 创建电话群组
     * @author gbl
     * @date 2018/12/14 9:44
     **/

    @RequestMapping(value = "/rest/saveSMSPhoneGroup")
    public String saveSMSPhoneGroup(@RequestBody Map<String, Object> param){

        JSONObject result = new JSONObject();
        Object appkey = param.get("app_key");
        //String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String code = null;
        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            code = String.valueOf(appkey).concat("PG").concat(timestamp);
            SMSPhoneGroup smsPhoneGroup = new SMSPhoneGroup();
            smsPhoneGroup.setCode(code);
            smsPhoneGroup.setStatus(1);
            smsPhoneGroup.setCreatetime(new Date());
            smsPhoneGroupService.addSMSPhoneGroup(smsPhoneGroup);
            result.put("status","success");
            result.put("code",EnumRespStatus.DATA_OK.getCode());
            result.put("desc",EnumRespStatus.DATA_OK.getMessage());
            result.put("result",code);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("创建电话群组失败"+param.toString()+">>>by:"+e.getMessage());
            logger.info("创建电话群组失败"+param.toString()+">>>by:"+e.getMessage());
            result.put("status","fail");
            result.put("code",EnumRespStatus.DATA_ERROR.getCode());
            result.put("desc",EnumRespStatus.DATA_ERROR.getMessage());
            result.put("result",code);
            return result.toString();
        }

        return  result.toString();
    }


}