package com.guoanshequ.dc.das.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.DfCustomerStoreMapper;

@Service("DfCustomerStoreService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DfCustomerStoreService {

	@Autowired
	DfCustomerStoreMapper dfCustomerStoreMapper;
	
	public List<Map<String, String>> queryByCustomerIdAndStoreId(Map<String, String> mapParam){
		return dfCustomerStoreMapper.queryByCustomerIdStoreId(mapParam);
	}
	
	public void addCustomerStore(Map<String,String> mapParam){
		dfCustomerStoreMapper.addCustomerStore(mapParam);
	}
	
}
