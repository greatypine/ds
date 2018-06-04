package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoanshequ.dc.das.dao.slave.MemberCountMapper;

import java.util.List;
import java.util.Map;


/**
 * 
* @author wuxinxin
* @date 2018年5月28日
* @version 1.0
* 说明:查询社员成交信息
 */
@Service("MemberCountService")
@Transactional(value = "slave",rollbackFor = Exception.class)
public class MemberCountService {

    @Autowired
    MemberCountMapper memberCountDao;
    /**
     * 查询无人问津商品
     * TODO 
     * @author wuxinxin
     */
    public List<Map<String, Object>> querycoolProduct(Map<String, String> paraMap){
    	
    	return memberCountDao.querycoolProduct(paraMap);
    }
    /**
     * 查询最受欢迎商品
     * TODO 
     * @author wuxinxin
     */
    public List<Map<String, Object>> queryhotProduct(Map<String, String> paraMap){
    	
    	return memberCountDao.queryhotProduct(paraMap);
    }
    /**
     * 查询成交量
     * TODO 
     * @author wuxinxin
     */
    public List<Map<String, Object>> queryMemberCount(Map<String, String> paraMap){
    	
    	return memberCountDao.queryMemberCount(paraMap);
    }
    /**
     * 查询成交额
     * TODO 
     * @author wuxinxin
     */
    public List<Map<String, Object>> queryMemberSum(Map<String, String> paraMap){
    	
    	return memberCountDao.queryMemberSum(paraMap);
    }
    /**
     * 查询e店订单量、成交额
     * TODO 
     * @author wuxinxin
     */
    public List<Map<String, Object>> queryEshopCount(Map<String, String> paraMap){
    	
    	return memberCountDao.queryEshopCount(paraMap);
    }
    /**
     * 查询动销商品
     * TODO 
     * @author wuxinxin
     */
    public List<Map<String, Object>> queryMovingPin(Map<String, String> paraMap){
    	
    	return memberCountDao.queryMovingPin(paraMap);
    }
    /**
     * 查询e店社员成交额
     * TODO 
     * @author wuxinxin
     */
    public List<Map<String, Object>> queryYesEshopSum(Map<String, String> paraMap){
    	
    	return memberCountDao.queryYesEshopSum(paraMap);
    }
    /**
     * 查询e店社员、非社员成交额
     * TODO 
     * @author wuxinxin
     */
    public List<Map<String, Object>> queryAllEshopSum(Map<String, String> paraMap){
    	
    	return memberCountDao.queryAllEshopSum(paraMap);
    }
    /**
     * 查询e店非社员成交额
     * TODO 
     * @author wuxinxin
     */
    public List<Map<String, Object>> queryNoEshopSum(Map<String, String> paraMap){
    	
    	return memberCountDao.queryNoEshopSum(paraMap);
    }
    
}
