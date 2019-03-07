package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.dao.master
 * @Description: 东方大地保险产品
 * @Author: gbl
 * @CreateDate: 2019/1/29 13:34
 */
@Repository
@DataSource("master")
public interface WesterLandProductMapper {

    /**
     * @Description 保存保险产品
     * @author gbl
     * @date 2019/1/29 13:38
     **/

    public void addProduct(Map<String,Object> param);

    /**
     * @Description 删除指定日期的保险产品
     * @author gbl
     * @date 2019/1/29 13:39
     **/

    public void  deleteProduct(String date);

    /**
     * @Description 查询保险产品
     * @author gbl
     * @date 2019/1/29 13:41
     **/

    public List<Map<String,Object>> queryProduct(Map<String,Object> param);
}
