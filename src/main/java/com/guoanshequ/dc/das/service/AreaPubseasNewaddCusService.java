package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.AreaPubseasNewaddCusMapper;

import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年11月21日
* @version 1.0
* 说明:
 */
@Service("AreaPubseasNewaddCusService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class AreaPubseasNewaddCusService {

    @Autowired
    AreaPubseasNewaddCusMapper areaPubseasNewaddCusDao;

    public List<Map<String, String>> queryAreaPubseasNewaddCus(Map<String, String> paraMap) throws Exception{
        
    	return areaPubseasNewaddCusDao.queryAreaPubseasNewaddCus(paraMap);
    }
    
}
