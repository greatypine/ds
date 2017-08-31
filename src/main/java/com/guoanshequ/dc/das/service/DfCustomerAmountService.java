package com.guoanshequ.dc.das.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.DfCustomerAmountMapper;
import com.guoanshequ.dc.das.model.DfCustomerAmount;

@Service("DfCustomerAmountService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DfCustomerAmountService {
	
	@Autowired
	DfCustomerAmountMapper dfCustomerAmountMapper;

	
	public void addCustomerAmount(Map<String, String> mapParam){
		dfCustomerAmountMapper.addCustomerAmount(mapParam);
	}
	
	public DfCustomerAmount queryByCustomerIdAndStoreIdAndYearAndMonth(DfCustomerAmount dfCustomerAamount){
		return dfCustomerAmountMapper.queryByCustomerIdAndStoreIdAndYearAndMonth(dfCustomerAamount);
	}
	
	public void updateCustomerAmount(DfCustomerAmount dfCustomerAmount){
		dfCustomerAmountMapper.updateCustomerAmount(dfCustomerAmount);
	}
	
	public void addCustomerAmount(DfCustomerAmount dfCustomerAmount){
		dfCustomerAmountMapper.addDfCustomerAmount(dfCustomerAmount);
	}
	
}
