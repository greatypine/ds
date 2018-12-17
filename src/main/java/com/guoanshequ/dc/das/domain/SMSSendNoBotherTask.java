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
 * @CreateDate: 2018/12/11 16:39
 */
public class SMSSendNoBotherTask implements Runnable{

    private static final Logger logger = LogManager.getLogger(SMSSendNoBotherTask.class);

    @Autowired
    private SMSPhoneMapper smsPhoneMapper;

    @Autowired
    SMSSendService smsSendService;

    private String taskName;
    private SMS sms;


    public SMSSendNoBotherTask(String taskName, SMS sms){
        this.taskName = taskName;
        this.sms = sms;
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

    private final int STOP = -1;
    private final int SUSPEND = 0;
    private final int RUNNING = 1;
    private int status = 1;
    private long count = 0;



    @Override
    public synchronized void run()
    {
        // 判断是否停止
        while (status != STOP)
        {
            // 判断是否挂起
            if (status == SUSPEND)
            {
                try{
                    // 若线程挂起则阻塞自己
                    wait();
                }catch (InterruptedException e){
                    System.out.println(this.getTaskName()+",线程异常终止...");
                }
            }else{


                count++;
                logger.info(this.getTaskName()+"第" + count + "次运行...");
                System.out.println(this.getTaskName() + "第" + count + "次运行...");
                try{
                    //group短信发送业务
                    HashMap<String,Object> param = new HashMap<String,Object>();
                    param.put("groupCode",this.sms.getGroupCode());
                    param.put("serialNo",this.sms.getSerialNo());
                    List<Map<String,Object>> phoneList = smsPhoneMapper.selectSMSPhoneNoSend(param);

                    for(int i=0;i<phoneList.size();i++){
                        this.sms.setMobilePhone(phoneList.get(i).get("mobilephone").toString());
                        smsSendService.sendSMS(this.sms);
                    }
                }catch (Exception e){
                    System.out.println(this.getTaskName()+",线程异常终止...");
                }
            }
        }
    }

    /**
     * 恢复
     */
    public synchronized void myResume()
    {
        // 修改状态
        status = RUNNING;
        // 唤醒
        notifyAll();
    }

    /**
     * 挂起
     */
    public void mySuspend()
    {
        // 修改状态
        status = SUSPEND;
    }

    /**
     * 停止
     */
    public void myStop()
    {
        // 修改状态
        status = STOP;
    }
}
