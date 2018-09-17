package com.guoanshequ.dc.das.model;

/**
 * Created by sunning on 2018/9/7.
 */
public class CityDataStatistics {
    private Long id;
    /**
     * 城市id
     */
    private long city_id;
    /**
     * 城市name
     */
    private String city_name;
    /**
     * 城市管理小区数量
     */
    private Integer city_tiny_village_count;
    /**
     * 城市管理社区数量
     */
    private Integer city_village_count;
    /**
     * 城市管理街道数量
     */
    private Integer city_town_count;
    /**
     * 城市管理楼房数量
     */
    private Integer city_building_count;
    /**
     * 城市管理房屋数量
     */
    private Integer city_house_count;

    public long getCity_id() {
        return city_id;
    }

    public void setCity_id(long city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Integer getCity_tiny_village_count() {
        return city_tiny_village_count;
    }

    public void setCity_tiny_village_count(Integer city_tiny_village_count) {
        this.city_tiny_village_count = city_tiny_village_count;
    }

    public Integer getCity_village_count() {
        return city_village_count;
    }

    public void setCity_village_count(Integer city_village_count) {
        this.city_village_count = city_village_count;
    }

    public Integer getCity_town_count() {
        return city_town_count;
    }

    public void setCity_town_count(Integer city_town_count) {
        this.city_town_count = city_town_count;
    }

    public Integer getCity_building_count() {
        return city_building_count;
    }

    public void setCity_building_count(Integer city_building_count) {
        this.city_building_count = city_building_count;
    }

    public Integer getCity_house_count() {
        return city_house_count;
    }

    public void setCity_house_count(Integer city_house_count) {
        this.city_house_count = city_house_count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
