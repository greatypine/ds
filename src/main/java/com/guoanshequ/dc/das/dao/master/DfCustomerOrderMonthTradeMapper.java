package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("master")
public interface DfCustomerOrderMonthTradeMapper {

	String queryNextBeginTime();

	String queryCustomerMaxCount(Map<String, String> paraMap);
	
	Integer updateNextBeginTime(String begintime);

	Integer addDfCustomerOrderMonthTrade (Map<String, String> paraMap);
	
	Integer addDfCustomerOrderMonthTrades (List<Map<String, String>> resultList);
	
}
