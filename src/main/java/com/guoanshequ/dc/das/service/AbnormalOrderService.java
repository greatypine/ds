package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.slave.AbnormalOrderMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年9月24日
* @version 1.0
* 说明:
 */
@Service("AbnormalOrderService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class AbnormalOrderService {

    @Autowired
    AbnormalOrderMapper abnormalOrderDao;

    public List<Map<String, String>> queryAbnorOrderA(Map<String, String> paraMap) throws Exception{
        return abnormalOrderDao.queryAbnorOrderA(paraMap);
    }
    
    public List<Map<String, String>> queryAbnorOrderB(Map<String, String> paraMap) throws Exception{
        return abnormalOrderDao.queryAbnorOrderB(paraMap);
    }
    
    public List<Map<String, String>> queryAbnorOrderD1(Map<String, String> paraMap) throws Exception{
        return abnormalOrderDao.queryAbnorOrderD1(paraMap);
    }
    
    public List<Map<String, String>> queryAbnorOrderD2(Map<String, String> paraMap) throws Exception{
        return abnormalOrderDao.queryAbnorOrderD2(paraMap);
    }
    
    public List<Map<String, String>> queryAbnorOrderF(Map<String, String> paraMap) throws Exception{
        return abnormalOrderDao.queryAbnorOrderF(paraMap);
    }

}
