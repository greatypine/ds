package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TAreaPubseasTradeMapper{
	
	List<Map<String, String>> queryTAreaPubseasTrades(Map<String, String> paraMap);
	
	int addTAreaPubseasTrades(Map<String, String> paraMap);

	int deleteByYearMonth(Map<String, String> paraMap);
	
	int addTAreaPubseasTradeByStore(Map<String, String> paraMap);
}
