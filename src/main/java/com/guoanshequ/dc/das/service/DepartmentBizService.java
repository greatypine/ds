package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.master.DepartmentBizMapper;

import java.util.Map;



/**
 * 
 * @author CaoPS
 * 统计事业群业务:事业群gmv与事业群用户数
 * 
 */
@Service("DepartmentBizService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DepartmentBizService {

    @Autowired
    DepartmentBizMapper departmentBizDao;

    public int deleteDeptCusByYearMonth(Map<String, String> paraMap){
    	return departmentBizDao.deleteDeptCusByYearMonth(paraMap);
    }
    
    public int deleteDeptGmvByYearMonth(Map<String, String> paraMap){
    	return departmentBizDao.deleteDeptGmvByYearMonth(paraMap);
    }    
    
    public int addDeptGmvByMassOrder(Map<String, String> paraMap) {
    	return departmentBizDao.addDeptGmvByMassOrder(paraMap);
    }
    
    public int addDeptCusByMassOrder(Map<String, String> paraMap) {
    	return departmentBizDao.addDeptCusByMassOrder(paraMap);
    }   
    
}
