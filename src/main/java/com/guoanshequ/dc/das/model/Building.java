package com.guoanshequ.dc.das.model;

import com.guoanshequ.dc.das.model.base.DataEntity;

/**
 * Created by sunning on 2018/9/11.
 */
public class Building extends DataEntity {
    /**
     * 小区ID
     */
    private Long tinyvillage_id;

    /**
     * 社区ID
     */
    private Long village_id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;
    /**
     * 楼房分类 0平房   1楼房 2商业楼宇
     */
    private Integer type;

    private Long attachment_id;

    public Long getTinyvillage_id() {
        return tinyvillage_id;
    }

    public void setTinyvillage_id(Long tinyvillage_id) {
        this.tinyvillage_id = tinyvillage_id;
    }

    public Long getVillage_id() {
        return village_id;
    }

    public void setVillage_id(Long village_id) {
        this.village_id = village_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getAttachment_id() {
        return attachment_id;
    }

    public void setAttachment_id(Long attachment_id) {
        this.attachment_id = attachment_id;
    }
}
