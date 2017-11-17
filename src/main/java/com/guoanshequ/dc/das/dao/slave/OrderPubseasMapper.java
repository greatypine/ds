package com.guoanshequ.dc.das.dao.slave;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DfOrderPubseas;

@Repository
@DataSource("slave")
public interface OrderPubseasMapper{
	
	List<DfOrderPubseas> queryOrderPubseas(Map<String, String> paraMap);
	
}
