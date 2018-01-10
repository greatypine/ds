package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfOrderPubseasMapper;
import com.guoanshequ.dc.das.model.DfOrderPubseas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年11月13日
* @version 1.0
* 说明:
 */
@Service("DfOrderPubseasService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DfOrderPubseasService {

    @Autowired
    DfOrderPubseasMapper dfOrderPubseasDao;

    public Integer addDfOrderPubseas(DfOrderPubseas dfOrderPubseas){
    	return dfOrderPubseasDao.addDfOrderPubseas(dfOrderPubseas);
    }
    
    public String queryOrderPubseasByOrderId(Map<String, String> paraMap){
    	return dfOrderPubseasDao.queryOrderPubseasByOrderId(paraMap);
    }
    
    public Integer updateOrderPubseasByOrderId(DfOrderPubseas dfOrderPubseas){
    	return dfOrderPubseasDao.updateOrderPubseasByOrderId(dfOrderPubseas);
    }

    public String queryMaxSignedTime(){
        return dfOrderPubseasDao.queryMaxSignedTime();
    }

    public Integer addDfOrderPubseasByMassOrder(Map<String, String> paraMap){
        return dfOrderPubseasDao.addDfOrderPubseasByMassOrder(paraMap);
    }
}
