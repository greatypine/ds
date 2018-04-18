package com.guoanshequ.dc.das.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.DsPesCustomerStoreMonthMapper;

/**
 * 
 * @author gbl
 *
 */
@Service("DsPesCustomerMonthService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DsPesCustomerStoreMonthService {
	
	@Autowired
	DsPesCustomerStoreMonthMapper dsPesCustomerMonthDao;
	
	/**
	 * 
	* @Title: deletPesCustomer  
	* @Description: TODO 按月删除门店用户
	* 2018年4月16日
	* @param @param param
	* @param @return      
	* @return Integer 
	* @throws
	* @author gbl
	 */
	public Integer deleteDsPesCustomer(Map<String,String> param){
		return dsPesCustomerMonthDao.deleteDsPesCustomer(param);
	}
	
	
	/**
	 * 
	* @Title: insertDsPesCustomer  
	* @Description: TODO 按月插入门店的用户 
	* 2018年4月16日
	* @param @param param
	* @param @return      
	* @return Integer 
	* @throws
	 */
	public Integer addDsPesCustomer(Map<String,String> param){
		
		return dsPesCustomerMonthDao.addDsPesCustomer(param);
	}
}
