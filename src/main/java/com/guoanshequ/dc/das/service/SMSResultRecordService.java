package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.SMSResultRecordMapper;
import com.guoanshequ.dc.das.model.SMSResultRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.service
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/12/18 10:19
 */
@Service("SMSResultRecordService")
@Transactional(value ="master",rollbackFor = Exception.class)
public class SMSResultRecordService {

    @Autowired
    SMSResultRecordMapper smsResultRecordMapper;

    /**
     * @Description 查询短信发送结果
     * @author gbl
     * @date 2018/12/18 10:43
     **/

    public List<Map<String,Object>> querySMSResultRecord(SMSResultRecord smsResultRecord){

        return smsResultRecordMapper.querySMSResultRecord(smsResultRecord);
    }
}
