package com.guoanshequ.dc.das.dao.slave;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DfCustomerAmount;

@Repository
@DataSource("slave")
public interface CustomerAmountMapper {
	
	List<Map<String, String>> queryCustomerAmount();

}
