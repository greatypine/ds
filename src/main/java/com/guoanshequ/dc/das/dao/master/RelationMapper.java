package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface RelationMapper{
	/**
	 * 按国安侠统计拜访记录总数
	 */
	List<Map<String, String>> queryRelations(Map<String, String> paraMap);
	/**
	 * 按门店统计拜访记录总数
	 */
	List<Map<String, String>> queryRelationsByStore(Map<String, String> paraMap);
}
