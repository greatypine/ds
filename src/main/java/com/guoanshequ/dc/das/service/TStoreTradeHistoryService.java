package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TStoreTradeChannelMapper;
import com.guoanshequ.dc.das.dao.master.TStoreTradeHistoryMapper;

import java.util.List;
import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2017年12月28日
* @version 1.0
* 说明:
 */
@Service("TStoreTradeHistoryService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TStoreTradeHistoryService {

    @Autowired
    TStoreTradeHistoryMapper tstoreTradeHistoryDao;

    public List<Map<String, String>> queryTStoreTradeHistory(Map<String, String> paraMap) throws Exception{
        
    	return tstoreTradeHistoryDao.queryTStoreTradeHistory(paraMap);
    }
    
    public int addTStoreTradeHistory(Map<String, String> paraMap){
    	
    	return tstoreTradeHistoryDao.addTStoreTradeHistory(paraMap);
    }
    
}
