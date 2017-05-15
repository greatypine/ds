package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TRebuyCusMapper;

import java.util.List;
import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2017年4月12日
* @version 1.0
* 说明:复购用户按月度获取信息
 */
@Service("TRebuyCusService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TRebuyCusService {

    @Autowired
    TRebuyCusMapper trebuyCusDao;

    public List<Map<String, String>> queryTRebuyCus(Map<String, String> paraMap) throws Exception{
        
    	return trebuyCusDao.queryTRebuyCus(paraMap);
    }
    
    public void addTRebuyCus(Map<String, String> paraMap){
    	trebuyCusDao.addTRebuyCus(paraMap);
    }
}
