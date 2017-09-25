package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TAbnormalOrderMapper;
import com.guoanshequ.dc.das.dao.master.TSendOrderSumMapper;

import java.util.List;
import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2017年9月24日
* @version 1.0
* 说明:
 */
@Service("TAbnormalOrderService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TAbnormalOrderService {

    @Autowired
    TAbnormalOrderMapper tabnormalOrderDao;

    public String queryTAbnorOrdersByOrdersn(Map<String, String> paraMap){
    	return tabnormalOrderDao.queryTAbnorOrdersByOrdersn(paraMap);
    }
    
    public List<Map<String, String>> queryTAbnorOrders(Map<String, String> paraMap) throws Exception{
    	return tabnormalOrderDao.queryTAbnorOrders(paraMap);
    }
    
    public void addAbnorOrders(Map<String, String> paraMap){
    	tabnormalOrderDao.addAbnorOrders(paraMap);
    }
    
    public int deleteAbnorOrdersByYearMonth(Map<String, String> paraMap){
    	return tabnormalOrderDao.deleteAbnorOrdersByYearMonth(paraMap);
    }
    
    
    public String queryTAbnorDownByOrdersn(Map<String, String> paraMap){
    	return tabnormalOrderDao.queryTAbnorDownByOrdersn(paraMap);
    }
    
    public List<Map<String, String>> queryTAbnorDown(Map<String, String> paraMap) throws Exception{
    	return tabnormalOrderDao.queryTAbnorDown(paraMap);
    }
    
    public void addAbnorDown(Map<String, String> paraMap){
    	tabnormalOrderDao.addAbnorDown(paraMap);
    }
    
    public int deleteAbnorDownByYearMonth(Map<String, String> paraMap){
    	return tabnormalOrderDao.deleteAbnorDownByYearMonth(paraMap);
    }

}
