package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.slave.OrderPubseasMapper;
import com.guoanshequ.dc.das.model.DfOrderPubseas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年11月13日
* @version 1.0
* 说明:
 */
@Service("OrderPubseasService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class OrderPubseasService {

    @Autowired
    OrderPubseasMapper orderPubseasDao;

    public List<DfOrderPubseas> queryOrderPubseas(Map<String, String> paraMap){
    	return orderPubseasDao.queryOrderPubseas(paraMap);
    }
}
