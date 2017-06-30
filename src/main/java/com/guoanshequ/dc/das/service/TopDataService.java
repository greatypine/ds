package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.TopDataMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
* @author CaoPs
* @date 2017年6月17日
* @version 1.0
* 说明: topdate数据操作
*/ 
@Service("TopDataService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TopDataService {

    @Autowired
    TopDataMapper topDataDao;

    public int deleteTopDatas(Map<String, String> paraMap){
    	return topDataDao.deleteTopDatas(paraMap);
    }
    
	public void addHumanresources(Map<String, String> paraMap){
		topDataDao.addHumanresources(paraMap);
	}
	
    public void addStoreKeepers(Map<String, String> paraMap){
    	topDataDao.addStoreKeepers(paraMap);
    }
    
    public void updateRelationnum(Map<String, String> paraMap){
    	topDataDao.updateRelationnum(paraMap);
    }
    
    public void updateCusGrade1(Map<String, String> paraMap){
    	topDataDao.updateCusGrade1(paraMap);
    }
    
    public void updateCusGrade2(Map<String, String> paraMap){
    	topDataDao.updateCusGrade2(paraMap);
    }
    
    public void updateCusGrade3(Map<String, String> paraMap){
    	topDataDao.updateCusGrade3(paraMap);
    }
    
    public void updateWorkRecord(Map<String, String> paraMap){
    	topDataDao.updateWorkRecord(paraMap);
    }
    
    public void updateFerryPushnum(Map<String, String> paraMap){
    	topDataDao.updateFerryPushnum(paraMap);
    }
    
    public void updateStoreRelationnum(Map<String, String> paraMap){
    	topDataDao.updateStoreRelationnum(paraMap);
    }
    
    public void updateStoreCusgrade1(Map<String, String> paraMap){
    	topDataDao.updateStoreCusgrade1(paraMap);
    }
    
    public void updateStoreCusgrade2(Map<String, String> paraMap){
    	topDataDao.updateStoreCusgrade2(paraMap);
    }
    public void updateStoreCusgrade3(Map<String, String> paraMap){
    	topDataDao.updateStoreCusgrade3(paraMap);
    }
    
    public List<Map<String, String>> queryHumanResourcesOnTop(Map<String, String> paraMap) throws Exception{
        return topDataDao.queryHumanResourcesOnTop(paraMap);
    }
    
    public List<Map<String, String>> queryStoreKeeperOnTop(Map<String, String> paraMap) throws Exception{
    	return topDataDao.queryStoreKeeperOnTop(paraMap);
    }
    
    public List<Map<String, String>> queryCusgrade1OnTop(Map<String, String> paraMap) throws Exception{
        return topDataDao.queryCusgrade1OnTop(paraMap);
    }
    
    public List<Map<String, String>> queryCusgrade2OnTop(Map<String, String> paraMap) throws Exception{
    	return topDataDao.queryCusgrade2OnTop(paraMap);
    }
    
    public List<Map<String, String>> queryCusgrade3OnTop(Map<String, String> paraMap) throws Exception{
    	return topDataDao.queryCusgrade3OnTop(paraMap);
    }
    
    public List<Map<String, String>> queryStoreCusgrade1OnTop(Map<String, String> paraMap) throws Exception{
        return topDataDao.queryStoreCusgrade1OnTop(paraMap);
    }
    
    public List<Map<String, String>> queryStoreCusgrade2OnTop(Map<String, String> paraMap) throws Exception{
    	return topDataDao.queryStoreCusgrade2OnTop(paraMap);
    }
    
    public List<Map<String, String>> queryStoreCusgrade3OnTop(Map<String, String> paraMap) throws Exception{
    	return topDataDao.queryStoreCusgrade3OnTop(paraMap);
    }
    
    public List<Map<String, String>> queryFirstByStoreOnTop(Map<String, String> paraMap) throws Exception{
    	return topDataDao.queryFirstByStoreOnTop(paraMap);
    }
    
    public List<Map<String, String>> querySecondByStoreOnTop(Map<String, String> paraMap) throws Exception{
    	return topDataDao.querySecondByStoreOnTop(paraMap);
    }
    
    public List<Map<String, String>> queryThirdByStoreOnTop(Map<String, String> paraMap) throws Exception{
    	return topDataDao.queryThirdByStoreOnTop(paraMap);
    }
    
    public List<Map<String, String>> queryRelationnumOnTop(Map<String, String> paraMap) throws Exception{
    	return topDataDao.queryRelationnumOnTop(paraMap);
    }
    
    public List<Map<String, String>> queryStoreRelationnumOnTop(Map<String, String> paraMap) throws Exception{
    	return topDataDao.queryStoreRelationnumOnTop(paraMap);
    }
    
    public List<Map<String, String>> queryRelationsByStoreOnTop(Map<String, String> paraMap) throws Exception{
    	return topDataDao.queryRelationsByStoreOnTop(paraMap);
    }
    
    public List<Map<String, String>> queryWorkRecordsOnTop(Map<String, String> paraMap) throws Exception{
    	return topDataDao.queryWorkRecordsOnTop(paraMap);
    }
    
    public List<Map<String, String>> queryFerryPushOnTop(Map<String, String> paraMap) throws Exception{
    	return topDataDao.queryFerryPushOnTop(paraMap);
    }
}
