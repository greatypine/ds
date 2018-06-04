package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.BigScreenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("BigScreenService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class BigScreenService {

	@Autowired
	BigScreenMapper bigScreenDao;
	
	public Integer queryCusnumMonthForHQ(Map<String, String> paraMap){
        return bigScreenDao.queryCusnumMonthForHQ(paraMap);
    }

	public Integer queryCusnumHistoryForHQ(Map<String, String> paraMap){
		return bigScreenDao.queryCusnumHistoryForHQ(paraMap);
	}
	
	public Integer updateCusnumForHQ (Map<String, Integer> paraMap){
		return bigScreenDao.updateCusnumForHQ(paraMap);
	}
	
}
