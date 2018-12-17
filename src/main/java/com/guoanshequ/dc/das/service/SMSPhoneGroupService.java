package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.SMSPhoneGroupMapper;
import com.guoanshequ.dc.das.model.SMSPhoneGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.service
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/12/13 14:40
 */
@Service("SMSPhoneGroupService")
@Transactional(value ="master",rollbackFor = Exception.class)
public class SMSPhoneGroupService {

    @Autowired
    private SMSPhoneGroupMapper smsPhoneGroupMapper;


    /**
     * @Description 查询电话群组信息
     * @author gbl
     * @date 2018/12/13 14:43
     **/

    public List<SMSPhoneGroup> querySMSPhoneGroup(String groupCode){

        return smsPhoneGroupMapper.querySMSPhoneGroup(groupCode);
    }

    /**
     * @Description 保存电话群组
     * @author gbl
     * @date 2018/12/13 14:45
     **/

    public void addSMSPhoneGroup(SMSPhoneGroup smsPhoneGroup){

        smsPhoneGroupMapper.addSMSPhoneGroup(smsPhoneGroup);
    }

}
