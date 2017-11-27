package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.TAreaPubseasZdGmvStoreMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年11月21日
* @version 1.0
* 说明:
 */
@Service("TAreaPubseasZdGmvStoreService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TAreaPubseasZdGmvStoreService {

    @Autowired
    TAreaPubseasZdGmvStoreMapper tareaPubseasZdGmvStoreDao;

    public int deleteByYearMonth(Map<String, String> paraMap){
    	
    	return tareaPubseasZdGmvStoreDao.deleteByYearMonth(paraMap);
    }  
}
