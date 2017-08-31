package com.guoanshequ.dc.das.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.DfOrderDailyMapper;
import com.guoanshequ.dc.das.model.DFOrderRealtime;

@Service("DfOrderDailyService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DfOrderDailyService {
	
	@Autowired
	DfOrderDailyMapper dfOrderDailyMapper;
	
	public void addOrderDaily(String theday){
		dfOrderDailyMapper.addDfOrderDaily(theday);
	}
	
	public void deleteOrderDaily(){
		dfOrderDailyMapper.deleteOrderDaily();
	}
	
	public List<DFOrderRealtime> queryOrdersDaily(){
		return dfOrderDailyMapper.queryOrdersDaily();
	}

}
