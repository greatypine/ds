package com.guoanshequ.dc.das.dao.slave;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("slave")
public interface ProductCityMapper {
	
	 List<Map<String, String>> queryByProductCity(Map<String, String> paraMap);
	
	 List<Map<String, String>> queryByProductCityDay(Map<String, String> paraMap);
	 
	 List<Map<String, String>> queryProductDay(Map<String, String> paraMap);
	 
	 Map<String, String> queryProductStore(Map<String, String> paraMap);
	 
	 Map<String, String> queryProductMapping(Map<String, String> paraMap);
	 
	 Map<String, String> queryProductEshopName(Map<String, String> paraMap);
	 
	 Map<String, String> queryProductCityName(Map<String, String> paraMap);
	 
	 Map<String, String> queryProductDeptChannelName(Map<String, String> paraMap);
}
