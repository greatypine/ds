package com.guoanshequ.dc.das.dao.slave;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("slave")
public interface AbnormalOrderMapper{
	
	List<Map<String, String>> queryAbnorOrderA(Map<String, String> paraMap);
	
	List<Map<String, String>> queryAbnorOrderB(Map<String, String> paraMap);
	
	List<Map<String, String>> queryAbnorOrderDownA(Map<String, String> paraMap);
	
	List<Map<String, String>> queryAbnorOrderDownB(Map<String, String> paraMap);

	List<Map<String, String>> queryAbnorOrderDownC(Map<String, String> paraMap);
	
	List<Map<String, String>> queryAbnorOrderDownD(Map<String, String> paraMap);
}
