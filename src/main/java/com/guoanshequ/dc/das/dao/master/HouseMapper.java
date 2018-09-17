package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.House;
import org.springframework.stereotype.Repository;

/**
 * Created by sunning on 2018/9/11.
 */
@Repository
@DataSource("master")
public interface HouseMapper {
    void deleteHouseDataByVillageID(Long village_id);
    void insertHouse(House house);
}
