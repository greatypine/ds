package com.guoanshequ.dc.das.dao.slave;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("slave")
public interface UserMemberMapper{
	
	List<Map<String, Object>> queryUserMemberByCreateTime(Map<String, String> paraMap);
	
	List<Map<String, String>> queryCancelOrderCityByCreateTime(Map<String, String> paraMap);
	
	Map<String,String> queryStoreIdOfGroupByCusid(String customer_id);
	
	String queryCityNoByStoreid(String store_id);
}
