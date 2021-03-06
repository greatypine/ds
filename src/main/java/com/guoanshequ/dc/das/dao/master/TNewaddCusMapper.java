package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("master")
public interface TNewaddCusMapper{
	
	List<Map<String, String>> queryTNewaddCus(Map<String, String> paraMap);
	
	void addTNewaddCus(Map<String, String> paraMap);
	
	int deleteByYearMonth(Map<String, String> paraMap);
	
	List<Map<String, Object>> queryTNewaddCusSumByMonth(Map<String, String> paraMap);
}
