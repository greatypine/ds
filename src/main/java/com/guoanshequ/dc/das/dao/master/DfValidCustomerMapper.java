package com.guoanshequ.dc.das.dao.master;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DfValidCustomer;

@Repository
@DataSource("master")
public interface DfValidCustomerMapper {
	
	public DfValidCustomer queryByCustomerId(String customer_id);
	
	public void updateValidCustomer(DfValidCustomer dfValidCustomer);
	
	public void addValidCustomer(DfValidCustomer dfValidCustomer);

}
