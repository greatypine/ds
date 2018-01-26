package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TAreaNewaddCusMapper;

import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年10月20日
* @version 1.0
* 说明:
 */
@Service("TAreaNewaddCusService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TAreaNewaddCusService {

    @Autowired
    TAreaNewaddCusMapper tareaNewaddCusDao;

    public List<Map<String, String>> queryTAreaNewaddCus(Map<String, String> paraMap) throws Exception{
        
    	return tareaNewaddCusDao.queryTAreaNewaddCus(paraMap);
    }
    
    public List<Map<String, String>> queryTAreaNewaddCusGroupByEmp(Map<String, String> paraMap) throws Exception{
        
    	return tareaNewaddCusDao.queryTAreaNewaddCusGroupByEmp(paraMap);
    }
    
    public String queryTAreaNewaddcusGroupByEmpOnMonth(Map<String, String> paraMap) throws Exception{
    	
    	return tareaNewaddCusDao.queryTAreaNewaddcusGroupByEmpOnMonth(paraMap);
    }
    
    public int addTAreaNewaddCus(Map<String, String> paraMap){
    	return tareaNewaddCusDao.addTAreaNewaddCus(paraMap);
    }
    
    public int deleteByYearMonth(Map<String, String> paraMap){
    	
    	return tareaNewaddCusDao.deleteByYearMonth(paraMap);
    }
    
    public int updatePubSeasByYearMonth(Map<String, String> paraMap){
    	return tareaNewaddCusDao.updatePubSeasByYearMonth(paraMap);
    }
    
    public int addTAreaNewaddCusByStore(Map<String, String> paraMap){
    	return tareaNewaddCusDao.addTAreaNewaddCusByStore(paraMap);
    }

    public String queryAreaNewaddcusByEmpOnMass(Map<String, String> paraMap) throws Exception{

        return tareaNewaddCusDao.queryAreaNewaddcusByEmpOnMass(paraMap);
    }
}
