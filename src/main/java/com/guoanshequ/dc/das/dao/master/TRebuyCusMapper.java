package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TRebuyCusMapper{
	
	List<Map<String, String>> queryTRebuyCus(Map<String, String> paraMap);
	
	void addTRebuyCus(Map<String, String> paraMap);
}
