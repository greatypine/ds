package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
@DataSource("master")
public interface TargetValueMapper {

	List<Map<String,String>> queryTargetValues(Map<String, String> paraMap);
	
    void addTargetValues(Map<String, String> paraMap);
    
}
