package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.slave.AreaPubseasZdGmvMapper;

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
@Service("AreaPubseasZdGmvService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class AreaPubseasZdGmvService {

    @Autowired
    AreaPubseasZdGmvMapper areaPubseasZdGmvDao;

    public  List<Map<String, String>> queryAreaPubseasZdGmv(Map<String, String> paraMap){
    	
    	return areaPubseasZdGmvDao.queryAreaPubseasZdGmv(paraMap);
    }
    
}
