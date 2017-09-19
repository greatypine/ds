package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.DfValidCustomerMapper;
import com.guoanshequ.dc.das.model.DfValidCustomer;

@Service("DfValidCustomerService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DfValidCustomerService {
	
	@Autowired
	DfValidCustomerMapper dfValidCustomerMapper;
	
	public DfValidCustomer queryByCustomerId(String customer_id){
		return dfValidCustomerMapper.queryByCustomerId(customer_id);
	}
	
	public void updateValidCustomer(DfValidCustomer dfValidCustomer){
		dfValidCustomerMapper.updateValidCustomer(dfValidCustomer);
		
	}
	
	public void addValidCustomer(DfValidCustomer dfValidCustomer){
		dfValidCustomerMapper.addValidCustomer(dfValidCustomer);
	}
	
}
