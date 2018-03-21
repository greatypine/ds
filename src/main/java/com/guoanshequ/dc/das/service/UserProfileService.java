package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.UserProfileMapper;
import com.guoanshequ.dc.das.model.Customer;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2018年3月12日
* @version 1.0
* 说明:
 */
@Service("UserProfileService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class UserProfileService {

    @Autowired
    UserProfileMapper userProfileDao;

    public List<Map<String, String>> queryCustomerInfoBySignTime(Map<String, String> paraMap){
    	
    	return userProfileDao.queryCustomerInfoBySignTime(paraMap);
    }
    
    public List<Customer> queryCusName(){
    	return userProfileDao.queryCusName();
    }
}
