package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfUserProfileMapper;
import com.guoanshequ.dc.das.model.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
	
	public void addName(Customer paraMap){
		dfUserProfileDao.addName(paraMap);
	}
	
	public Integer addDfUserStore(Map<String, String> paraMap) {
		return dfUserProfileDao.addDfUserStore(paraMap);
	}
	
	public Integer addInterStaffUserTag(Map<String, String> paraMap) {
		return dfUserProfileDao.addUserTag(paraMap);
	}
	
	public void addYlcUser(Map<String, String> paraMap) {
		 dfUserProfileDao.addYlcUser(paraMap);
	}

	public List<Map<String, Object>> isExistCustomer(Map<String, String> paraMap) {
		/**
		 * @author wuxinxin
		 * 2018年10月10日
		 */
		return dfUserProfileDao.isExistCustomer(paraMap);
		
	}

	public void updateYlcUser(Map<String, String> telMap) {
		/**
		 * @author wuxinxin
		 * 2018年10月11日
		 */
		dfUserProfileDao.updateYlcUser(telMap);
		
	}

	public String queryYlcCustomer(Map<String, String> paraMap) {
		/**
		 * @author wuxinxin
		 * 2018年10月15日
		 */
		return dfUserProfileDao.queryYlcCustomer(paraMap);
		
	}	
}
