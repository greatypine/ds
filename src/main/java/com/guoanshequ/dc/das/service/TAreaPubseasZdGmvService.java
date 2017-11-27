package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.TAreaPubseasZdGmvMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年11月21日
* @version 1.0
* 说明:
 */
@Service("TAreaPubseasZdGmvService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TAreaPubseasZdGmvService {

    @Autowired
    TAreaPubseasZdGmvMapper tareaPubseasZdGmvDao;

    public  List<Map<String, String>> queryTAreaPubseasZdGmv(Map<String, String> paraMap){
    	
    	return tareaPubseasZdGmvDao.queryTAreaPubseasZdGmv(paraMap);
    }
    
    public int addTAreaPubseasZdGmv(Map<String, String> paraMap){
    	
    	return tareaPubseasZdGmvDao.addTAreaPubseasZdGmv(paraMap);
    }
    
    public int deleteByYearMonth(Map<String, String> paraMap){
    	
    	return tareaPubseasZdGmvDao.deleteByYearMonth(paraMap);
    }  
    
    public int addTAreaPubseasZdGmvByStore(Map<String, String> paraMap)  throws Exception{
    	
    	return tareaPubseasZdGmvDao.addTAreaPubseasZdGmvByStore(paraMap);
    }      
}
