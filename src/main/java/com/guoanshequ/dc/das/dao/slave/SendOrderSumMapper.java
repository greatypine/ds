package com.guoanshequ.dc.das.dao.slave;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("slave")
public interface SendOrderSumMapper{
	
	List<Map<String, String>> querySendOrderSum(Map<String, String> paraMap);
}
