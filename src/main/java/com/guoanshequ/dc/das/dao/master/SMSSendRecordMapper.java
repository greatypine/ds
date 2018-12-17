package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.SMSSendRecord;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.dao.master
 * @Description: 短信发送记录
 * @Author: gbl
 * @CreateDate: 2018/11/26 16:37
 */
@Repository
@DataSource("master")
public interface SMSSendRecordMapper {

    /**
     * @Description 保存短信发送请求记录
     * @author gbl
     * @date 2018/11/26 16:42
     **/

    public void addSMSSendRecord(SMSSendRecord smsRecord);
}
