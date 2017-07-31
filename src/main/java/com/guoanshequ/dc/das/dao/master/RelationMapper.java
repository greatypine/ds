package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface RelationMapper{
	/**
	 * pes系统：按国安侠统计拜访记录总数，实时数据
	 */
	List<Map<String, String>> queryRelations(Map<String, String> paraMap);
	/**
	 * pes系统：按门店统计拜访记录总数，实时数据
	 */
	List<Map<String, String>> queryRelationsByStore(Map<String, String> paraMap);
	/**
	 * 社区动态系统：按日期门店统计拜访记录总数，实时数据
	 */
	List<Map<String, String>> queryRelationsStoreByDay(Map<String, String> paraMap);
	
}
