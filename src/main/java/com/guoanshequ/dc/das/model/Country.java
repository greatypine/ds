package com.guoanshequ.dc.das.model;

/**
 * Created by shuhuadai on 2017/2/14.
 */
public class Country {

    private Long id;

    private String gb_code;

    private String name;

    private Long city_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCity_id() {
        return city_id;
    }

    public void setCity_id(Long city_id) {
        this.city_id = city_id;
    }
}
