package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TStoreTradeChannelMapper{
	
	List<Map<String, String>> queryTStoreTradeChannel(Map<String, String> paraMap);
	
	void addTStoreTradeChannel(Map<String, String> paraMap);
	
	int deleteByYearMonth(Map<String, String> paraMap);
	
}
