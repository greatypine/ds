package com.guoanshequ.dc.das.dao.master;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TAreaPubseasNewaddCusStoreMapper{
	
	int deleteByYearMonth(Map<String, String> paraMap);
	
}
