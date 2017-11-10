package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.slave.AreaZdGmvMapper;

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
@Service("AreaZdGmvService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class AreaZdGmvService {

    @Autowired
    AreaZdGmvMapper areaZdGmvDao;

    public  List<Map<String, String>> queryAreaZdGmv(Map<String, String> paraMap){
    	
    	return areaZdGmvDao.queryAreaZdGmv(paraMap);
    }
    
    public String queryAreaZdGmvByOrderDaily(Map<String, String> paraMap){
    	
    	return areaZdGmvDao.queryAreaZdGmvByOrderDaily(paraMap);
    }
}
