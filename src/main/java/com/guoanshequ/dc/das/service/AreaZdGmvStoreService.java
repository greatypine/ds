package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.slave.AreaZdGmvStoreMapper;

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
@Service("AreaZdGmvStoreService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class AreaZdGmvStoreService {

    @Autowired
    AreaZdGmvStoreMapper areaZdGmvStoreDao;

    public  List<Map<String, String>> queryAreaZdGmvStore(Map<String, String> paraMap){
    	
    	return areaZdGmvStoreDao.queryAreaZdGmvStore(paraMap);
    }
}
