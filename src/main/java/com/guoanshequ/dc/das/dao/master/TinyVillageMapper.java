package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
@DataSource("master")
public interface TinyVillageMapper {


	Map<String, Object> queryTinyidByCode(String code);

}
