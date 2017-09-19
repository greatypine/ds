package com.guoanshequ.dc.das.dao.master;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("master")
public interface DfCustomerStoreMapper {

	public List<Map<String,String>> queryByCustomerIdStoreId(Map<String, String> mapParam);
	
	public void addCustomerStore(Map<String,String> mapParam);
	
}
