package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@DataSource("master")
public interface DsPesGmvActivityMapper{
	
	int deleteByYearMonth(Map<String, String> paraMap);

	int addDsPesGmvActivityStoreMonthByMassOrder(Map<String, String> paraMap);
	
	int addDsPesGmvActivityEmpMonthByMassOrder(Map<String, String> paraMap);
	
}
