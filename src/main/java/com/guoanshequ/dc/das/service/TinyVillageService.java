package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.TinyVillageMapper;
import com.guoanshequ.dc.das.dao.master.VillageMapper;
import com.guoanshequ.dc.das.model.TinyVillage;
import com.guoanshequ.dc.das.model.User;
import com.guoanshequ.dc.das.model.Village;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 查询小区相关信息
 */
@Service("TinyVillageService")
@Transactional(value = "master",rollbackFor = Exception.class)
public class TinyVillageService {

    @Autowired
    TinyVillageMapper tinyVillageDao;
    @Autowired
    VillageMapper villageDao;

    public Map<String, Object> queryTinyidByCode(String code) throws Exception{

        return tinyVillageDao.queryTinyidByCode(code);
    }

    /**
     * 根据文件名判断是否有小区
     * @param file_name
     * @return
     */
    public String isExistTinyVillageBYFileName(String file_name){
        file_name=file_name.split("/")[2];
        String gb_code=file_name.split("-")[0];
        Village village = villageDao.findVillageByGB_CODE(gb_code);
        if(village!=null){
            //根据社区id查询小区信息
            List<TinyVillage> tinyVillageList = tinyVillageDao.findTinyVillageByVillageId(village.getId());
            if(tinyVillageList!=null&&tinyVillageList.size()>0){
                return village.getGb_code()+"-"+village.getName();
            }
        }
        return null;
    }

    public TinyVillage findTinyVillageByVillageIdAndName(String tiny_name,Long village_id){
        TinyVillage tinyVillage = new TinyVillage();
        tinyVillage.setName(tiny_name);
        tinyVillage.setVillage_id(village_id);
        return tinyVillageDao.findTinyVillageByVillageIdAndName(tinyVillage);
    }

    public TinyVillage insertOrUpdateTinyVillage(TinyVillage tinyvillage, User user){
        tinyvillage.setStatus(0);
        tinyvillage.setUpdate_user(user.getName());
        tinyvillage.setUpdate_time(new Date());
        tinyvillage.setUpdate_user_id(user.getId());
        if(tinyvillage.getId()==null){
            tinyvillage.setVersion(0);
            tinyvillage.setCreate_user(user.getName());
            tinyvillage.setCreate_time(new Date());
            tinyvillage.setCreate_user_id(user.getId());
            tinyVillageDao.insertTinyVillage(tinyvillage);
            return tinyvillage;
        }
        tinyVillageDao.updateTinyVillage(tinyvillage);
        return  tinyvillage;
    }

    public void insertTinyCode(TinyVillage tinyVillage,User user){
        Map<String,Object> tinycodeMap=new HashMap<>();
        //根据小区id查询小区code
        Map<String, Object> map = tinyVillageDao.queryTinyCodeinfoBytinyId(tinyVillage.getId());
        if(map==null){
            //根据街道id获取街道信息
            Map<String, Object> townmap = tinyVillageDao.queryTownById(tinyVillage.getTown_id());
            String town_gb_code = townmap.get("gb_code")+"";
            Integer maxnum = tinyVillageDao.findMaxTinyVillageCode(town_gb_code);
            if (maxnum != null) {
                String strss = maxnum + 1 + "";
                if (strss.length() == 1) {
                    tinycodeMap.put("code",town_gb_code+"000000000"+ strss);
                } else if (strss.length() == 2) {
                    tinycodeMap.put("code",town_gb_code+"00000000"+ strss);
                } else if (strss.length() == 3) {
                    tinycodeMap.put("code",town_gb_code+"0000000"+ strss);
                } else if (strss.length() == 4) {
                    tinycodeMap.put("code",town_gb_code+"000000"+ strss);
                } else if (strss.length() == 5) {
                    tinycodeMap.put("code",town_gb_code+"00000"+ strss);
                } else if (strss.length() == 6) {
                    tinycodeMap.put("code",town_gb_code+"0000"+ strss);
                } else if (strss.length() == 7) {
                    tinycodeMap.put("code",town_gb_code+"000"+ strss);
                } else if (strss.length() == 8) {
                    tinycodeMap.put("code",town_gb_code+"00"+ strss);
                } else if (strss.length() == 9) {
                    tinycodeMap.put("code",town_gb_code+"0"+ strss);
                }
            } else {
                tinycodeMap.put("code",town_gb_code+"0000000001");
            }
            tinycodeMap.put("tiny_village_id",tinyVillage.getId());
            tinycodeMap.put("tiny_village_name",tinyVillage.getName());
            tinycodeMap.put("create_user_id",user.getId());
            tinycodeMap.put("create_time",new Date());
            tinycodeMap.put("update_user_id",user.getId());
            tinycodeMap.put("update_time",new Date());
            tinycodeMap.put("version",0);
            tinyVillageDao.insertTinyCode(tinycodeMap);
        }
    }




}
