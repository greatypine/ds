package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.Village;
import org.springframework.stereotype.Repository;

/**
 * Created by sunning on 2018/9/11.
 */
@Repository
@DataSource("master")
public interface VillageMapper {

    Village findVillageByGB_CODE(String gb_code);

    Village findVillageByID(Long id);

    void updateVillage(Village village);

}
