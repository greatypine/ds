package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.DfMemberCountMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("DfMemberCountService")
@Transactional(value = "master", rollbackFor = Exception.class)
public class DfMemberCountService {

	@Autowired
	DfMemberCountMapper dfMemberCountDao;
	/**
	 * 添加社员成交数据
	 * TODO 
	 * @author wuxinxin
	 */
	public Integer addDfMemberCount(Map<String, String> runMap) {
		return dfMemberCountDao.addDfMemberCount(runMap);
	}
	/**
	 * 修改社员成交额  成交量
	 * TODO 
	 * @author wuxinxin
	 */
	public Integer updateMemberCount(Map<String, String> runMap) {
		return dfMemberCountDao.updateDfMemberCount(runMap);
	}
	
	/**
	 * 删除社员最受欢迎、无人问津商品
	 * TODO 
	 * @author wuxinxin
	 */
	public void deletMembers(List<String> fileIdList) {
		dfMemberCountDao.deleteDfMemberCount(fileIdList);
	}
	/**
	 * 添加订单产品分类统计
	 * TODO 
	 * @author wuxinxin
	 */
	public void addProductCount(Map<String, String> countMap) {
		
		dfMemberCountDao.addProductCount(countMap);
	}
	/**
	 * 添加安心合作社
	 * TODO 
	 * @author wuxinxin
	 */
	public void addEshopInfo(Map<String, String> eshopMap) {
		dfMemberCountDao.addEshopInfo(eshopMap);
	}
	/**
	 * 删除安心合作社
	 * TODO 
	 * @author wuxinxin
	 */
	public void delEshopInfo(Map<String, String> eshopMap) {
		dfMemberCountDao.delEshopInfo(eshopMap);
		
	}
	
	/**
	 * 查询安心合作社每天每小时订单量
	 * TODO 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> queryOrderHourCount(Map<String, String> paraMap) {
		return dfMemberCountDao.queryOrderHourCount(paraMap);
	}
	/**
	 * 添加数据到按小时订单量表中
	 * TODO 
	 * @author wuxinxin
	 */
	public void addOrderHourCount(Map<String, String> paraMap) {
		 dfMemberCountDao.addOrderHourCount(paraMap);
		
	}
	
}
