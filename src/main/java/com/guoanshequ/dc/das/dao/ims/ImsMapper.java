package com.guoanshequ.dc.das.dao.ims;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("ims")
public interface ImsMapper {

	List<ImsTbOCount> queryImsTbOCountByDate(Map<String, String> paraMap);

	List<ImsTbOCountg> queryImsTbOCountgByCId(String c_id);

	ImsTbStore queryImsTbStoreByStoreId(String c_store_id);

	List<ImsTbOl> queryImsTbOlByDate(Map<String, String> paraMap);
	
	String queryMaxAuDtTime();
	
}
