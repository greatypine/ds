package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfOrderReturnedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("DfOrderReturnedService")
@Transactional(value = "master", rollbackFor = Exception.class)
public class DfOrderReturnedService {

	@Autowired
	DfOrderReturnedMapper dfOrderReturnedDao;
	
	public String queryMaxReturnedTime(){
		return dfOrderReturnedDao.queryMaxReturnedTime();
	}

	public Integer addDfOrderReturned(Map<String, String> paraMap) {
		return dfOrderReturnedDao.addDfOrderReturned(paraMap);
	}
	
	public List<Map<String, String>> queryDfOrderReturneds(Map<String, String> paraMap){
		return dfOrderReturnedDao.queryDfOrderReturneds(paraMap);
	}

}
