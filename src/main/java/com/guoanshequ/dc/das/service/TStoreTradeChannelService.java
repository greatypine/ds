package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TStoreTradeChannelMapper;

import java.util.List;
import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2017年8月28日
* @version 1.0
* 说明:
 */
@Service("TStoreTradeChannelService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TStoreTradeChannelService {

    @Autowired
    TStoreTradeChannelMapper tstoreTradeChannelDao;

    public List<Map<String, String>> queryTStoreTradeChannel(Map<String, String> paraMap) throws Exception{
        
    	return tstoreTradeChannelDao.queryTStoreTradeChannel(paraMap);
    }
    
    public void addTStoreTradeChannel(Map<String, String> paraMap){
    	tstoreTradeChannelDao.addTStoreTradeChannel(paraMap);
    }
    
    public int deleteByYearMonth(Map<String, String> paraMap){
    	return tstoreTradeChannelDao.deleteByYearMonth(paraMap);
    }
}
