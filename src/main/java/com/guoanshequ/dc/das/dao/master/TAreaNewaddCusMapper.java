package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TAreaNewaddCusMapper{
	
	List<Map<String, String>> queryTAreaNewaddCus(Map<String, String> paraMap);
	
	List<Map<String, String>> queryTAreaNewaddCusGroupByEmp(Map<String, String> paraMap);
	
	void addTAreaNewaddCus(Map<String, String> paraMap);
	
	int deleteByYearMonth(Map<String, String> paraMap);
	
	int updatePubSeasByYearMonth(Map<String, String> paraMap);
	
	int addTAreaNewaddCusByStore(Map<String, String> paraMap);
}
