package com.guoanshequ.dc.das.dao.master;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DfCustomerMonth;

@Repository
@DataSource("master")
public interface DfCustomerMonthMapper {
			
	DfCustomerMonth queryByCustomerIdAndStoreId(Map<String,String> mapParam);
	
	void updateCustomerMonth(DfCustomerMonth dfCustomerAmount);
	
	void addDfCustomerMonth(DfCustomerMonth dfCustomerAmount);
	
}
