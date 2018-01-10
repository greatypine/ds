package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

@Repository
@DataSource("master")
public interface AreaInfoMapper {


	String queryAreaNoByTinyNo(String code);

}
