package com.guoanshequ.dc.das.dao.slave;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("slave")
public interface OrderMapper{
	
	String queryIdByOrderSn(Map<String, String> paraMap);
	
	Map<String, Object> queryOrderAddressByOrderSn(Map<String, String> paraMap);
	
	String queryMaxSigntime(Map<String, String> paraMap);
}
