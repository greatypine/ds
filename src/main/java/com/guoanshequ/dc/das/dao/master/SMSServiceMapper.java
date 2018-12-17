package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.SMSService;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.dao.master
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/11/23 15:33
 */
@Repository
@DataSource("master")
public interface SMSServiceMapper {

    /**
     * @Description 根据短信通道id查询短信服务
     * @author gbl
     * @date 2018/11/23 15:35
     **/

    SMSService findSMSServiceByChannelId(HashMap<String,Object> param);

    /**
     * @Description 根据短信通道首字母查询短信服务
     * @author gbl
     * @date 2018/12/6 9:18
     **/

    SMSService findSMSServiceByChannelSpell(HashMap<String,Object> param);
}
