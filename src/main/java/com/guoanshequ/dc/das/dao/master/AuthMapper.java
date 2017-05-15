package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.Auth;
import org.springframework.stereotype.Repository;

@Repository
@DataSource("master")
public interface AuthMapper {

    Auth findByAppKey(String appKey);

}
