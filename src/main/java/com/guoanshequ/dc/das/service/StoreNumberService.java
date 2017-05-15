package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.slave.StoreNumberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
* @author CaoPs
* @date 2017年4月24日
* @version 1.0
* 说明:正式数据库上的t_store表数据，获取门店号
 */
@Service("StoreNumberService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class StoreNumberService {

    @Autowired
    StoreNumberMapper storeNumberDao;

    public String queryStoreNumbers(){
    	return storeNumberDao.queryStoreNumbers();
    }
}
