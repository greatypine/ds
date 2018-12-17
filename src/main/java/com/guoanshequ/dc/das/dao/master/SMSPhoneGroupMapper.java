package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.SMSPhoneGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.dao.master
 * @Description:电话群组
 * @Author: gbl
 * @CreateDate: 2018/12/13 14:10
 */
@Repository
@DataSource("master")
public interface SMSPhoneGroupMapper {

    /**
     * @Description 查询电话群组
     * @author gbl
     * @date 2018/12/13 14:15
     **/

    public List<SMSPhoneGroup> querySMSPhoneGroup(String groupCode);


    /**
     * @Description 保存群组信息
     * @author gbl
     * @date 2018/12/13 14:18
     **/

    public  void  addSMSPhoneGroup(SMSPhoneGroup smsPhoneGroup);


}
