package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TAreaPubseasTradeStoreMapper;

import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年11月21日
* @version 1.0
* 说明:
 */
@Service("TAreaPubseasTradeStoreService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TAreaPubseasTradeStoreService {

    @Autowired
    TAreaPubseasTradeStoreMapper tareaPubseasTradeStoreDao;

    public int deleteByYearMonth(Map<String, String> paraMap){
    	
    	return tareaPubseasTradeStoreDao.deleteByYearMonth(paraMap);
    }    
}
