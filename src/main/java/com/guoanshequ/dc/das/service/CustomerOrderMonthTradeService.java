package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.slave.CustomerOrderMonthTradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("CustomerOrderMonthTradeService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class CustomerOrderMonthTradeService {

	@Autowired
	CustomerOrderMonthTradeMapper customerOrderMonthTradeDao;
	
	public List<Map<String, String>> queryCustomerOrderMonthTradeBySignTime(Map<String, String> paraMap){
        return customerOrderMonthTradeDao.queryCustomerOrderMonthTradeBySignTime(paraMap);
    }

	public String queryMaxSigntime(){
		return customerOrderMonthTradeDao.queryMaxSigntime();
	}
	
	public List<String> queryCusOrderMonByCreatetime(Map<String, String> paraMap){
		return customerOrderMonthTradeDao.queryCusOrderMonByCreatetime(paraMap);
	}
	
	public Integer updateTinyInfo(Map<String, String> paraMap){
		return customerOrderMonthTradeDao.updateTinyInfo(paraMap);
	} 
}
