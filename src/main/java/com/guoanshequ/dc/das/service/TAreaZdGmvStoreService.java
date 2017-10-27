package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.TAreaZdGmvStoreMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
* 
* @author CaoPs
* @date 2017年10月20日
* @version 1.0
* 说明:
 */
@Service("TAreaZdGmvStoreService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TAreaZdGmvStoreService {

    @Autowired
    TAreaZdGmvStoreMapper tareaZdGmvStoreDao;

    public  List<Map<String, String>> queryTAreaZdGmvStore(Map<String, String> paraMap){
    	
    	return tareaZdGmvStoreDao.queryTAreaZdGmvStore(paraMap);
    }
    
    public void addTAreaZdGmvStore(Map<String, String> paraMap){
    	tareaZdGmvStoreDao.addTAreaZdGmvStore(paraMap);
    }
    
    public int deleteByYearMonth(Map<String, String> paraMap){
    	return tareaZdGmvStoreDao.deleteByYearMonth(paraMap);
    }
    
    public int updatePubSeasByYearMonth(Map<String, String> paraMap){
    	return tareaZdGmvStoreDao.updatePubSeasByYearMonth(paraMap);
    }    
    
}
