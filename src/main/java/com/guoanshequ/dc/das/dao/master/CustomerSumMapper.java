package com.guoanshequ.dc.das.dao.master;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface CustomerSumMapper {

	Integer addCusumMonth(Map<String, String> paraMap);
	
	Integer addCusumDay(Map<String, String> paraMap);
	
	Integer deleteCusumMonthByMonth(Map<String, String> paraMap);
	
	Integer addCusumMonthCity(Map<String, String> paraMap);
	
	Integer addCusumDayCity(Map<String, String> paraMap);
	
	Integer deleteCusumMonthCityByMonth(Map<String, String> paraMap);
}
