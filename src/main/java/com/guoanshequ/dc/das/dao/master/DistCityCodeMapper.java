package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.DistCityCode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunning on 2018/9/6.
 */
@Repository
@DataSource("master")
public interface DistCityCodeMapper {
    List<DistCityCode> getAllDistCityCode();

}
