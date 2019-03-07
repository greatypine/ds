package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.dao.master
 * @Description:东方大地保险交易信息
 * @Author: gbl
 * @CreateDate: 2019/1/29 13:34
 */
@Repository
@DataSource("master")
public interface WesterLandTransactionMapper {

    /**
     * @Description 保存交易信息
     * @author gbl
     * @date 2019/1/29 13:38
     **/

    public void addTransaction(Map<String,Object> param);

    /**
     * @Description 删除指定日期的交易信息
     * @author gbl
     * @date 2019/1/29 13:39
     **/

    public void  deleteTransaction(String date);

    /**
     * @Description 查询交易信息
     * @author gbl
     * @date 2019/1/29 13:41
     **/

    public List<Map<String,Object>> queryTransaction(Map<String,Object> param);
}
