package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.EmployeeKeeperInfoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 员工店长信息
 */
@Service("employeeKeeperInfoService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class EmployeeKeeperInfoService {

    @Autowired
    EmployeeKeeperInfoMapper employeeKeeperInfoDao;

    public List<Map<String, String>> queryPhones() throws Exception{
        return employeeKeeperInfoDao.queryPhones();
    }
}
