package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.SMSPhone;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.dao.master
 * @Description:电话群组
 * @Author: gbl
 * @CreateDate: 2018/12/7 13:50
 */
@Repository
@DataSource("master")
public interface SMSPhoneMapper {

    /**
     * @Description 根据短信群组编号查询电话号码
     * @author gbl
     * @date 2018/12/7 13:56
     **/

    List<SMSPhone> querySMSPhone(String groupCode);

    /**
     * @Description 查询未发送的电话号码
     * @author gbl
     * @date 2018/12/10 10:01
     **/

    List<Map<String,Object>> selectSMSPhoneNoSend(HashMap<String,Object> param);

    /**
     * @Description 创建电话群组
     * @author gbl
     * @date 2018/12/12 17:17
     **/

    public void  addSMSPhone(Map<String,Object> map);
}
