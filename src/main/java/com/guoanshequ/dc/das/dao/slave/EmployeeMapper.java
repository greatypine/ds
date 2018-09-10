package com.guoanshequ.dc.das.dao.slave;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.dao.slave
 * @Description: 员工表
 * @Author: gbl
 * @CreateDate: 2018/9/6 14:59
 */
@Repository
@DataSource("slave")
public interface EmployeeMapper {

    /**
     * @Description 根据ID 查询员工
     * @author gbl
     * @date 2018/9/6 15:00
     **/

    List<Map<String,Object>> queryEmployeeById(String id);
}
