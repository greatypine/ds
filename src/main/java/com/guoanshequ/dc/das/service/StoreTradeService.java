package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.StoreTradeMapper;

import java.util.List;
import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2017年4月10日
* @version 1.0
* 说明:门店交易额
 */
@Service("StoreTradeService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class StoreTradeService {

    @Autowired
    StoreTradeMapper storeTradeDao;

    public List<Map<String, String>> queryStoreTrades(Map<String, String> paraMap) throws Exception{
        
    	return storeTradeDao.queryStoreTrades(paraMap);
    }
    
    public List<Map<String, String>> queryStoreTradesOrderByGMV(Map<String, String> paraMap) throws Exception{
    	
    	return storeTradeDao.queryStoreTradesOrderByGMV(paraMap);
    }
    
    public List<Map<String, String>> queryStoreTradesOrderByOrderNum(Map<String, String> paraMap) throws Exception{
    	
    	return storeTradeDao.queryStoreTradesOrderByOrderNum(paraMap);
    }
    
}
