package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.RewardTimesMapper;

import java.util.List;
import java.util.Map;

/**
 * 
* @author CaoPs
* @date 2017年7月20日
* @version 1.0
* 说明:国安侠好评打赏次数
 */
@Service("RewardTimesService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class RewardTimesService {

    @Autowired
    RewardTimesMapper rewardTimesDao;

    public List<Map<String, String>> queryRewardTimes(Map<String, String> paraMap) throws Exception{
        
    	return rewardTimesDao.queryRewardTimes(paraMap);
    }
}
