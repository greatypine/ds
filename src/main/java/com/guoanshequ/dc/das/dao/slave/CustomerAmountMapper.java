package com.guoanshequ.dc.das.dao.slave;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DfCustomerMonth;
import com.guoanshequ.dc.das.model.DfValidCustomer;

@Repository
@DataSource("slave")
public interface CustomerAmountMapper {
	
	public List<DfValidCustomer> queryValidCustomer();
	
	public List<Map<String,String>> queryCustomerStore();
	
	public List<DfCustomerMonth> queryCustomerMonth();

}
