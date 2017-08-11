package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface CustomerMapper {

	List<Map<String, String>> queryFirst(Map<String, String> paraMap);
	
	List<Map<String, String>> querySecond(Map<String, String> paraMap);
	
	List<Map<String, String>> queryThird(Map<String, String> paraMap);
	
	List<Map<String, String>> queryFirstByStore(Map<String, String> paraMap);
	
	List<Map<String, String>> querySecondByStore(Map<String, String> paraMap);
	
	List<Map<String, String>> queryThirdByStore(Map<String, String> paraMap);
	
	List<Map<String, String>> queryCustomerSecondStoreByDate(Map<String, String> param);
	
	List<Map<String, String>> queryCustomerSecondStoreSumByDate(Map<String, String> param);
}
