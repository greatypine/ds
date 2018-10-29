package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@DataSource("master")
public interface DfImsMapper {

	ImsTbOCount queryImsTbOCountIsExist(String c_id);

	ImsTbOl queryImsTbOlIsExist(String c_id);

	ImsTbStore queryImsTbStoreIsExist(String c_id);

	Integer addDfImsTbOCount(ImsTbOCount imsTbOCount);

	Integer addDfImsTbOCountg(List<ImsTbOCountg> list);

	Integer addDfImsTbStore(ImsTbStore imsTbStore);

	Integer addDfImsTbOl(ImsTbOl imsTbOl);

	Integer deleteDfImsTbOCount(String c_id);

	Integer deleteDfImsTbOCountg(String c_id);

	Integer deleteDfImsTbOl(String c_id);

	Integer deleteDfImsTbStore(String c_store_id);
}
