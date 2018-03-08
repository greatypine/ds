package com.guoanshequ.dc.das.dao.slave;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("slave")
public interface CustomerOrderMonthTradeMapper {

	List<Map<String, String>> queryCustomerOrderMonthTradeBySignTime(Map<String, String> paraMap);
	
	String queryMaxSigntime();
	
}
