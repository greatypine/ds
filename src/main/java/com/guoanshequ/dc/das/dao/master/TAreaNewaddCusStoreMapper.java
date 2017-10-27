package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TAreaNewaddCusStoreMapper{
	
	List<Map<String, String>> queryTAreaNewaddCusStore(Map<String, String> paraMap);
	
	void addTAreaNewaddCusStore(Map<String, String> paraMap);
	
	int deleteByYearMonth(Map<String, String> paraMap);
	
	int updatePubSeasByYearMonth(Map<String, String> paraMap);
}
