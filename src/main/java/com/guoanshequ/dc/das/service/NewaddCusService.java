package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.NewaddCusMapper;

import java.util.List;
import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2017年4月11日
* @version 1.0
* 说明:新增消费客户总量接口信息
 */
@Service("NewaddCusService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class NewaddCusService {

    @Autowired
    NewaddCusMapper newaddCusDao;

    public List<Map<String, String>> queryNewaddCus(Map<String, String> paraMap) throws Exception{
        
    	return newaddCusDao.queryNewaddCus(paraMap);
    }
    
    public List<Map<String, Object>> queryNewaddCusSumByDate(Map<String, String> paraMap)throws Exception{
        
    	return newaddCusDao.queryNewaddCusSumByDate(paraMap);
    }
}
