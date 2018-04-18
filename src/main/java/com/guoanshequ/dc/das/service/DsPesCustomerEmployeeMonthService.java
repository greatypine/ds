package com.guoanshequ.dc.das.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guoanshequ.dc.das.dao.master.DsPesCustomerEmployeeMonthMapper;

/**
 * 
 * @author gbl
 *
 */
@Service("DsPesCustomerEmployeeMonthService")
public class DsPesCustomerEmployeeMonthService {
	
	@Autowired
	DsPesCustomerEmployeeMonthMapper dsPesCustomerEmployeeMonthDao;
	
	/**
	 * 
	* @Title: deleteDsPesCustomer  
	* @Description: TODO  按月删除国安侠用户
	* 2018年4月16日
	* @param @param param
	* @param @return      
	* @return Integer 
	* @throws
	 */
	public Integer deleteDsPesCustomer(Map<String,String> param){
		return dsPesCustomerEmployeeMonthDao.deleteDsPesCustomer(param);
	}
	
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
	public Integer addDsPesCustomer(Map<String,String> param){
		return dsPesCustomerEmployeeMonthDao.addDsPesCustomer(param);
	}
}
