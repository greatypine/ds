package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.RelationMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;



/**
* @author CaoPs
* @date 2017年3月16日
* @version 1.0
* 说明: 拜访记录接口信息
*/ 
@Service("RelationService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class RelationService {

    @Autowired
    RelationMapper relationDao;

    public List<Map<String, String>> queryRelations(Map<String, String> paraMap) throws Exception{
        
    	return relationDao.queryRelations(paraMap);
    }
    
    public List<Map<String, String>> queryRelationsByStore(Map<String, String> paraMap) throws Exception{
        
    	return relationDao.queryRelationsByStore(paraMap);
    }
}
