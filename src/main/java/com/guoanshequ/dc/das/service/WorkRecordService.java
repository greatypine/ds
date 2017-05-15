package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.WorkRecordMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
* @author CaoPs
* @date 2017年3月10日
* @version 1.0
* 说明: 个人绩效员工考勤打分业务处理
*/ 
@Service("WorkRecordService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class WorkRecordService {

    @Autowired
    WorkRecordMapper workRecordDao;

    public List<Map<String, String>> queryWorkRecords(Map<String, String> paraMap) throws Exception{
        return workRecordDao.queryWorkRecords(paraMap);
    }

}
