package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.EmployeeTradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2018年1月23日
* @version 1.0
* 说明:国安侠gmv
 */
@Service("TEmpTradeService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class EmployeeTradeService {

    @Autowired
    EmployeeTradeMapper employeeTradeDao;

    public List<Map<String, String>> queryEmployeeTrade(Map<String, String> paraMap) throws Exception{
        
    	return employeeTradeDao.queryEmployeeTrade(paraMap);
    }
    
    public int addEmployeeTradeByMassOrder(Map<String, String> paraMap){

        return employeeTradeDao.addEmployeeTradeByMassOrder(paraMap);
    }
    
    public int deleteByYearMonth(Map<String, String> paraMap){
    	return employeeTradeDao.deleteByYearMonth(paraMap);
    }

    public String queryEmployeeTradeByEmp(Map<String, String> paraMap){
        return  employeeTradeDao.queryEmployeeTradeByEmp(paraMap);
    }
    
    public List<Map<String, String>> queryEmployeePesgmvByEmp(Map<String, String> paraMap){
        return  employeeTradeDao.queryEmployeePesgmvByEmp(paraMap);
    }
    
    public int addPreEmployeeTradeByMassOrder(Map<String, String> paraMap){

        return employeeTradeDao.addPreEmployeeTradeByMassOrder(paraMap);
    }
}
