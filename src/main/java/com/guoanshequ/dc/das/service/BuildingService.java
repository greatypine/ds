package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.BuildingMapper;
import com.guoanshequ.dc.das.model.Building;
import com.guoanshequ.dc.das.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by sunning on 2018/9/12.
 */
@Service("BuildingService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class BuildingService {
    @Autowired
    BuildingMapper buildingDao;

    public void deleteBuildingByVillageID(Long village_id){
        buildingDao.deleteBuildingByVillageID(village_id);
    }

    public Building insertBuilding(Building building,User user){
        building.setCreate_user(user.getName());
        building.setCreate_user_id(user.getId());
        building.setUpdate_user(user.getName());
        building.setUpdate_user_id(user.getId());
        building.setCreate_time(new Date());
        building.setUpdate_time(new Date());
        building.setStatus(0);
        building.setVersion(0);
        buildingDao.insertBuilding(building);
        return  building;
    }
    public Building findBuildingBynameAndTinyIdAndtype(Building building){
      return buildingDao.findBuildingBynameAndTinyIdAndtype(building);
    }
}
