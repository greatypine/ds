package com.guoanshequ.dc.das.model;

import java.util.Date;

/**
 * Created by sunning on 2018/9/6.
 */
public class Store {
    private Long store_id;
    private String name;
    private String estate;
    private String city_name;
    private Integer ordnumber;
    private Date open_shop_time;

    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEstate() {
        return estate;
    }

    public void setEstate(String estate) {
        this.estate = estate;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Integer getOrdnumber() {
        return ordnumber;
    }

    public void setOrdnumber(Integer ordnumber) {
        this.ordnumber = ordnumber;
    }

    public Date getOpen_shop_time() {
        return open_shop_time;
    }

    public void setOpen_shop_time(Date open_shop_time) {
        this.open_shop_time = open_shop_time;
    }
}
