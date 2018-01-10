package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.slave.ProductCityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2018年01月09日
* @version 1.0
* 说明:
 */
@Service("ProductCityService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class ProductCityService {

    @Autowired
    ProductCityMapper productCityDao;

    public List<Map<String, String>> queryByProductCity(Map<String, String> paraMap){

    	return productCityDao.queryByProductCity(paraMap);
    }
}
