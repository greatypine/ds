package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TAreaPubseasNewaddCusStoreMapper;

import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年11月21日
* @version 1.0
* 说明:
 */
@Service("TAreaPubseasNewaddCusStoreService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TAreaPubseasNewaddCusStoreService {

    @Autowired
    TAreaPubseasNewaddCusStoreMapper tareaPubseasNewaddCusStoreDao;

    public int deleteByYearMonth(Map<String, String> paraMap){
    	
    	return tareaPubseasNewaddCusStoreDao.deleteByYearMonth(paraMap);
    }
    
}
