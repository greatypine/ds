package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.TargetValueMapper;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("TargetValueService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TargetValueService {
    @Autowired
    TargetValueMapper targetValueDao;

    public List<Map<String, String>> queryTargetValues(Map<String, String> paraMap){
    	return targetValueDao.queryTargetValues(paraMap);
    }
    
    public void addTargetValues(Map<String, String> paraMap){
        targetValueDao.addTargetValues(paraMap);
    }
    
}

