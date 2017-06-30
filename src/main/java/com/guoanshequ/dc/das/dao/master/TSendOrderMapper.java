package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TSendOrderMapper{
	
	List<Map<String, String>> queryTSendOrders(Map<String, String> paraMap);
	
	void addTSendOrders(Map<String, String> paraMap);
	
	int deleteByYearMonth(Map<String, String> paraMap);
}
