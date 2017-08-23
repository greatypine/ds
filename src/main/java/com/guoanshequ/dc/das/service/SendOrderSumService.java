package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.SendOrderSumMapper;

import java.util.List;
import java.util.Map;



/**
* @author CaoPs
* @date 2017年3月16日
* @version 1.0
* 说明: 上门送单量接口信息(国安侠总送单量)
*/ 
@Service("SendOrderSumService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class SendOrderSumService {

    @Autowired
    SendOrderSumMapper sendOrderSumDao;

    public List<Map<String, String>> querySendOrderSum(Map<String, String> paraMap) throws Exception{
        
    	return sendOrderSumDao.querySendOrderSum(paraMap);
    }
    
    public List<Map<String, Object>> querySendOrderSumByDate(Map<String, String> paraMap) throws Exception{
    	
    	return sendOrderSumDao.querySendOrderSumByDate(paraMap);
    }
    
    public List<Map<String, String>> querySendOrderEmployeeSumByDate(Map<String, String> paraMap) throws Exception{
        
    	return sendOrderSumDao.querySendOrderEmployeeSumByDate(paraMap);
    }
}
