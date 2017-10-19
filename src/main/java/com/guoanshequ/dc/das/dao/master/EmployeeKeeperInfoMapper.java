package com.guoanshequ.dc.das.dao.master;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 员工店长手机号信息
 */
@Repository
public interface EmployeeKeeperInfoMapper {

	List<Map<String, String>> queryPhones();
	
}
