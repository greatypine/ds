package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TAreaPubseasTradeMapper;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年11月21日
* @version 1.0
* 说明:
 */
@Service("TAreaPubseasTradeService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TAreaPubseasTradeService {

    @Autowired
    TAreaPubseasTradeMapper tareaPubseasTradeDao;

    public List<Map<String, String>> queryTAreaPubseasTrades(Map<String, String> paraMap) throws Exception{
        
    	return tareaPubseasTradeDao.queryTAreaPubseasTrades(paraMap);
    } 
    
    public int addTAreaPubseasTrades(Map<String, String> paraMap){
    	return tareaPubseasTradeDao.addTAreaPubseasTrades(paraMap);
    } 
    
    public int deleteByYearMonth(Map<String, String> paraMap){
    	
    	return tareaPubseasTradeDao.deleteByYearMonth(paraMap);
    } 
    
    public int addTAreaPubseasTradeByStore(Map<String, String> paraMap)  throws Exception{
    	
    	return tareaPubseasTradeDao.addTAreaPubseasTradeByStore(paraMap);
    }    
}
