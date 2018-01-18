package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("master")
public interface DfOrderReturnedMapper {

	String queryMaxReturnedTime();

	Integer addDfOrderReturned(Map<String, String> paraMap);

	List<Map<String, String>> queryDfOrderReturneds(Map<String, String> paraMap);
	
}
