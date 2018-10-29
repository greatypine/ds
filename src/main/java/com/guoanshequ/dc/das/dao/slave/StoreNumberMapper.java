package com.guoanshequ.dc.das.dao.slave;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("slave")
public interface StoreNumberMapper {

	List<String> queryStoreNumbers();
	
	List<String> queryStoreNoes();
	
	Integer queryStoreSum(Map<String, String> paraMap);
	
	Integer queryStoreNumberById(String store_id); 
}
