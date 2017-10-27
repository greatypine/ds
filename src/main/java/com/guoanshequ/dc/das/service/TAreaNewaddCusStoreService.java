package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TAreaNewaddCusStoreMapper;

import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年10月20日
* @version 1.0
* 说明:
 */
@Service("TAreaNewaddCusStoreService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TAreaNewaddCusStoreService {

    @Autowired
    TAreaNewaddCusStoreMapper tareaNewaddCusStoreDao;

    public List<Map<String, String>> queryTAreaNewaddCusStore(Map<String, String> paraMap) throws Exception{
        
    	return tareaNewaddCusStoreDao.queryTAreaNewaddCusStore(paraMap);
    }
    
    public void addTAreaNewaddCusStore(Map<String, String> paraMap){
    	tareaNewaddCusStoreDao.addTAreaNewaddCusStore(paraMap);
    }
    
    public int deleteByYearMonth(Map<String, String> paraMap){
    	return tareaNewaddCusStoreDao.deleteByYearMonth(paraMap);
    }
    
    public int updatePubSeasByYearMonth(Map<String, String> paraMap){
    	return tareaNewaddCusStoreDao.updatePubSeasByYearMonth(paraMap);
    }
}
