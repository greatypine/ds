package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.OrderMapper;
import com.guoanshequ.dc.das.dao.slave.StoreTradeMapper;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2018年2月11日
* @version 1.0
* 说明:
 */
@Service("OrderService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class OrderService {

    @Autowired
    OrderMapper orderDao;

    public String queryIdByOrderSn(Map<String, String> paraMap){
    	
    	return orderDao.queryIdByOrderSn(paraMap);
    }
    
    public Map<String, Object> queryOrderAddressByOrderSn(Map<String, String> paraMap){
    	
    	return orderDao.queryOrderAddressByOrderSn(paraMap);
    }
    
    public String queryMaxSigntime(Map<String, String> paraMap){
    	
    	return orderDao.queryMaxSigntime(paraMap);
    }
}
