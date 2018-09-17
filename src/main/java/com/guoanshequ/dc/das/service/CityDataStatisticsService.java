package com.guoanshequ.dc.das.service;

import com.guoanshequ.dc.das.dao.master.CityDataStatisticsMapper;
import com.guoanshequ.dc.das.model.CityDataStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by sunning on 2018/9/7.
 */
@Service("CityDataStatisticsService")
@Transactional("master")
public class CityDataStatisticsService {
    @Autowired
    CityDataStatisticsMapper cityDataStatisticsMapper;
    public CityDataStatistics findCityDataStatisticsByCityId(Long city_id){
        return cityDataStatisticsMapper.findCityDataStatisticsByCityId(city_id);
    }
    public void insertCityDataStatistics(CityDataStatistics cityDataStatistics){
        CityDataStatistics cityDataStatisticsByCityId = cityDataStatisticsMapper.findCityDataStatisticsByCityId(cityDataStatistics.getCity_id());
        if(cityDataStatisticsByCityId==null){
            cityDataStatisticsMapper.insertCityDataStatistics(cityDataStatistics);
        }else{
            cityDataStatisticsMapper.updateCityDataStatistics(cityDataStatistics);
        }
    }
    public void updateCityDataStatistics(CityDataStatistics cityDataStatistics){
        cityDataStatisticsMapper.updateCityDataStatistics(cityDataStatistics);
    }

    public int findHouseByCityName(String city_name){
        return cityDataStatisticsMapper.findHouseByCityName(city_name);
    }
    public int findBuildingByCityName(String city_name){
        return cityDataStatisticsMapper.findBuildingByCityName(city_name);
    }
    public int findTinyVillageByCityName(String city_name){
        return cityDataStatisticsMapper.findTinyVillageByCityName(city_name);
    }
    public int findVillageByCityName(String city_name){
        return cityDataStatisticsMapper.findVillageByCityName(city_name);
    }
    public int findTownCountByCityName(String city_name){
        return cityDataStatisticsMapper.findTownCountByCityName(city_name);
    }

    /**
     * 获取每天小区拆分的数据
     * @param map
     * @return
     */
    public List<Map<String,Object>> findSplitTinyVillageAfterBuilding(Map<String,Object> map){
        return cityDataStatisticsMapper.findSplitTinyVillageAfterBuilding(map);
    }

    /**
     * 获取九月五号及之后的所有数据
     * @return
     */
    public List<Map<String,Object>> findSplitTinyVillageAfterBuildingDateForFifthOfSeptember(){
        return cityDataStatisticsMapper.findSplitTinyVillageAfterBuildingDateForFifthOfSeptember();
    }

    /**
     * 修改房屋数据
     * @return
     */
    public  void updateHousetinyvillage(Map<String,Object> map){
        cityDataStatisticsMapper.updateHousetinyvillage(map);
    };







}
