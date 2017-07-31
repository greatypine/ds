package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.TRewardTimesMapper;

import java.util.List;
import java.util.Map;



/**
 * 
* @author CaoPs
* @date 2017年7月25日
* @version 1.0
* 说明:国安侠好评次按月度统计
 */
@Service("TRewardTimesService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TRewardTimesService {

    @Autowired
    TRewardTimesMapper trewardTimesDao;

    public List<Map<String, String>> queryTRewardTimes(Map<String, String> paraMap) throws Exception{
        
    	return trewardTimesDao.queryTRewardTimes(paraMap);
    }
    
    public void addTRewardTimes(Map<String, String> paraMap){
    	trewardTimesDao.addTRewardTimes(paraMap);
    }
    
    public int deleteByYearMonth(Map<String, String> paraMap){
    	return trewardTimesDao.deleteByYearMonth(paraMap);
    }
}
