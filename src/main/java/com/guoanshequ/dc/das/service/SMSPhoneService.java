package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.SMSPhoneMapper;
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
 * @CreateDate: 2018/12/12 17:18
 */
@Service("SMSPhoneService")
@Transactional(value ="master",rollbackFor = Exception.class)
public class SMSPhoneService {

    @Autowired
    private SMSPhoneMapper smsPhoneMapper;

    public  void addSMSPhone(List<Map<String,Object>> list){
        if(list!=null&&list.size()>0){
            for(Map m:list){
                smsPhoneMapper.addSMSPhone(m);
            }
        }
    }
}
