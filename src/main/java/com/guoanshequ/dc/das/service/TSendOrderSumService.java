package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TSendOrderMapper;
import com.guoanshequ.dc.das.dao.master.TSendOrderSumMapper;

import java.util.List;
import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2017年8月28日
* @version 1.0
* 说明:
 */
@Service("TSendOrderSumService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TSendOrderSumService {

    @Autowired
    TSendOrderSumMapper tsendOrderSumDao;

    public List<Map<String, String>> queryTSendOrderSum(Map<String, String> paraMap) throws Exception{
        
    	return tsendOrderSumDao.queryTSendOrderSum(paraMap);
    }
}
