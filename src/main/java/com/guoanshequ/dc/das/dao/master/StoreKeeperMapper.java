package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface StoreKeeperMapper {

	List<Map<String, String>> queryStoreKeepers(Map<String, String> paraMap);
	
	List<Map<String, String>> queryPreStoreKeepers(Map<String, String> paraMap);
	
}
