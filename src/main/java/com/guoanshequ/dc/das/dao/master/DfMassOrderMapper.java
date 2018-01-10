package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DfMassOrder;

@Repository
@DataSource("master")
public interface DfMassOrderMapper {

	String queryMaxSignedTime();

	Integer addDfMassOrderDaily(@Param("dfMassOrderList")List<DfMassOrder> dfMassOrderList);
	
	Integer addDfMassOrderMonthly(@Param("dfMassOrderList")List<DfMassOrder> dfMassOrderList);
	
	Integer addDfMassOrderTotal(@Param("dfMassOrderList")List<DfMassOrder> dfMassOrderList);
	
	Integer deleteDfMassOrderDaily(String dateTime);
	
	Integer deleteDfMassOrderMonthly(String dateTime);
	
	Integer updateDfMassOrderDaily(Map<String, String> paraMap);
	
	Integer updateDfMassOrderMonthly(Map<String, String> paraMap);
	
	Integer updateDfMassOrderTotal(Map<String, String> paraMap);
	
	Integer updateAbnormalOrder(Map<String, String> paraMap);
	
	Integer updateAbnormalOrderToNormal(Map<String, String> paraMap);
	
}
