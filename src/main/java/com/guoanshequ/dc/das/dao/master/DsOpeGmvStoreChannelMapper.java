package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@DataSource("master")
public interface DsOpeGmvStoreChannelMapper{
	
	int deleteByYearMonth(Map<String, String> paraMap);

	int addDsOpeGmvStoreChannelMonthByMassOrder(Map<String, String> paraMap);
	
}
