package com.guoanshequ.dc.das.dao.master;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

/**
 * 
 * @author gbl
 *
 */
@Repository
@DataSource("master")
public interface DsPesCustomerStoreMonthMapper {
	
	/**
	 * 
	* @Title: deleteDsCustomer  
	* @Description: TODO 按月删除门店用户 
	* 2018年4月16日
	* @param @param param
	* @param @return      
	* @return Integer 
	* @throws
	* @author gbl
	 */
	public Integer deleteDsPesCustomer(Map<String,String> param);
	
	/**
	 * 
	* @Title: insertDsPesCustomer  
	* @Description: TODO 按月产生门店用户数据（包括门店编号，城市，超10元拉新用户量，消费用户量，超10元消费用户量） 
	* 2018年4月16日
	* @param @return      
	* @return int 
	* @throws
	* @author gbl
	 */
	public Integer addDsPesCustomer(Map<String,String> param);
}
