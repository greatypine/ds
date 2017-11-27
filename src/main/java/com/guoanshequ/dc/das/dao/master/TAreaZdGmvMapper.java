package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TAreaZdGmvMapper{
	
	 List<Map<String, String>> queryTAreaZdGmv(Map<String, String> paraMap);
	 
	 List<Map<String, String>> queryTAreaZdGmvGroupByEmp(Map<String, String> paraMap);
	 
	 String queryTAreaZdGmvSumGroupByEmpOnMonth(Map<String, String> paraMap);
	 
	 int addTAreaZdGmv(Map<String, String> paraMap);
		
	 int deleteByYearMonth(Map<String, String> paraMap);
		
	 int updatePubSeasByYearMonth(Map<String, String> paraMap);
	 
	 int addTAreaZdGmvByStore(Map<String, String> paraMap);
}
