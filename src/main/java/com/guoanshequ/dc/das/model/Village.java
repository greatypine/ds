package com.guoanshequ.dc.das.model;

import com.guoanshequ.dc.das.model.base.DataEntity;

/**
 * Created by sunning on 2018/9/11.
 */
public class Village extends DataEntity {
    /**
     * 社区国标码
     */
    private String gb_code;

    /**
     * 社区名
     */
    private String name;

    /**
     * 辖区面积
     */
    private String square_area;

    /**
     * 户数
     */
    private Integer household_number;

    /**
     * 常住人口数
     */
    private Double resident_population_number;
    /**
     * 社区介绍
     */
    private String introduction;

    /**
     * 居委会地址
     */
    private String committee_address;

    /**
     * 居委会电话
     */
    private String committee_phone;

    /**
     * 街道ID
     */
    private Long town_id;

    private String town;

    private String store_code;

    private String store_name;

    private Long attachment_id;

    public Long getTown_id() {
        return town_id;
    }

    public void setTown_id(Long town_id) {
        this.town_id = town_id;
    }

    public String getGb_code() {
        return gb_code;
    }

    public void setGb_code(String gb_code) {
        this.gb_code = gb_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSquare_area() {
        return square_area;
    }

    public void setSquare_area(String square_area) {
        this.square_area = square_area;
    }

    public Integer getHousehold_number() {
        return household_number;
    }

    public void setHousehold_number(Integer household_number) {
        this.household_number = household_number;
    }

    public Double getResident_population_number() {
        return resident_population_number;
    }

    public void setResident_population_number(Double resident_population_number) {
        this.resident_population_number = resident_population_number;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCommittee_address() {
        return committee_address;
    }

    public void setCommittee_address(String committee_address) {
        this.committee_address = committee_address;
    }

    public String getCommittee_phone() {
        return committee_phone;
    }

    public void setCommittee_phone(String committee_phone) {
        this.committee_phone = committee_phone;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStore_code() {
        return store_code;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public Long getAttachment_id() {
        return attachment_id;
    }

    public void setAttachment_id(Long attachment_id) {
        this.attachment_id = attachment_id;
    }
}
