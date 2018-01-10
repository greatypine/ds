package com.guoanshequ.dc.das.dao.slave;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("slave")
public interface ProductCityMapper {
	
	 List<Map<String, String>> queryByProductCity(Map<String, String> paraMap);
	
}
