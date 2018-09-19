package com.guoanshequ.dc.das.service;


import com.guoanshequ.dc.das.dao.master.HouseMapper;
import com.guoanshequ.dc.das.model.House;
import com.guoanshequ.dc.das.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by sunning on 2018/9/12.
 */
@Service("HouseService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class HouseService {
    @Autowired
    HouseMapper houseDao;

    public void deleteHouseDataByVillageID(Long village_id){
        /*Map<String, Object> map = new HashMap<>();
        map.put("village_id",village_id);*/
        houseDao.deleteHouseDataByVillageID(village_id);
    }

    public void insertHouse(House house,User user){
        house.setCreate_user(user.getName());
        house.setCreate_user_id(user.getId());
        house.setUpdate_user(user.getName());
        house.setUpdate_user_id(user.getId());
        house.setCreate_time(new Date());
        house.setUpdate_time(new Date());
        house.setStatus(0);
        house.setVersion(0);
        houseDao.insertHouse(house);
    }

}
