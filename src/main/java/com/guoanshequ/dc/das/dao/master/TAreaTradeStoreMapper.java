package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TAreaTradeStoreMapper{
	
	List<Map<String, String>> queryTAreaTradesStore(Map<String, String> paraMap);
	
	void addTAreaTradesStore(Map<String, String> paraMap);
	
	int deleteByYearMonth(Map<String, String> paraMap);
	
	int updatePubSeasByYearMonth(Map<String, String> paraMap);
}
