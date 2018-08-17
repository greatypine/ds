package com.guoanshequ.dc.das.dao.slave;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.OrderReceipts;

@Repository
@DataSource("slave")
public interface UserMemberMapper{
	
	List<Map<String, Object>> queryUserMemberByCreateTime(Map<String, String> paraMap);
	
	List<Map<String, String>> queryCancelOrderCityByCreateTime(Map<String, String> paraMap);
	
	Map<String,String> queryStoreIdOfGroupByCusid(String customer_id);
	
	String queryCityNoByStoreid(String store_id);
	
	List<Map<String, Object>> queryUserMemberByLevelTime(Map<String, String> paraMap);
	
	OrderReceipts queryRegistInfoByCusIdOftry2(String customer_id);
	
	OrderReceipts queryRegistInfoByCusIdOf2(String customer_id);
	
	List<Map<String, Object>> queryUserMemberByLeveFromOpRecord(Map<String, String> paraMap);
}
