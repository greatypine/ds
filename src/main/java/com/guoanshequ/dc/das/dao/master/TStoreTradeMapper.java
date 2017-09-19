package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TStoreTradeMapper{
	
	List<Map<String, String>> queryTStoreTrades(Map<String, String> paraMap);
	
	void addTStoreTrades(Map<String, String> paraMap);
	
	int deleteByYearMonth(Map<String, String> paraMap);
	
	List<Map<String, Object>> queryTStoreTradesSumByMonth(Map<String, String> paraMap);
}
