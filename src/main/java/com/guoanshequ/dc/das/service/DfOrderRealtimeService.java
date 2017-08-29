package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfOrderRealtimeMapper;
import com.guoanshequ.dc.das.dao.slave.OrderRealtimeMapper;
import com.guoanshequ.dc.das.model.DFOrderRealtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年8月25日
* @version 1.0
* 说明:
 */
@Service("DfOrderRealtimeService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DfOrderRealtimeService {

    @Autowired
    DfOrderRealtimeMapper dfOrderRealtimeDao;

    public void addDfOrderRealtimeByOrderId(DFOrderRealtime dfOrderRealtimeList){
    	dfOrderRealtimeDao.addDfOrderRealtimeByOrderId(dfOrderRealtimeList);
    }
    
    public void deleteDfOrderRealtimeByOrderId(String orderid){
    	dfOrderRealtimeDao.deleteDfOrdersRealtimeByOrderId(orderid);
    }
}
