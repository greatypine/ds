package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.ScoreRecordMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年7月24日
* @version 1.0
* 说明: 店长打分：拉新、营业额、等
 */
@Service("ScoreRecordService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class ScoreRecordService {

    @Autowired
    ScoreRecordMapper scoreRecordDao;

    public List<Map<String, String>> queryScoreRecords(Map<String, String> paraMap) throws Exception{
        return scoreRecordDao.queryScoreRecords(paraMap);
    }

}
