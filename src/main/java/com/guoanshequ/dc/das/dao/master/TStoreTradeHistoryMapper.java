package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TStoreTradeHistoryMapper{
	
	List<Map<String, String>> queryTStoreTradeHistory(Map<String, String> paraMap);
	
	int addTStoreTradeHistory(Map<String, String> paraMap);
	
}
