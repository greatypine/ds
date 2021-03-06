package com.guoanshequ.dc.das.dao.master;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DFOrderRealtime;

@Repository
@DataSource("master")
public interface DfOrderDailyMapper {
	
	public void addDfOrderDaily(String theday);
	
	List<DFOrderRealtime> queryOrdersDaily();
	
	void deleteOrderDaily();

}
