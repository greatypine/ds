package com.guoanshequ.dc.das.service;


import com.guoanshequ.dc.das.dao.master.SMSResultRecordMapper;
import com.guoanshequ.dc.das.dao.master.SMSSendRecordMapper;
import com.guoanshequ.dc.das.domain.SMSCode;
import com.guoanshequ.dc.das.dto.SMS;
import com.guoanshequ.dc.das.model.SMSResultRecord;
import com.guoanshequ.dc.das.model.SMSSendRecord;
import com.guoanshequ.dc.das.utils.HttpClientUtil;
import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.service
 * @Description:短信服务
 * @Author: gbl
 * @CreateDate: 2018/11/27 9:29
 */
@Service("SMSSendService")
@Transactional(value ="master",rollbackFor = Exception.class)
public class SMSSendService {


    private static final Logger logger = LogManager.getLogger(SMSSendService.class);

    @Autowired
    SMSSendRecordMapper smsSendRecordMapper;

    @Autowired
    SMSResultRecordMapper smsResultRecordMapper;
    /**
     * @Description 保存短信发送请求记录
     * @author gbl
     * @date 2018/11/27 9:34
     **/

    public void addSMSSendRecordService(SMSSendRecord smsRecord){
        smsSendRecordMapper.addSMSSendRecord(smsRecord);
    }

    /**
     * @Description  发送短信
     * @author gbl
     * @date 2018/12/6 13:41
     **/

    public JSONObject sendSMS(SMS sms){

        String smsContent_gb2312 = null;
        String smsResult = null;
        JSONObject jsonObject  = new JSONObject();
        Date sendTime = null;
        SMSResultRecord srr = new SMSResultRecord();//短信发送请求结果
        try {
            sendTime = new Date();
            //短信内容转码
            smsContent_gb2312 = URLEncoder.encode(sms.getContent(), "gb2312");
            //创建url
            String smsurl = sms.getUrl().concat("username=").concat(sms.getUserName())
                            .concat("&password=").concat(sms.getPassWord())
                            .concat("&message=").concat(smsContent_gb2312)
                            .concat("&phone=").concat(sms.getMobilePhone())
                            .concat("&epid=").concat(sms.getChannelId()).concat("&linkid=&subcode=");

            //发送短信
            smsResult = HttpClientUtil.sendGet(smsurl);

            if(SMSCode.SMS_SUCCESS.getCode().equals(smsResult)){//短信发送成功
                //请求返回值
                jsonObject.put("status","success");
//                jsonObject.put("code",smsResult);
                jsonObject.put("sendResult",SMSCode.SMS_SUCCESS.getMessage());
                jsonObject.put("mobilePhone",sms.getMobilePhone());
                //返回值存数据库
                srr.setSendResultFlag(smsResult);
                srr.setSendResultDesc(SMSCode.SMS_SUCCESS.getMessage());
            }else if(smsResult.contains(SMSCode.SMS_IP_ERROR.getCode())){//请求发送成功（不是短信发送成功）
                jsonObject.put("status","fail");
//                jsonObject.put("code",smsResult);
                jsonObject.put("sendResult",SMSCode.SMS_IP_ERROR.getMessage());
                jsonObject.put("mobilePhone",sms.getMobilePhone());

                srr.setSendResultFlag(smsResult);
                srr.setSendResultDesc(SMSCode.SMS_IP_ERROR.getMessage());
            }else{//请求发送成功（不是短信发送成功）
                jsonObject.put("status","fail");
//                jsonObject.put("code",smsResult);
                jsonObject.put("sendResult",SMSCode.getMessage(smsResult));
                jsonObject.put("mobilePhone",sms.getMobilePhone());
                srr.setSendResultFlag(smsResult);
                srr.setSendResultDesc(SMSCode.getMessage(smsResult));
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.info("发送短信失败1"+e.getMessage());
            jsonObject.put("status","fail");//请求发送失败
//            jsonObject.put("code",SMSCode.SMS_REQUEST_FAIL.getCode());//请求发送失败
            jsonObject.put("sendResult",SMSCode.SMS_REQUEST_FAIL.getMessage()+"-error1");
            jsonObject.put("mobilePhone",sms.getMobilePhone());

            srr.setSendResultFlag(SMSCode.SMS_REQUEST_FAIL.getCode());
            srr.setSendResultDesc(SMSCode.SMS_REQUEST_FAIL.getMessage()+"-error1");
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            logger.info("发送短信失败2"+e.getMessage());
            jsonObject.put("status","fail");//请求发送失败
//            jsonObject.put("code",SMSCode.SMS_REQUEST_FAIL.getCode());//请求发送失败
            jsonObject.put("sendResult",SMSCode.SMS_REQUEST_FAIL.getMessage()+"-error2");
            jsonObject.put("mobilePhone",sms.getMobilePhone());

            srr.setSendResultFlag(SMSCode.SMS_REQUEST_FAIL.getCode());
            srr.setSendResultDesc(SMSCode.SMS_REQUEST_FAIL.getMessage()+"-error2");
            return jsonObject;
        }finally {

            try {
                srr.setAppKey(sms.getAppKey());
                srr.setChannelId(sms.getChannelId());
                srr.setMobilePhone(sms.getMobilePhone());
                srr.setSendTime(sendTime);
                srr.setAppKey(sms.getAppKey());
                srr.setSmsType(sms.getSmsType());
                srr.setGroupCode(sms.getGroupCode());
                srr.setSerialNo(sms.getSerialNo());
                smsResultRecordMapper.addSMSResultRecord(srr);
            } catch (Exception e) {
                e.printStackTrace();
                StringBuilder smsInfo = new StringBuilder();
                smsInfo.append("phone:").append(srr.getMobilePhone())
                        .append(";result").append(srr.getSendResultFlag()+":"+srr.getSendResultDesc())
                        .append(";ssmType:").append(srr.getSmsType())
                        .append(";groupCode:").append(srr.getGroupCode())
                        .append(";appKey:").append(srr.getAppKey())
                        .append("channelID:").append(srr.getChannelId());
                logger.info("保存短信发送结果失败"+smsInfo.toString());
            }
        }


        return jsonObject;
    }
}
