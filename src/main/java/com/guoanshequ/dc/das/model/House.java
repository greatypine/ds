package com.guoanshequ.dc.das.model;

import com.guoanshequ.dc.das.model.base.DataEntity;

/**
 * Created by sunning on 2018/9/11.
 */
public class House extends DataEntity{
    /**
     * 住房分类
     */
    private Integer house_type;

    /**
     * 小区ID
     */
    private Long tinyvillage_id;

    /**
     * 楼房ID
     */
    private Long building_id;

    /**
     * 楼房楼号
     */
    private String building_number;

    /**
     * 楼房单元号
     */
    private String building_unit_number;

    /**
     * 楼房房间号
     */
    private String building_room_number = "0";

    /**
     * 平房门牌号
     */
    private String bungalow_number;

    /**
     * 商业楼楼层号
     */
    private String commercial_floor_number;

    /**
     * 商业楼房间号
     */
    private Integer commercial_room_number;

    /**
     * 完整地址
     */
    private String address;

    private Long attachment_id;

    private String building_unit_no;
    private String building_house_no;
    private String house_no;
    private Integer used_price;
    private Integer rent;
    private Integer approve_status;

    public Integer getHouse_type() {
        return house_type;
    }

    public void setHouse_type(Integer house_type) {
        this.house_type = house_type;
    }

    public Long getTinyvillage_id() {
        return tinyvillage_id;
    }

    public void setTinyvillage_id(Long tinyvillage_id) {
        this.tinyvillage_id = tinyvillage_id;
    }

    public Long getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(Long building_id) {
        this.building_id = building_id;
    }

    public String getBuilding_number() {
        return building_number;
    }

    public void setBuilding_number(String building_number) {
        this.building_number = building_number;
    }

    public String getBuilding_unit_number() {
        return building_unit_number;
    }

    public void setBuilding_unit_number(String building_unit_number) {
        this.building_unit_number = building_unit_number;
    }

    public String getBuilding_room_number() {
        return building_room_number;
    }

    public void setBuilding_room_number(String building_room_number) {
        this.building_room_number = building_room_number;
    }

    public String getBungalow_number() {
        return bungalow_number;
    }

    public void setBungalow_number(String bungalow_number) {
        this.bungalow_number = bungalow_number;
    }

    public String getCommercial_floor_number() {
        return commercial_floor_number;
    }

    public void setCommercial_floor_number(String commercial_floor_number) {
        this.commercial_floor_number = commercial_floor_number;
    }

    public Integer getCommercial_room_number() {
        return commercial_room_number;
    }

    public void setCommercial_room_number(Integer commercial_room_number) {
        this.commercial_room_number = commercial_room_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAttachment_id() {
        return attachment_id;
    }

    public void setAttachment_id(Long attachment_id) {
        this.attachment_id = attachment_id;
    }

    public String getBuilding_unit_no() {
        return building_unit_no;
    }

    public void setBuilding_unit_no(String building_unit_no) {
        this.building_unit_no = building_unit_no;
    }

    public String getBuilding_house_no() {
        return building_house_no;
    }

    public void setBuilding_house_no(String building_house_no) {
        this.building_house_no = building_house_no;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public Integer getUsed_price() {
        return used_price;
    }

    public void setUsed_price(Integer used_price) {
        this.used_price = used_price;
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public Integer getApprove_status() {
        return approve_status;
    }

    public void setApprove_status(Integer approve_status) {
        this.approve_status = approve_status;
    }
}
