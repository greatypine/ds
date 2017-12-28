package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.StoreTradeHistoryMapper;
import com.guoanshequ.dc.das.dao.slave.StoreTradeMapper;

import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年12月28日
* @version 1.0
* 说明:门店历史交易额
 */
@Service("StoreTradeHistoryService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class StoreTradeHistoryService {

    @Autowired
    StoreTradeHistoryMapper storeTradeHistoryDao;

    public List<Map<String, String>> queryStoreTrades(Map<String, String> paraMap) throws Exception{
        
    	return storeTradeHistoryDao.queryStoreTradesHistory(paraMap);
    }
    
}
