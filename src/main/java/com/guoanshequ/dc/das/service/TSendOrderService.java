package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TSendOrderMapper;

import java.util.List;
import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2017年4月12日
* @version 1.0
* 说明:上门送单量按月度从表中取
 */
@Service("TSendOrderService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TSendOrderService {

    @Autowired
    TSendOrderMapper tsendOrderDao;

    public List<Map<String, String>> queryTSendOrders(Map<String, String> paraMap) throws Exception{
        
    	return tsendOrderDao.queryTSendOrders(paraMap);
    }
    
    public void addTSendOrders(Map<String, String> paraMap){
    	 tsendOrderDao.addTSendOrders(paraMap);
    }
    
    public int deleteByYearMonth(Map<String, String> paraMap){
    	return tsendOrderDao.deleteByYearMonth(paraMap);
    }
}
