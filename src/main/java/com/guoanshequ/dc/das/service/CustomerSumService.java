package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.CustomerSumMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2018年3月20日
* @version 1.0
* 说明:
 */
@Service("CustomerSumService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class CustomerSumService {

    @Autowired
    CustomerSumMapper customerSumDao;

    public Integer addCusumMonth(Map<String, String> paraMap) throws Exception{
        return customerSumDao.addCusumMonth(paraMap);
    }
    
    public Integer addCusumDay(Map<String, String> paraMap) throws Exception{
        return customerSumDao.addCusumDay(paraMap);
    }
    
    public Integer deleteCusumMonthByMonth(Map<String, String> paraMap) throws Exception{
    	return customerSumDao.deleteCusumMonthByMonth(paraMap);
    }
    
}
