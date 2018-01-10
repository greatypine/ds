package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("master")
public interface TProductCityMapper {
	
	List<Map<String, String>> queryTProductCity(Map<String, String> paraMap);
	
	int addTProductCity(Map<String, String> paraMap);
	
	int deleteByYearMonth();
}
