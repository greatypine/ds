package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.SendOrderMapper;

import java.util.List;
import java.util.Map;



/**
* @author CaoPs
* @date 2017年3月16日
* @version 1.0
* 说明: 上门送单量接口信息
*/ 
@Service("SendOrderService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class SendOrderService {

    @Autowired
    SendOrderMapper sendOrderDao;

    public List<Map<String, String>> querySendOrders(Map<String, String> paraMap) throws Exception{
        
    	return sendOrderDao.querySendOrders(paraMap);
    }
}
