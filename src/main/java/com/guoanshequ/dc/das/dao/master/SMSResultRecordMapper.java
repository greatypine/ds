package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.SMSResultRecord;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.dao.master
 * @Description:短信发送结果记录
 * @Author: gbl
 * @CreateDate: 2018/12/6 10:58
 */
@Repository
@DataSource("master")
public interface SMSResultRecordMapper {

    /**
     * @Description 保存短信发送结果
     * @author gbl
     * @date 2018/12/6 18:16
     **/

    public void addSMSResultRecord(SMSResultRecord smsResultRecord);
}
