package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfCustomerOrderMonthTradeMapper;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("DfCustomerOrderMonthTradeService")
@Transactional(value = "master", rollbackFor = Exception.class)
public class DfCustomerOrderMonthTradeService {

	@Autowired
	DfCustomerOrderMonthTradeMapper dfCustomerOrderMonthTradeDao;
	
	public String queryNextBeginTime(){
		return dfCustomerOrderMonthTradeDao.queryNextBeginTime();
	}
	
	public Integer updateNextBeginTime(String begintime){
		return dfCustomerOrderMonthTradeDao.updateNextBeginTime(begintime);
	}
	
	
	public Integer queryCustomerMaxCount(Map<String, String> paraMap){
		return dfCustomerOrderMonthTradeDao.queryCustomerMaxCount(paraMap);
	}

	public Integer addDfCustomerOrderMonthTrade (Map<String, String> paraMap){
		return dfCustomerOrderMonthTradeDao.addDfCustomerOrderMonthTrade(paraMap);
	}
	
	public Integer addDfCustomerOrderMonthTrades (List<Map<String, String>> resultList){
		return dfCustomerOrderMonthTradeDao.addDfCustomerOrderMonthTrades(resultList);
	}
	
	public Integer updateCustomerOrderNewDaily (Map<String, String> paraMap){
		return dfCustomerOrderMonthTradeDao.updateCustomerOrderNewDaily(paraMap);
	}
	
	public Integer updateCustomerOrderNewMonthly(Map<String, String> paraMap){
		return dfCustomerOrderMonthTradeDao.updateCustomerOrderNewMonthly(paraMap);
	}
	
	public Integer updateCustomerOrderNewTotal (Map<String, String> paraMap){
		return dfCustomerOrderMonthTradeDao.updateCustomerOrderNewTotal(paraMap);
	}
	
	public Integer updateTinyInfo(Map<String, String> paraMap){
		return dfCustomerOrderMonthTradeDao.updateTinyInfo(paraMap);
	} 
	
	public List<String> queryCusOrderMonByCreatetime(Map<String, String> paraMap){
		return dfCustomerOrderMonthTradeDao.queryCusOrderMonByCreatetime(paraMap);
	}
	
	public Integer customerTradePatch(Map<String, String> paraMap){
		return dfCustomerOrderMonthTradeDao.customerTradePatch(paraMap);
	}
}
