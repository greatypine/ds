package com.guoanshequ.dc.das.dao.slave;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("slave")
public interface OrderWashMapper{
	
	void insertDfOrderHistory(Map<String, String> paraMap);
	
	void insertDfOrderRealtime(Map<String, String> paraMap);
}
