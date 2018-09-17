package com.guoanshequ.dc.das.dao.master;

import com.guoanshequ.dc.das.datasource.DataSource;
import com.guoanshequ.dc.das.model.CityDataStatistics;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by sunning on 2018/9/7.
 */
@Repository
@DataSource("master")
public interface CityDataStatisticsMapper {
    CityDataStatistics findCityDataStatisticsByCityId(Long city_id);
    void insertCityDataStatistics(CityDataStatistics cityDataStatistics);
    void updateCityDataStatistics(CityDataStatistics cityDataStatistics);
    int findHouseByCityName(String city_name);
    int findBuildingByCityName(String city_name);
    int findTinyVillageByCityName(String city_name);
    int findVillageByCityName(String city_name);
    int findTownCountByCityName(String city_name);

    /**
     * 获取每天小区拆分的数据
     * @param map
     * @return
     */
    List<Map<String,Object>> findSplitTinyVillageAfterBuilding(Map<String, Object> map);

    /**
     * 获取九月五号及之后的所有数据
     * @return
     */
    List<Map<String,Object>> findSplitTinyVillageAfterBuildingDateForFifthOfSeptember();
    /**
     * 修改房屋数据
     * @return
     */
    void updateHousetinyvillage(Map<String, Object> map);

}
