package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.PermissionService;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.dao.master
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/11/14 13:16
 */
@Repository
@DataSource("master")
public interface PermissionServiceMapper {

    /**
     * @Description 根据appkey 和请求查询有效的服务
     * @author gbl
     * @date 2018/11/14 13:23
     **/

    PermissionService findByAppKeyAndURI(HashMap<String,Object> param);
}
