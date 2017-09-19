package com.guoanshequ.dc.das.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.DfCustomerMonthMapper;
import com.guoanshequ.dc.das.model.DFOrderRealtime;
import com.guoanshequ.dc.das.model.DfCustomerMonth;

@Service("DfCustomerMonthService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DfCustomerMonthService {
	
	@Autowired
	DfCustomerMonthMapper dfCustomerMonthMapper;
	
	public DfCustomerMonth queryByCustomerIdAndStoreId(Map<String, String> mapParam){
		return dfCustomerMonthMapper.queryByCustomerIdAndStoreId(mapParam);
	}
	
	public void updateCustomerMonth(DfCustomerMonth dfCustomerMonth){
		dfCustomerMonthMapper.updateCustomerMonth(dfCustomerMonth);
	}
	
	public void addCustomerMonth(DfCustomerMonth dfCustomerMonth){
		dfCustomerMonthMapper.addDfCustomerMonth(dfCustomerMonth);
	}
	
}
