package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TGmvPercentMapper;
import com.guoanshequ.dc.das.dao.master.TSendOrderMapper;
import com.guoanshequ.dc.das.dao.master.TSendOrderSumMapper;

import java.util.List;
import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2017年8月28日
* @version 1.0
* 说明:
 */
@Service("TGmvPercentService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TGmvPercentService {

    @Autowired
    TGmvPercentMapper tgmvPercentDao;

    public List<Map<String, String>> queryTGmvPercent(Map<String, String> paraMap) throws Exception{
        
    	return tgmvPercentDao.queryTGmvPercent(paraMap);
    }
}
