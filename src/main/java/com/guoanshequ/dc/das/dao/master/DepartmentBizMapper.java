package com.guoanshequ.dc.das.dao.master;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface DepartmentBizMapper{
	
	int deleteDeptGmvByYearMonth(Map<String, String> paraMap);
	
	int deleteDeptCusByYearMonth(Map<String, String> paraMap); 
	
	int addDeptGmvByMassOrder(Map<String, String> paraMap);
	
	int addDeptCusByMassOrder(Map<String, String> paraMap);
}
