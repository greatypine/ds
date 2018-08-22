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
	
	int addTProductCityDay(Map<String, String> paraMap);
	
	int deleteTProductCityDay();
	
	Map<String, String> queryTProductIsExist(Map<String, String> paraMap);
	
	int addTProductSales(Map<String, String> paraMap);
	
	int updateTProductSalesCount(Map<String, String> paraMap);
	
	int updateTProductSalesNames(Map<String, String> paraMap);
	
	List<Map<String, String>> queryProductCityByDaq(Map<String, String> paraMap);
}
