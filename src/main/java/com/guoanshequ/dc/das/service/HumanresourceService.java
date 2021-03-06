package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.HumanresourceMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 员工业务类
 * Created by liuxi on 2017/2/20.
 */
@Service("humanresourceService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class HumanresourceService {

    @Autowired
    HumanresourceMapper humanresourceDao;

    public List<Map<String, String>> queryHumanresources(Map<String, String> paraMap) throws Exception{
        return humanresourceDao.queryHumanresources(paraMap);
    }

    public List<Map<String, String>> queryPreHumanresources(Map<String, String> paraMap) throws Exception{
        return humanresourceDao.queryPreHumanresources(paraMap);
    }
    
    public int queryStoreEmpCount(String storeno) throws Exception{
        return humanresourceDao.queryStoreEmpCount(storeno);
    }
    
    
    public List<Map<String, String>> queryHumanresourcesList(Map<String, String> paraMap) throws Exception{
    	return humanresourceDao.queryHumanresourcesList(paraMap);
    }
    public List<Map<String, String>> queryAllHumanresourcesList() throws Exception{
    	return humanresourceDao.queryAllHumanresourcesList();
    }
    
    public List<Map<String, String>> queryPostList() throws Exception{
    	return humanresourceDao.queryPostList();
    }
    public List<Map<String, String>> queryStoreList() throws Exception{
    	return humanresourceDao.queryStoreList();
    }
    
    
    public String queryIdCardOfonlineByPhone(String mobilephone) {
    	return humanresourceDao.queryIdCardOfonlineByPhone(mobilephone);
    }
}
