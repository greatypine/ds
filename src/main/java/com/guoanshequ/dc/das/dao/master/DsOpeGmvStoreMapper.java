package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@DataSource("master")
public interface DsOpeGmvStoreMapper{
	
	int deleteByYearMonth(Map<String, String> paraMap);

	int addDsOpeGmvStoreMonthByMassOrder(Map<String, String> paraMap);
	
}
