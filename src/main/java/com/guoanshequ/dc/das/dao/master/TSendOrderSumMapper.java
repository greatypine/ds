package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TSendOrderSumMapper{
	
	List<Map<String, String>> queryTSendOrderSum(Map<String, String> paraMap);
	
	void addTSendOrderSum(Map<String, String> paraMap);
	
	int deleteByYearMonth(Map<String, String> paraMap);
	
	List<Map<String, Object>> queryTSendOrderSumByMonth(Map<String, String> paraMap);
}
