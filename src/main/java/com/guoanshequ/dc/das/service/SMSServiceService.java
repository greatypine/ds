package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.SMSServiceMapper;
import com.guoanshequ.dc.das.model.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.service
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/11/23 15:44
 */
@Service("SMSServiceService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class SMSServiceService {

    @Autowired
    SMSServiceMapper smsServiceMapper;

    /**
     * @Description 根据通道id查询短信服务
     * @author gbl
     * @date 2018/11/23 15:46
     **/

    public SMSService findSMSServiceByChannelId(String channelId){
        HashMap<String,Object> param = new HashMap<String,Object>();
        param.put("channelId",channelId);
        return smsServiceMapper.findSMSServiceByChannelId(param);
    }

    /**
     * @Description 根据通道首字母拼音查询短信服务
     * @author gbl
     * @date 2018/12/6 9:15
     **/

    public SMSService findSMSServiceByChannelSpell(String channelSpell){
        HashMap<String,Object> param = new HashMap<String,Object>();
        param.put("channelSpell",channelSpell);
        return smsServiceMapper.findSMSServiceByChannelSpell(param);
    }





}
