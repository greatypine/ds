package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@DataSource("master")
public interface BigScreenMapper {

	Integer queryCusnumMonthForHQ(Map<String, String> paraMap);
	
	Integer queryCusnumHistoryForHQ(Map<String, String> paraMap);

	Integer updateCusnumForHQ (Map<String, Integer> paraMap);
	
}
