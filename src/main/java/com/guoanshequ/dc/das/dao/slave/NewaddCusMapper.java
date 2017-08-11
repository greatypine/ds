package com.guoanshequ.dc.das.dao.slave;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("slave")
public interface NewaddCusMapper{
	
	List<Map<String, String>> queryNewaddCus(Map<String, String> paraMap);
	
	List<Map<String, Object>> queryNewaddCusSumByDate(Map<String, String> paraMap);
}
