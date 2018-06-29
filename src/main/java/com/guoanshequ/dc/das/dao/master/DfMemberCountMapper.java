package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface DfMemberCountMapper {
	
	/**
	 * TODO 添加社员成交数据
	 * @author wuxinxin
	 */
	public Integer addDfMemberCount(Map<String, String> runMap);
	
	/**
	 * TODO 更新社员成交量、成交额
	 * @author wuxinxin
	 */
	public Integer updateDfMemberCount(Map<String, String> runMap);

	/**
	 * TODO  删除社员统计信息
	 * @author wuxinxin
	 */
	public void deleteDfMemberCount(List<String> fileIdList);

	public void addProductCount(Map<String, String> countMap);

	public void addEshopInfo(Map<String, String> eshopMap);

	public void delEshopInfo(Map<String, String> eshopMap);

	public List<Map<String, Object>> queryOrderHourCount(Map<String, String> paraMap);

	public Integer addOrderHourCount(Map<String, String> paraMap);
	
}
