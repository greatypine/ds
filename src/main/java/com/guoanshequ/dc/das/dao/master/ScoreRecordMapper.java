package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface ScoreRecordMapper {

	List<Map<String, String>> queryScoreRecords(Map<String, String> paraMap);
}
