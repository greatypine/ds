package com.guoanshequ.dc.das.dao.slave;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("slave")
public interface StoreTradeHistoryMapper{
	
	List<Map<String, String>> queryStoreTradesHistory(Map<String, String> paraMap);
	
}
