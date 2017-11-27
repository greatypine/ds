package com.guoanshequ.dc.das.dao.slave;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("slave")
public interface AreaPubseasTradeMapper{
	
	List<Map<String, String>> queryAreaPubseasTrades(Map<String, String> paraMap);
	
}
