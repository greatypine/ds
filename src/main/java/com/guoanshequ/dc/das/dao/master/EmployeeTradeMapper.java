package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("master")
public interface EmployeeTradeMapper{
	
	List<Map<String, String>> queryEmployeeTrade(Map<String, String> paraMap);
	
	int addEmployeeTradeByMassOrder(Map<String, String> paraMap);
	
	int deleteByYearMonth(Map<String, String> paraMap);

	String queryEmployeeTradeByEmp(Map<String, String> paraMap);
	
	List<Map<String, String>> queryEmployeePesgmvByEmp(Map<String, String> paraMap);
	
	int addPreEmployeeTradeByMassOrder(Map<String, String> paraMap);
}
