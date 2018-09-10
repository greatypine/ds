package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.slave.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.service
 * @Description:员工信息
 * @Author: gbl
 * @CreateDate: 2018/9/6 15:12
 */
@Service("EmployeeService")
@Transactional(value="slave")
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * @Description 根据ID 查询员工信息
     * @author gbl
     * @date 2018/9/6 15:16
     **/

    public List<Map<String,Object>> queryEmployeeById(String id){
        return employeeMapper.queryEmployeeById(id);
    }
}
