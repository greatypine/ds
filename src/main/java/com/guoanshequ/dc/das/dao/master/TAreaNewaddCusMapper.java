package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("master")
public interface TAreaNewaddCusMapper{
	
	List<Map<String, String>> queryTAreaNewaddCus(Map<String, String> paraMap);
	
	List<Map<String, String>> queryTAreaNewaddCusGroupByEmp(Map<String, String> paraMap);
	
	String queryTAreaNewaddcusGroupByEmpOnMonth(Map<String, String> paraMap);
	
	int addTAreaNewaddCus(Map<String, String> paraMap);
	
	int deleteByYearMonth(Map<String, String> paraMap);
	
	int updatePubSeasByYearMonth(Map<String, String> paraMap);
	
	int addTAreaNewaddCusByStore(Map<String, String> paraMap);

	String queryAreaNewaddcusByEmpOnMass(Map<String, String> paraMap);
}
