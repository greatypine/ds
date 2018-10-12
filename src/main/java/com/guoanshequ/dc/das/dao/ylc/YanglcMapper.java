package com.guoanshequ.dc.das.dao.ylc;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("ylc")
public interface YanglcMapper{
	/**
	 * 查询养老餐订单
	 * TODO 
	 * @author wuxinxin
	 */
	List<Map<String, Object>> queryYlcOrder(Map<String, String> paraMap);
	/**
	 * 查询养老餐用户数据
	 * TODO 
	 * @author wuxinxin
	 */
	List<Map<String, Object>> queryYlcUser(Map<String, String> paraMap);
	
	
}
