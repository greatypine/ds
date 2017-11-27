package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TAreaPubseasZdGmvMapper{
	
	 List<Map<String, String>> queryTAreaPubseasZdGmv(Map<String, String> paraMap);
	 
	 int addTAreaPubseasZdGmv(Map<String, String> paraMap);
	
	 int deleteByYearMonth(Map<String, String> paraMap);
	 
	 int addTAreaPubseasZdGmvByStore(Map<String, String> paraMap);
}
