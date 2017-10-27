package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TAreaTradeStoreMapper;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年10月20日
* @version 1.0
* 说明:
 */
@Service("TAreaTradeStoreService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TAreaTradeStoreService {

    @Autowired
    TAreaTradeStoreMapper tareaTradeStoreDao;

    public List<Map<String, String>> queryTAreaTradesStore(Map<String, String> paraMap) throws Exception{
        
    	return tareaTradeStoreDao.queryTAreaTradesStore(paraMap);
    }
    
    public void addTAreaTradesStore(Map<String, String> paraMap){
    	tareaTradeStoreDao.addTAreaTradesStore(paraMap);
    }
    
    public int deleteByYearMonth(Map<String, String> paraMap){
    	return tareaTradeStoreDao.deleteByYearMonth(paraMap);
    }

    public int updatePubSeasByYearMonth(Map<String, String> paraMap){
    	return tareaTradeStoreDao.updatePubSeasByYearMonth(paraMap);
    }    
}
