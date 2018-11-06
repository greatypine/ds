package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.OrderMapper;
import com.guoanshequ.dc.das.model.Contract;
import com.guoanshequ.dc.das.model.OrderItemExtra;

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
    
    public OrderItemExtra queryOrderRebateCouponById(Map<String, String> paraMap){
    	
    	return orderDao.queryOrderRebateCouponById(paraMap);
    }
    
    public String queryOrderCostPriceById(Map<String, String> paraMap){
    	
    	return orderDao.queryOrderCostPriceById(paraMap);
    }
    
    public Contract queryOrderContractInfoById(String order_id){
    	
    	return orderDao.queryOrderContractInfoById(order_id);
    }    
}
