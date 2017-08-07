package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface CustomerMapper {

	List<Map<String, String>> queryFirst(Map<String, String> paraMap);
	
	List<Map<String, String>> querySecond(Map<String, String> paraMap);
	
	List<Map<String, String>> queryThird(Map<String, String> paraMap);
	
	List<Map<String, String>> queryFirstByStore(Map<String, String> paraMap);
	
	List<Map<String, String>> querySecondByStore(Map<String, String> paraMap);
	
	List<Map<String, String>> queryThirdByStore(Map<String, String> paraMap);
	
	
	
	/**
	 * 
	 * TODO 按月查询门店用户画像 
	 * 2017年8月4日
	 * @author gaobaolei
	 * @param storeId
	 * @param query_date
	 * @return
	 */
	public Integer getCustomerAmountByStoreOfMonth(Map<String, Object> param); 
	
	/**
	 * 
	 * TODO 按天查询门店用户画像 
	 * 2017年8月4日
	 * @author gaobaolei
	 * @param storeId
	 * @param query_date
	 * @return
	 */
	public Integer getCustomerAmountByStoreOfDaily(Map<String, Object> param);
}
