package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TAreaTradeMapper;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年10月20日
* @version 1.0
* 说明:
 */
@Service("TAreaTradeService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TAreaTradeService {

    @Autowired
    TAreaTradeMapper tareaTradeDao;

    public List<Map<String, String>> queryTAreaTrades(Map<String, String> paraMap) throws Exception{
        
    	return tareaTradeDao.queryTAreaTrades(paraMap);
    }
    
    public List<Map<String, String>> queryTAreaTradesGroupByEmp(Map<String, String> paraMap) throws Exception{
        
    	return tareaTradeDao.queryTAreaTradesGroupByEmp(paraMap);
    }
    
    public void addTAreaTrades(Map<String, String> paraMap){
    	tareaTradeDao.addTAreaTrades(paraMap);
    }
    
    public int deleteByYearMonth(Map<String, String> paraMap){
    	return tareaTradeDao.deleteByYearMonth(paraMap);
    }
    
    public int updatePubSeasByYearMonth(Map<String, String> paraMap){
    	return tareaTradeDao.updatePubSeasByYearMonth(paraMap);
    }
}
