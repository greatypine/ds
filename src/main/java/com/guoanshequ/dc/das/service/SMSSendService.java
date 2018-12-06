package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.SMSResultRecordMapper;
import com.guoanshequ.dc.das.dao.master.SMSSendRecordMapper;
import com.guoanshequ.dc.das.dto.SMS;
import com.guoanshequ.dc.das.model.SMSSendRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.service
 * @Description:短信服务
 * @Author: gbl
 * @CreateDate: 2018/11/27 9:29
 */
@Service("SMSRecordService")
@Transactional(value ="master",rollbackFor = Exception.class)
public class SMSSendService {

    @Autowired
    SMSSendRecordMapper smsRecordMapper;

    @Autowired
    SMSResultRecordMapper smsResultRecordMapper;
    /**
     * @Description 保存短信记录
     * @author gbl
     * @date 2018/11/27 9:34
     **/

    public void addSMSRecordService(SMSSendRecord smsRecord){
        smsRecordMapper.addSMSRecord(smsRecord);
    }

    /**
     * @Description  发送短信
     * @author gbl
     * @date 2018/12/6 13:41
     **/

    public String sendSMS(SMS sms){


        return "";
    }
}
