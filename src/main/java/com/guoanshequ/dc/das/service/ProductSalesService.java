package com.guoanshequ.dc.das.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.ProductCityMapper;

/**
 * @author：chenchuang
 * @date:2018年6月29日
 *
 * @version V1.0
 */
@Service("ProductSalesService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class ProductSalesService {

	@Autowired
    ProductCityMapper productCityDao;

    public List<Map<String, String>> queryProductDay(Map<String, String> paraMap){
    	return productCityDao.queryProductDay(paraMap);
    }
    
    public Map<String, String> queryProductStore(Map<String, String> paraMap){
    	return productCityDao.queryProductStore(paraMap);
    }
    
    public Map<String, String> queryProductMapping(Map<String, String> paraMap){
    	return productCityDao.queryProductMapping(paraMap);
    }
   
    public Map<String, String> queryProductEshopName(Map<String, String> paraMap){
    	return productCityDao.queryProductEshopName(paraMap);
    }
    
    public Map<String, String> queryProductCityName(Map<String, String> paraMap){
    	return productCityDao.queryProductCityName(paraMap);
    }
    
    public Map<String, String> queryProductDeptChannelName(Map<String, String> paraMap){
    	return productCityDao.queryProductDeptChannelName(paraMap);
    }
}
