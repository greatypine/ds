package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfUserProfileMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("DfUserProfileService")
@Transactional(value = "master", rollbackFor = Exception.class)
public class DfUserProfileService {

	@Autowired
	DfUserProfileMapper dfUserProfileDao;
	
	public Integer addDfUserProfile(Map<String, String> paraMap) {
		return dfUserProfileDao.addDfUserProfile(paraMap);
	}
	
	public Integer isExistCusDraw(String mobilephone){
		return dfUserProfileDao.isExistCusDraw(mobilephone);
	}

}
