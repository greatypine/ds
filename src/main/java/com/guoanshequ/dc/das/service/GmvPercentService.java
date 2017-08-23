package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.slave.GmvPercentMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年8月21日
* @version 1.0
* 说明:
 */
@Service("GmvPercentService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class GmvPercentService {

    @Autowired
    GmvPercentMapper gmvPercentDao;

    public  List<Map<String, String>> queryGmvPercentByDate(Map<String, String> paraMap){
    	return gmvPercentDao.queryGmvPercentByDate(paraMap);
    }
}
