package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TNewaddCusMapper;

import java.util.List;
import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2017年4月11日
* @version 1.0
* 说明:新增消费客户总量接口信息
 */
@Service("TNewaddCusService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TNewaddCusService {

    @Autowired
    TNewaddCusMapper tnewaddCusDao;

    public List<Map<String, String>> queryTNewaddCus(Map<String, String> paraMap) throws Exception{
        
    	return tnewaddCusDao.queryTNewaddCus(paraMap);
    }
    
    public void addTNewaddCus(Map<String, String> paraMap){
    	tnewaddCusDao.addTNewaddCus(paraMap);
    }
    
    public int deleteByYearMonth(Map<String, String> paraMap){
    	return tnewaddCusDao.deleteByYearMonth(paraMap);
    }
    
    public List<Map<String, Object>> queryTNewaddCusSumByMonth(Map<String, String> paraMap) throws Exception{
        
    	return tnewaddCusDao.queryTNewaddCusSumByMonth(paraMap);
    }
}
