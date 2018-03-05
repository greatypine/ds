package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DsCronTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2018年1月22日
* @version 1.0
* 说明:任务调度
 */
@Service("DsCronTaskService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class DsCronTaskService {

    @Autowired
    DsCronTaskMapper dsCronTaskDao;

    public List<Map<String, String>> queryDsCronTask(Map<String, String> paraMap) throws Exception{
        return dsCronTaskDao.queryDsCronTask(paraMap);
    }

    public Map<String, String> queryDsCronTaskById(int id) throws Exception{
        return dsCronTaskDao.queryDsCronTaskById(id);
    }

    public Integer updateTaskStatusById(Map<String, String> paraMap) throws Exception{
    	return dsCronTaskDao.updateTaskStatusById(paraMap);
    }
    
}
