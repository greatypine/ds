package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface FerryPushMapper {

	List<Map<String, String>> queryFerryPushes(Map<String, String> paraMap);
	
}
