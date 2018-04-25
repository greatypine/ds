package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.DsOpeGmvStoreMapper;

import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2018年4月23日
* @version 1.0
* 说明:运营数据--门店交易额按月度取数据
 */
@Service("DsOpeGmvStoreService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DsOpeGmvStoreService {

    @Autowired
    DsOpeGmvStoreMapper dsOpeGmvStoreDao;

    public int deleteByYearMonth(Map<String, String> paraMap){
    	return dsOpeGmvStoreDao.deleteByYearMonth(paraMap);
    }
    
    public int addDsOpeGmvStoreMonthByMassOrder(Map<String, String> paraMap){
        return dsOpeGmvStoreDao.addDsOpeGmvStoreMonthByMassOrder(paraMap);
    }
    
}
