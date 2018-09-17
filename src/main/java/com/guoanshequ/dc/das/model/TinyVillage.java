package com.guoanshequ.dc.das.model;

import com.guoanshequ.dc.das.model.base.DataEntity;

/**
 * Created by sunning on 2018/9/11.
 */
public class TinyVillage extends DataEntity{
    /**
     * 小区名
     */
    private String name;
    /**
     * 小区名
     */
    private String othername;

    /**
     * 小区地址
     */
    private String address;

    /**
     * 社区ID
     */
    private Long village_id;

    /**
     * 街道ID
     */
    private Long town_id;
    /**
     * 门店编号
     */
    private String store_code;
    /**
     * 居民户数
     */
    private Integer residents_number;
    /**
     * 小区类型 0平房 1楼房 2商业楼宇
     */
    private Integer tinyvillage_type;

    private Long store_id;

    private Long attachment_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOthername() {
        return othername;
    }

    public void setOthername(String othername) {
        this.othername = othername;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getVillage_id() {
        return village_id;
    }

    public void setVillage_id(Long village_id) {
        this.village_id = village_id;
    }

    public Long getTown_id() {
        return town_id;
    }

    public void setTown_id(Long town_id) {
        this.town_id = town_id;
    }

    public Integer getResidents_number() {
        return residents_number;
    }

    public void setResidents_number(Integer residents_number) {
        this.residents_number = residents_number;
    }

    public String getStore_code() {
        return store_code;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    public Integer getTinyvillage_type() {
        return tinyvillage_type;
    }

    public void setTinyvillage_type(Integer tinyvillage_type) {
        this.tinyvillage_type = tinyvillage_type;
    }

    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public Long getAttachment_id() {
        return attachment_id;
    }

    public void setAttachment_id(Long attachment_id) {
        this.attachment_id = attachment_id;
    }
}
