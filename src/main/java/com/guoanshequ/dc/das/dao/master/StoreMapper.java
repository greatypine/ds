package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface StoreMapper {

	List<Map<String, String>> queryStores(Map<String, String> paraMap);
	
	String queryStoreNumbers();
}
