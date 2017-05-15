package com.guoanshequ.dc.das.dao.slave;


import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;

@Repository
@DataSource("slave")
public interface StoreNumberMapper {

	String queryStoreNumbers();
}
