package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.TProductCityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2018年01月01日
* @version 1.0
* 说明:
 */
@Service("TProductCityService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TProductCityService {

    @Autowired
    TProductCityMapper tProductCityDao;

    public List<Map<String, String>> queryTProductCity(Map<String, String> paraMap) throws Exception{
        
    	return tProductCityDao.queryTProductCity(paraMap);
    }
    
    public int addTProductCity(Map<String, String> paraMap){

    	return tProductCityDao.addTProductCity(paraMap);
    }
    
    public int deleteByYearMonth(){

    	return tProductCityDao.deleteByYearMonth();
    }
    
}
