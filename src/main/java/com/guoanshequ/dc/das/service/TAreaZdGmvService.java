package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.TAreaZdGmvMapper;

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
@Service("TAreaZdGmvService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TAreaZdGmvService {

    @Autowired
    TAreaZdGmvMapper tareaZdGmvDao;

    public  List<Map<String, String>> queryTAreaZdGmv(Map<String, String> paraMap){
    	
    	return tareaZdGmvDao.queryTAreaZdGmv(paraMap);
    }
    
    public  List<Map<String, String>> queryTAreaZdGmvGroupByEmp(Map<String, String> paraMap){
    	
    	return tareaZdGmvDao.queryTAreaZdGmvGroupByEmp(paraMap);
    }
    
    public void addTAreaZdGmv(Map<String, String> paraMap){
    	tareaZdGmvDao.addTAreaZdGmv(paraMap);
    }
    
    public int deleteByYearMonth(Map<String, String> paraMap){
    	return tareaZdGmvDao.deleteByYearMonth(paraMap);
    }
    
    public int updatePubSeasByYearMonth(Map<String, String> paraMap){
    	return tareaZdGmvDao.updatePubSeasByYearMonth(paraMap);
    }   
    
    public int addTAreaZdGmvByStore(Map<String, String> paraMap){
    	return tareaZdGmvDao.addTAreaZdGmvByStore(paraMap);
    }  
}
