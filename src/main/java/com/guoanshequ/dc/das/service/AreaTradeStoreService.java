package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.AreaTradeStoreMapper;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年10月20日
* @version 1.0
* 说明:
 */
@Service("AreaTradeStoreService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class AreaTradeStoreService {

    @Autowired
    AreaTradeStoreMapper areaTradeStoreDao;

    public List<Map<String, String>> queryAreaTradesStore(Map<String, String> paraMap) throws Exception{
        
    	return areaTradeStoreDao.queryAreaTradesStore(paraMap);
    }
}
