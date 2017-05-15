package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.FerryPushMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 
* @author CaoPs
* @date 2017年4月17日
* @version 1.0
* 说明:摆渡车接口信息
 */
@Service("FerryPushService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class FerryPushService {

    @Autowired
    FerryPushMapper ferryPushDao;

    public List<Map<String, String>> queryFerryPushes(Map<String, String> paraMap) throws Exception{
        return ferryPushDao.queryFerryPushes(paraMap);
    }

}
