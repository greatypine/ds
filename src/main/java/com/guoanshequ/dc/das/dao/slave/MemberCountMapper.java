package com.guoanshequ.dc.das.dao.slave;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("slave")
public interface MemberCountMapper{
	/**
	 * 查询成交量
	 * TODO 
	 * @author wuxinxin
	 */
	List<Map<String, Object>> queryMemberCount(Map<String, String> paraMap);
	/**
	 * 查询成交额
	 * TODO 
	 * @author wuxinxin
	 */
	List<Map<String, Object>> queryMemberSum(Map<String, String> paraMap);
	/**
	 * 查询最受欢迎商品
	 * TODO 
	 * @author wuxinxin
	 */
	List<Map<String, Object>> queryhotProduct(Map<String, String> paraMap);
	/**
	 * 查询无人问津商品
	 * TODO 
	 * @author wuxinxin
	 */
	List<Map<String, Object>> querycoolProduct(Map<String, String> paraMap);
	/**
	 * 查询查询动销商品量
	 * TODO 
	 * @author wuxinxin
	 */
	List<Map<String, Object>> queryMovingPin(Map<String, String> paraMap);
	/**
	 * 查询e店订单量、成交额
	 * TODO 
	 * @author wuxinxin
	 */
	List<Map<String, Object>> queryEshopCount(Map<String, String> paraMap);
	
	/**
	 * 查询e店 社员、非社员成交额
	 * TODO 
	 * @author wuxinxin
	 */
	List<Map<String, Object>> queryAllEshopSum(Map<String, String> paraMap);
	
	/**
	 * 查询e店 社员成交额
	 * TODO 
	 * @author wuxinxin
	 */
	List<Map<String, Object>> queryYesEshopSum(Map<String, String> paraMap);
	
	/**
	 * 查询e店 非社员成交额
	 * TODO 
	 * @author wuxinxin
	 */
	List<Map<String, Object>> queryNoEshopSum(Map<String, String> paraMap);
	/**
	 * 查询E店所属城市
	 * TODO 
	 * @author wuxinxin
	 */
	List<Map<String, Object>> queryEshopCity(Map<String, String> paraMap);
	/**
	 * 查询商品订单分类
	 * TODO 
	 * @author wuxinxin
	 */
	List<Map<String, Object>> queryProductCount(Map<String, String> paraMap);
	
	
	/**
	 * 查询安心合作社
	 * TODO 
	 * @author wuxinxin
	 */
	List<Map<String, Object>> queryEshopInfo(Map<String, String> paraMap);
	
}
