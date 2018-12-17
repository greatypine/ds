package com.guoanshequ.dc.das.domain;

import com.guoanshequ.dc.das.dao.master.SMSPhoneMapper;
import com.guoanshequ.dc.das.dto.SMS;
import com.guoanshequ.dc.das.service.SMSSendService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.domain
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/12/11 16:42
 */
public class SMSSendTask implements Runnable{

    private static final Logger logger = LogManager.getLogger(SMSSendTask.class);



    private String taskName;
    private SMS sms;
    private SMSSendService smsSendService;
    private SMSPhoneMapper smsPhoneMapper;


    public SMSSendTask(String taskName, SMS sms,SMSSendService smsSendService,SMSPhoneMapper smsPhoneMapper){
        this.taskName = taskName;
        this.sms = sms;
        this.smsSendService = smsSendService;
        this.smsPhoneMapper = smsPhoneMapper;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public SMS getSms() {
        return sms;
    }

    public void setSms(SMS sms) {
        this.sms = sms;
    }





    @Override
    public synchronized void run() {
        HashMap<String,Object> param = new HashMap<String,Object>();
        try{
            logger.info(taskName+"--开始发送短信...");
            //group短信发送业务

            param.put("groupCode",this.sms.getGroupCode());
            param.put("serialNo",this.sms.getSerialNo());
            List<Map<String,Object>> phoneList = smsPhoneMapper.selectSMSPhoneNoSend(param);

            for(int i=0;i<phoneList.size();i++){
                this.sms.setMobilePhone(phoneList.get(i).get("mobilephone").toString());
                smsSendService.sendSMS(this.sms);
            }

            logger.info(taskName+"--结束发送短信...");
            logger.info(taskName+"--发送短信成功>>>"+param);

        }catch (Exception e){
            e.printStackTrace();
            logger.info(taskName+"--发送短信失败>>>"+param.toString()+">>>by:"+e.getMessage());
            logger.info(this.getTaskName()+"--线程异常终止..."+">>>by:"+e.getMessage());
            System.out.println(this.getTaskName()+"线程异常终止...");
        }

    }


}
