package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.StoreMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
* @author CaoPs
* @date 2017年3月9日
* @version 1.0
* 说明: 门店表业务处理
*/ 
@Service("StoreService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class StoreService {

    @Autowired
    StoreMapper storeDao;

    public List<Map<String, String>> queryStores(Map<String, String> paraMap) throws Exception{
        return storeDao.queryStores(paraMap);
    }

    public String queryStoreNumbers(){
    	return storeDao.queryStoreNumbers();
    }
}
