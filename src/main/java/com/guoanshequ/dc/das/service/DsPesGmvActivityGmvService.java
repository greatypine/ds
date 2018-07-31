package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.DsPesGmvActivityMapper;

import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2018年7月12日
* @version 1.0
* 说明:绩效数据--门店221活动绩效GMV
 */
@Service("DsPesGmvActivityGmvService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DsPesGmvActivityGmvService {

    @Autowired
    DsPesGmvActivityMapper dsPesGmvActivityGmvDao;

    public int deleteByYearMonth(Map<String, String> paraMap){
    	return dsPesGmvActivityGmvDao.deleteByYearMonth(paraMap);
    }
    
    public int addDsPesGmvActivityStoreMonthByMassOrder(Map<String, String> paraMap){
        return dsPesGmvActivityGmvDao.addDsPesGmvActivityStoreMonthByMassOrder(paraMap);
    }
    
    public int addDsPesGmvActivityEmpMonthByMassOrder(Map<String, String> paraMap){
        return dsPesGmvActivityGmvDao.addDsPesGmvActivityEmpMonthByMassOrder(paraMap);
    }
    
}
