package com.guoanshequ.dc.das.dao.master;


import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DfOrderPubseas;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@DataSource("master")
public interface DfOrderPubseasMapper {

	Integer addDfOrderPubseas(DfOrderPubseas dfOrderPubseas);
	
	String queryOrderPubseasByOrderId(Map<String, String> paraMap);
	
	Integer updateOrderPubseasByOrderId(DfOrderPubseas dfOrderPubseas);

	String queryMaxSignedTime();

	Integer addDfOrderPubseasByMassOrder(Map<String, String> paraMap);

}
