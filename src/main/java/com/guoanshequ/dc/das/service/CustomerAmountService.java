package com.guoanshequ.dc.das.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.CustomerAmountMapper;
import com.guoanshequ.dc.das.model.DfCustomerMonth;
import com.guoanshequ.dc.das.model.DfValidCustomer;

@Service("CustomerAmountService")
@Transactional("slave")
public class CustomerAmountService {
	
	@Autowired
	CustomerAmountMapper customerAmountMapper;
	
	
	public List<DfValidCustomer> queryCustomerAmount(){
		return customerAmountMapper.queryValidCustomer();
	}
	
	public List<Map<String, String>> queryCustomerStore(){
		return customerAmountMapper.queryCustomerStore();
	}
	
	public List<DfCustomerMonth> queryCustomerMonth(){
		return customerAmountMapper.queryCustomerMonth();
	}

}
