package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.CityDataStatistics;
import com.guoanshequ.dc.das.model.DistCityCode;
import com.guoanshequ.dc.das.service.CityDataStatisticsService;
import com.guoanshequ.dc.das.service.DistCityCodeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计城市下有多少楼房，街道，社区，小区，房屋
 * Created by sunning on 2018/9/7.
 */
@Component
public class CityDataStatisticsTask {
    @Autowired
    CityDataStatisticsService cityDataStatisticsService;
    @Autowired
    DistCityCodeService distCityCodeService;
    private static final Logger logger = LogManager.getLogger(CityDataStatisticsTask.class);
    /**
     * 统计每个城市下有多少楼房，街道，社区，小区，房屋
     */
    @Scheduled(cron ="0 10 6 * * ?")
    public void syncCityDataStatistics(){
        new Thread(){
            public void run() {
                try{
                    logger.info("**********统计每个城市基础数据任务调度开始**********");
                    // 获取所有有门店的城市
                    List<DistCityCode> allDistCityCode = distCityCodeService.getAllDistCityCode();
                    for (DistCityCode distCityCode:allDistCityCode) {
                        CityDataStatistics cityDataStatistics = cityDataStatisticsService.findCityDataStatisticsByCityId(distCityCode.getId());
                        if(cityDataStatistics==null){
                            cityDataStatistics=new CityDataStatistics();
                        }
                        cityDataStatistics.setCity_id(distCityCode.getId());
                        cityDataStatistics.setCity_name(distCityCode.getCityname());
                        cityDataStatistics.setCity_town_count(cityDataStatisticsService.findTownCountByCityName(distCityCode.getCityname()));
                        cityDataStatistics.setCity_village_count(cityDataStatisticsService.findVillageByCityName(distCityCode.getCityname()));
                        cityDataStatistics.setCity_tiny_village_count(cityDataStatisticsService.findTinyVillageByCityName(distCityCode.getCityname()));
                        cityDataStatistics.setCity_building_count(cityDataStatisticsService.findBuildingByCityName(distCityCode.getCityname()));
                        cityDataStatistics.setCity_house_count(cityDataStatisticsService.findHouseByCityName(distCityCode.getCityname()));
                        cityDataStatisticsService.insertCityDataStatistics(cityDataStatistics);
                    }
                    logger.info("**********统计每个城市基础数据任务调度结束**********");
                }catch (Exception e){
                    logger.info(e.toString());
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 定时维护小区拆分后的房屋数据
     */
    @Scheduled(cron ="0 0 23 * * ?")
    public void syncHoseBuilding(){
        new Thread(){
            public void run() {
                try{
                    logger.info("**********维护小区拆分后的房屋数据任务调度开始**********");
                    SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
                    SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd" );
                    Date date = new Date();
                    String format = sdf.format(date);
                    String format2 = sdf1.format(date);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    Date date1= calendar.getTime();
                    String format1 = sdf.format(date1);
                    //执行九月五号至今的数据（九月五号将定时任务从daqWeb迁移到ds上）
                    if("2018-09-09".equals(format2)){
                        //获取从九月五号至今的数据
                        List<Map<String, Object>> forFifthOfSeptember = cityDataStatisticsService.findSplitTinyVillageAfterBuildingDateForFifthOfSeptember();
                        if(forFifthOfSeptember != null && forFifthOfSeptember.size() > 0){
                            Map<String, Object> map=null;
                            for (Map<String, Object> mapbuildHouse:forFifthOfSeptember) {
                                map=new HashMap<String, Object>();
                                map.put("update_user",mapbuildHouse.get("create_user"));
                                map.put("update_user_id",mapbuildHouse.get("create__user_id"));
                                map.put("tinyvillage_id",mapbuildHouse.get("tinyvillageId"));
                                map.put("builds",String.valueOf(mapbuildHouse.get("buildingid")).split(","));
                                cityDataStatisticsService.updateHousetinyvillage(map);
                            }
                        }
                    }else{
                        Map<String, Object> dateMap=new HashMap<>();
                        dateMap.put("startDate",format1);
                        dateMap.put("endDate",format);
                        List<Map<String, Object>> forFifthOfSeptember = cityDataStatisticsService.findSplitTinyVillageAfterBuilding(dateMap);
                        if(forFifthOfSeptember != null && forFifthOfSeptember.size() > 0){
                            Map<String, Object> map=null;
                            for (Map<String, Object> mapbuildHouse:forFifthOfSeptember) {
                                map=new HashMap<String, Object>();
                                map.put("update_user",mapbuildHouse.get("create_user"));
                                map.put("update_user_id",mapbuildHouse.get("create__user_id"));
                                map.put("tinyvillage_id",mapbuildHouse.get("tinyvillageId"));
                                map.put("builds",String.valueOf(mapbuildHouse.get("buildingid")).split(","));
                                cityDataStatisticsService.updateHousetinyvillage(map);
                            }
                        }
                    }
                    logger.info("**********维护小区拆分后的房屋数据任务调度结束**********");
                }catch (Exception e){
                    logger.info(e.toString());
                    e.printStackTrace();
                }
            }
        }.start();
    }






}
