package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.UserMemberMapper;
import com.guoanshequ.dc.das.model.OrderReceipts;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2018年5月18日
* @version 1.0
* 说明:
 */
@Service("UserMemberService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class UserMemberService {

    @Autowired
    UserMemberMapper userMemberDao;

    public List<Map<String, Object>> queryUserMemberByCreateTime(Map<String, String> paraMap){
    	
    	return userMemberDao.queryUserMemberByCreateTime(paraMap);
    }
    
    public List<Map<String, String>> queryCancelOrderCityByCreateTime(Map<String, String> paraMap){
    	
    	return userMemberDao.queryCancelOrderCityByCreateTime(paraMap);
    }
    
    public Map<String, String> queryStoreIdOfGroupByCusid(String customer_id){
    	
    	return userMemberDao.queryStoreIdOfGroupByCusid(customer_id);
    }
    
    public String queryCityNoByStoreid(String store_id) {
    	
    	return userMemberDao.queryCityNoByStoreid(store_id);
    }
    
    public List<Map<String, Object>> queryUserMemberByLevelTime(Map<String, String> paraMap){
    	
    	return userMemberDao.queryUserMemberByLevelTime(paraMap);
    }
    
    public OrderReceipts queryRegistInfoByCusIdOftry2(String customer_id){
    	
    	return userMemberDao.queryRegistInfoByCusIdOftry2(customer_id);
    } 
    
    public OrderReceipts queryRegistInfoByCusIdOf2(String customer_id){
    	
    	return userMemberDao.queryRegistInfoByCusIdOf2(customer_id);
    }  
    
    public List<Map<String, Object>> queryUserMemberByLeveFromOpRecord(Map<String, String> paraMap){
    	
    	return userMemberDao.queryUserMemberByLeveFromOpRecord(paraMap);
    }
    
    public String queryCreatetimeFromOpRecordByCusId(String customer_id) {
    	
    	return userMemberDao.queryCreatetimeFromOpRecordByCusId(customer_id);
    }
    
    public List<Map<String, String>> queryMemberToNormalByCreateTime(Map<String, String> paraMap){
    	
    	return userMemberDao.queryMemberToNormalByCreateTime(paraMap);
    }
}
