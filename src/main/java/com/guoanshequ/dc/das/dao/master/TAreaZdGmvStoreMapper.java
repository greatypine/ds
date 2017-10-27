package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TAreaZdGmvStoreMapper{
	
	 List<Map<String, String>> queryTAreaZdGmvStore(Map<String, String> paraMap);
	 
		void addTAreaZdGmvStore(Map<String, String> paraMap);
		
		int deleteByYearMonth(Map<String, String> paraMap);
		
		int updatePubSeasByYearMonth(Map<String, String> paraMap);
}
