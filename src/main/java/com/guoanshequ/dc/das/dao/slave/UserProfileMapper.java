package com.guoanshequ.dc.das.dao.slave;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("slave")
public interface UserProfileMapper{
	
	List<Map<String, String>> queryCustomerInfoBySignTime(Map<String, String> paraMap);
	
	List<Map<String, String>> queryCusName();
}