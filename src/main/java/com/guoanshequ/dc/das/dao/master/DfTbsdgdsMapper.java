package com.guoanshequ.dc.das.dao.master;


import org.springframework.stereotype.Repository;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.ImsTbsdgds;

@Repository
@DataSource("master")
public interface DfTbsdgdsMapper{
	
	int addDfTbsdgdsDaily(ImsTbsdgds imsTbsdgds); 
	
	int addDfTbsdgdsTotal(ImsTbsdgds imsTbsdgds); 
	
}
