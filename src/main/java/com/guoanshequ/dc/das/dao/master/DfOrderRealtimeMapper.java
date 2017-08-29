package com.guoanshequ.dc.das.dao.master;


import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DFOrderRealtime;

@Repository
@DataSource("master")
public interface DfOrderRealtimeMapper {

	void addDfOrderRealtimeByOrderId(DFOrderRealtime dfOrderRealtimeList);
	
	void deleteDfOrdersRealtimeByOrderId(String orderid);
	
}
