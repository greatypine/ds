package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.CustomerMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
* @author CaoPs
* @date 2017年3月14日
* @version 1.0
* 说明: 单体画像接口业务处理
*/ 
@Service("CustomerService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class CustomerService {

    @Autowired
    CustomerMapper customerDao;

    /**
     * 按国安侠返回第一档数据
     */
    public List<Map<String, String>> queryFirst(Map<String, String> paraMap) throws Exception{
        return customerDao.queryFirst(paraMap);
    }

    /**
     * 按国安侠返回第二档数据
     */
    public List<Map<String, String>> querySecond(Map<String, String> paraMap) throws Exception{
        return customerDao.querySecond(paraMap);
    }
    
    /**
     * 按国安侠返回第三档数据
     */
    public List<Map<String, String>> queryThird(Map<String, String> paraMap) throws Exception{
        return customerDao.queryThird(paraMap);
    }
    
    /**
     * 按门店返回第一档数据
     */
    public List<Map<String, String>> queryFirstByStore(Map<String, String> paraMap) throws Exception{
        return customerDao.queryFirstByStore(paraMap);
    }

    /**
     * 按门店返回第二档数据
     */
    public List<Map<String, String>> querySecondByStore(Map<String, String> paraMap) throws Exception{
        return customerDao.querySecondByStore(paraMap);
    }
    
    /**
     * 按门店返回第三档数据
     */
    public List<Map<String, String>> queryThirdByStore(Map<String, String> paraMap) throws Exception{
        return customerDao.queryThirdByStore(paraMap);
    }
    
    /**
     * 
     * TODO  按日查询门店用户画像 
     * 2017年8月4日
     * @author gaobaolei
     * @param paraMap
     * @return
     */
    public List<Map<String, String>> queryCustomerSecondStoreByDate(Map<String, String> paraMap){
    	return customerDao.queryCustomerSecondStoreByDate(paraMap);
    }
    /**
     * 
     * TODO  按日查询门店用户画像 
     * 2017年8月4日
     * @author gaobaolei
     * @param paraMap
     * @return
     */
    public List<Map<String, String>> queryCustomerSecondStoreSumByDate(Map<String, String> paraMap){
    	return customerDao.queryCustomerSecondStoreSumByDate(paraMap);
    }
    
    public List<Map<String, String>> queryCustomerSecondEmployeeSumByDate(Map<String, String> param){
    	return customerDao.queryCustomerSecondEmployeeSumByDate(param);
    }
    
}
