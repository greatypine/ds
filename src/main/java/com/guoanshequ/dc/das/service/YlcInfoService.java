package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.MemberCountMapper;
import com.guoanshequ.dc.das.dao.ylc.YanglcMapper;

import java.util.List;
import java.util.Map;


/**
 * 
* @author wuxinxin
* @date 2018年5月28日
* @version 1.0
* 说明:查询社员成交信息
 */
@Service("YlcInfoService")
@Transactional(value = "ylc",rollbackFor = Exception.class)
public class YlcInfoService {

    @Autowired
    YanglcMapper ylcInfoDao;
    /**
     * 查询养老餐用户
     * TODO 
     * @author wuxinxin
     */
    public List<Map<String, Object>> queryYlcUser(Map<String, String> paraMap){
    	
    	return ylcInfoDao.queryYlcUser(paraMap);
    }
    /**
     * 查询养老餐订单
     * TODO 
     * @author wuxinxin
     */
    public List<Map<String, Object>> queryYlcOrder(Map<String, String> paraMap){
    	
    	return ylcInfoDao.queryYlcOrder(paraMap);
    }
    
}
