package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.VillageMapper;
import com.guoanshequ.dc.das.model.User;
import com.guoanshequ.dc.das.model.Village;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by sunning on 2018/9/11.
 */
@Service("VillageService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class VillageService {
    @Autowired
    private VillageMapper villageDao;

    public Village findVillageByFileName(String gb_code){
        return villageDao.findVillageByGB_CODE(gb_code);

    }

    public Village updateVillage(Village village,User user){
        village.setUpdate_time(new Date());
        village.setUpdate_user(user.getName());
        village.setUpdate_user_id(user.getId());
        villageDao.updateVillage(village);
        return village;
    }



}
