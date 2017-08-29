package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.DfOrderDailyMapper;

@Service("DfOrderDailyService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DfOrderDailyService {
	
	@Autowired
	DfOrderDailyMapper dfOrderDailyMapper;
	
	public void addOrderDaily(String theday){
		dfOrderDailyMapper.addDfOrderDaily(theday);
	}

}
