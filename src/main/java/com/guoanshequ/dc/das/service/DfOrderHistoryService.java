package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.DfOrderHistoryMapper;

@Service("DfOrderHistoryService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DfOrderHistoryService {
	
	@Autowired
	DfOrderHistoryMapper dfOrderHistoryMapper;
	
	public void addOrderHistory(String theday){
		dfOrderHistoryMapper.addDfOrderHistoty(theday);
	}

}
