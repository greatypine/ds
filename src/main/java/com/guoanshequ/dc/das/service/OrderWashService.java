package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.slave.OrderWashMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年8月17日
* @version 1.0
* 说明:
 */
@Service("OrderWashService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class OrderWashService {

    @Autowired
    OrderWashMapper orderWashDao;

    public void insertDfOrderHistory(Map<String, String> paraMap){
    	 orderWashDao.insertDfOrderHistory(paraMap);
    }
    
    public void insertDfOrderRealtime(Map<String, String> paraMap){
    	 orderWashDao.insertDfOrderRealtime(paraMap);
    }
}
