package com.guoanshequ.dc.das.dao.slave;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("slave")
public interface StoreTradeMapper{
	
	List<Map<String, String>> queryStoreTrades(Map<String, String> paraMap);
	
	List<Map<String, Object>> queryStoreTradesSumByDate(Map<String, String> paraMap);
	
	List<Map<String, String>> queryStoreTradesOrderByGMV(Map<String, String> paraMap);
	
	List<Map<String, String>> queryStoreTradesOrderByOrderNum(Map<String, String> paraMap);
}
