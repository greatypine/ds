package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface TAbnormalOrderMapper{
	
	String queryTAbnorOrdersByOrdersn(Map<String, String> paraMap);
	
	List<Map<String, String>> queryTAbnorOrders(Map<String, String> paraMap);
	
	void addAbnorOrders(Map<String, String> paraMap);
	
	int deleteAbnorOrdersByYearMonth(Map<String, String> paraMap);
	
	String queryTAbnorDownByOrdersn(Map<String, String> paraMap);
	
	String queryTAbnorDownByPhone(Map<String, String> paraMap);
	
	List<Map<String, String>> queryTAbnorDown(Map<String, String> paraMap);
	
	void addAbnorDown(Map<String, String> paraMap);
	
	int deleteAbnorDownByYearMonth(Map<String, String> paraMap);
	
}
