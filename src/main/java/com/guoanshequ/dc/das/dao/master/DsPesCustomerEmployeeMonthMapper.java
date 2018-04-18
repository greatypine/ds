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
public interface DsPesCustomerEmployeeMonthMapper {
	
	/**
	 * 
	* @Title: deleteDsPesCustomer  
	* @Description: TODO 按月删除国安侠用户 
	* 2018年4月16日
	* @param @param param
	* @param @return      
	* @return Integer 
	* @throws
	 */
	public Integer deleteDsPesCustomer(Map<String,String> param);
	
	/**
	 * 
	* @Title: addDsPesCustomer  
	* @Description: TODO 按月产生国安侠用户 
	* 2018年4月16日
	* @param @param param
	* @param @return      
	* @return Integer 
	* @throws
	 */
	public Integer addDsPesCustomer(Map<String,String> param);
}
