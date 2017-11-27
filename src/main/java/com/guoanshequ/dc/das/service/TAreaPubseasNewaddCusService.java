package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TAreaPubseasNewaddCusMapper;

import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年11月21日
* @version 1.0
* 说明:
 */
@Service("TAreaPubseasNewaddCusService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TAreaPubseasNewaddCusService {

    @Autowired
    TAreaPubseasNewaddCusMapper tareaPubseasNewaddCusDao;

    public List<Map<String, String>> queryTAreaPubseasNewaddCus(Map<String, String> paraMap) throws Exception{
        
    	return tareaPubseasNewaddCusDao.queryTAreaPubseasNewaddCus(paraMap);
    }
    
    public int addTAreaPubseasNewaddCus(Map<String, String> paraMap)  throws Exception{
    	
    	return tareaPubseasNewaddCusDao.addTAreaPubseasNewaddCus(paraMap);
    }
    
    public int deleteByYearMonth(Map<String, String> paraMap)  throws Exception{
    	
    	return tareaPubseasNewaddCusDao.deleteByYearMonth(paraMap);
    }
    
    public int addTAreaPubseasNewaddCusByStore(Map<String, String> paraMap)  throws Exception{
    	
    	return tareaPubseasNewaddCusDao.addTAreaPubseasNewaddCusByStore(paraMap);
    }
}
