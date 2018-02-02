package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TStoreTradeMapper;

import java.util.List;
import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2017年4月12日
* @version 1.0
* 说明:门店交易额按月度取数据
 */
@Service("TStoreTradeService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TStoreTradeService {

    @Autowired
    TStoreTradeMapper tstoreTradeDao;

    public List<Map<String, String>> queryTStoreTrades(Map<String, String> paraMap) throws Exception{
        
    	return tstoreTradeDao.queryTStoreTrades(paraMap);
    }
    
    public void addTStoreTrades(Map<String, String> paraMap){
    	tstoreTradeDao.addTStoreTrades(paraMap);
    }
    
    public int deleteByYearMonth(Map<String, String> paraMap){
    	return tstoreTradeDao.deleteByYearMonth(paraMap);
    }
    
    public List<Map<String, Object>> queryTStoreTradesSumByMonth(Map<String, String> paraMap) throws Exception{
        
    	return tstoreTradeDao.queryTStoreTradesSumByMonth(paraMap);
    }

    public int addTStoreTradesByMassOrder(Map<String, String> paraMap){
        return tstoreTradeDao.addTStoreTradesByMassOrder(paraMap);
    }
    
    public int addPreTStoreTradesByMassOrder(Map<String, String> paraMap){
        return tstoreTradeDao.addPreTStoreTradesByMassOrder(paraMap);
    }
}
