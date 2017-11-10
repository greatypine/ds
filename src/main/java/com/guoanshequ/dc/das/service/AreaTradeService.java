package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.AreaTradeMapper;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年10月20日
* @version 1.0
* 说明:
 */
@Service("AreaTradeService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class AreaTradeService {

    @Autowired
    AreaTradeMapper areaTradeDao;

    public List<Map<String, String>> queryAreaTrades(Map<String, String> paraMap) throws Exception{
        
    	return areaTradeDao.queryAreaTrades(paraMap);
    }
    
    public String queryAreaTradesByOrderDaily(Map<String, String> paraMap) throws Exception{
    	
    	return areaTradeDao.queryAreaTradesByOrderDaily(paraMap);
    }
}
