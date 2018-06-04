package com.guoanshequ.dc.das.dao.slave;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DfMassOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("slave")
public interface MassOrderMapper {

	List<DfMassOrder> queryMassOrderByDate(Map<String, String> paraMap);
	
	String queryMaxReturnTime();
	
	List<Map<String, String>> queryReturnOrderByDate(Map<String, String> paraMap);

	String queryMaxQueryTime();
	
	List<Map<String, String>> queryCustomerTradeTask(Map<String, String> paraMap);

	List<Map<String, String>> queryReturnOrders(Map<String, String> paraMap);

	List<Map<String, String>> queryKSeshopByName();
}
