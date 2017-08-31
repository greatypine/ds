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
import com.guoanshequ.dc.das.model.DfCustomerAmount;

@Service("CustomerAmountService")
@Transactional("slave")
public class CustomerAmountService {
	
	@Autowired
	CustomerAmountMapper customerAmountMapper;
	
	
	public List<Map<String, String>> queryCustomerAmount(){
		return customerAmountMapper.queryCustomerAmount();
	}

}
