package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.TinyVillage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("master")
public interface TinyVillageMapper {


	Map<String, Object> queryTinyidByCode(String code);

	List<TinyVillage> findTinyVillageByVillageId(Long village_id);

	/**
	 * 根据小区名字和社区id查询小区信息
	 * @param tinyVillage
	 * @return
     */
	TinyVillage findTinyVillageByVillageIdAndName(TinyVillage tinyVillage);

	void insertTinyVillage(TinyVillage tinyVillage);

	void updateTinyVillage(TinyVillage tinyVillage);

	void updateTinyCode(Map<String, Object> map);

	void insertTinyCode(Map<String, Object> map);

	Map<String,Object> queryTownById(Long id);

	Map<String,Object> queryTinyCodeinfoBytinyId(Long tiny_village_id);

	Integer findMaxTinyVillageCode(String gb_code);


}
