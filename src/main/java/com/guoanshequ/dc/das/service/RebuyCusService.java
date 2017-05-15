package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.RebuyCusMapper;

import java.util.List;
import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2017年4月11日
* @version 1.0
* 说明:复购用户接口信息
 */
@Service("RebuyCusService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class RebuyCusService {

    @Autowired
    RebuyCusMapper rebuyCusDao;

    public List<Map<String, String>> queryRebuyCus(Map<String, String> paraMap) throws Exception{
        
    	return rebuyCusDao.queryRebuyCus(paraMap);
    }
}
