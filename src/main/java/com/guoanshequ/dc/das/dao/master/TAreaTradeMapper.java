package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TAreaTradeMapper{
	
	List<Map<String, String>> queryTAreaTrades(Map<String, String> paraMap);

	List<Map<String, String>> queryTAreaTradesGroupByEmp(Map<String, String> paraMap);

	void addTAreaTrades(Map<String, String> paraMap);
	
	int deleteByYearMonth(Map<String, String> paraMap);
	
	int updatePubSeasByYearMonth(Map<String, String> paraMap);
	
	int addTAreaTradeByStore(Map<String, String> paraMap);
}
