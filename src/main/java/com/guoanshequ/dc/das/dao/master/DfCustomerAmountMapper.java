package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DfCustomerAmount;

@Repository
@DataSource("master")
public interface DfCustomerAmountMapper {
	
	void addCustomerAmount(Map<String,String> mapParam);
		
	DfCustomerAmount queryByCustomerIdAndStoreIdAndYearAndMonth(DfCustomerAmount dfCustomerAmount);
	
	void updateCustomerAmount(DfCustomerAmount dfCustomerAmount);
	
	void addDfCustomerAmount(DfCustomerAmount dfCustomerAmount);
	
}
