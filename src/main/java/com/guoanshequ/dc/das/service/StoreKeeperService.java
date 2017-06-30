package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.StoreKeeperMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
* @author CaoPs
* @date 2017年3月9日
* @version 1.0
* 说明: 店长表业务处理
*/ 
@Service("StoreKeeperService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class StoreKeeperService {

    @Autowired
    StoreKeeperMapper storeKeeperDao;

    public List<Map<String, String>> queryStoreKeepers(Map<String, String> paraMap) throws Exception{
        return storeKeeperDao.queryStoreKeepers(paraMap);
    }

    public List<Map<String, String>> queryPreStoreKeepers(Map<String, String> paraMap) throws Exception{
        return storeKeeperDao.queryPreStoreKeepers(paraMap);
    }
}
