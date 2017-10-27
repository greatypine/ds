package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.AreaNewaddCusMapper;

import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年10月20日
* @version 1.0
* 说明:
 */
@Service("AreaNewaddCusService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class AreaNewaddCusService {

    @Autowired
    AreaNewaddCusMapper areaNewaddCusDao;

    public List<Map<String, String>> queryAreaNewaddCus(Map<String, String> paraMap) throws Exception{
        
    	return areaNewaddCusDao.queryAreaNewaddCus(paraMap);
    }
    
}
