package com.guoanshequ.dc.das.dao.master;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DfMassOrder;

@Repository
@DataSource("master")
public interface DfMassOrderMapper {

	String queryMaxSignedTime();

	Integer addDfMassOrderDaily(DfMassOrder dfMassOrder);
	
	Integer addDfMassOrderMonthly(DfMassOrder dfMassOrder);
	
	Integer addDfMassOrderTotal(DfMassOrder dfMassOrder);
	
	Integer deleteDfMassOrderDaily(String dateTime);
	
	Integer deleteDfMassOrderMonthly(String dateTime);
	
	Integer updateDfMassOrderDaily(Map<String, String> paraMap);
	
	Integer updateDfMassOrderMonthly(Map<String, String> paraMap);
	
	Integer updateDfMassOrderTotal(Map<String, String> paraMap);
	
	Integer updateAbnormalOrder(Map<String, String> paraMap);
	
	Integer updateAbnormalOrderToNormal(Map<String, String> paraMap);
	
}
