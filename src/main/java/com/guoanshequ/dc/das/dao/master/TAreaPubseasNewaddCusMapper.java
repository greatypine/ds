package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TAreaPubseasNewaddCusMapper{
	
	List<Map<String, String>> queryTAreaPubseasNewaddCus(Map<String, String> paraMap);
	
	int addTAreaPubseasNewaddCus(Map<String, String> paraMap);
	
	int deleteByYearMonth(Map<String, String> paraMap);
	
	int addTAreaPubseasNewaddCusByStore(Map<String, String> paraMap);
}
