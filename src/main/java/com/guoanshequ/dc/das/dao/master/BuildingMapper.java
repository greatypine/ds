package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.Building;
import org.springframework.stereotype.Repository;

/**
 * Created by sunning on 2018/9/11.
 */
@Repository
@DataSource("master")
public interface BuildingMapper {
    void deleteBuildingByVillageID(Long village_id);
    void insertBuilding(Building building);
    Building findBuildingBynameAndTinyIdAndtype(Building building);
}
