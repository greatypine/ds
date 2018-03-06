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
	
}
