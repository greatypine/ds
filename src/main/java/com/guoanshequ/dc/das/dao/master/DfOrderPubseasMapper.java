package com.guoanshequ.dc.das.dao.master;


import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DfOrderPubseas;

@Repository
@DataSource("master")
public interface DfOrderPubseasMapper {

	Integer addDfOrderPubseas(DfOrderPubseas dfOrderPubseas);
	
	String queryOrderPubseasByOrderId(Map<String, String> paraMap);
	
	Integer updateOrderPubseasByOrderId(DfOrderPubseas dfOrderPubseas);
}
