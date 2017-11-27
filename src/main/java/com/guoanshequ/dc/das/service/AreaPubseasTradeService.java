package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.AreaPubseasTradeMapper;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年11月15日
* @version 1.0
* 说明:
 */
@Service("AreaPubseasTradeService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class AreaPubseasTradeService {

    @Autowired
    AreaPubseasTradeMapper areaPubseasTradeDao;

    public List<Map<String, String>> queryAreaPubseasTrades(Map<String, String> paraMap) throws Exception{
        
    	return areaPubseasTradeDao.queryAreaPubseasTrades(paraMap);
    }
    
}
