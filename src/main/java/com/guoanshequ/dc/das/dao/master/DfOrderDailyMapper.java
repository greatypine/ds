package com.guoanshequ.dc.das.dao.master;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface DfOrderDailyMapper {
	
	public void addDfOrderDaily(String theday);

}
