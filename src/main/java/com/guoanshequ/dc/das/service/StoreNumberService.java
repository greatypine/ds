package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.slave.StoreNumberMapper;

import java.util.List;
import java.util.Map;

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

    /**
     * 平台门店number字段，用逗号分开
     */
    public  String queryStoreNumbers(){
    	List<String> storeNumList = storeNumberDao.queryStoreNumbers();
    	String storeNumStr = String.join(",", storeNumList); 
    	return storeNumStr;
    }
    
    /**
     * 平台门店编号storeno字段，用逗号分开 
     */
    public String queryStoreNoes(){
    	List<String> storeNoesList = storeNumberDao.queryStoreNoes();
    	String storeNoesStr = String.join(",", storeNoesList);
    	return storeNoesStr;
    }
    /**
     * 统计门店总数
     */
    public Integer queryStoreSum(Map<String, String> paraMap){
    	return storeNumberDao.queryStoreSum(paraMap);
    }
    /**
     * 通过id查询平台对应的number
     */
    public Integer queryStoreNumberById(String store_id) {
    	return storeNumberDao.queryStoreNumberById(store_id);
    }
}
