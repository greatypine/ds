package com.guoanshequ.dc.das.model;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.model
 * @Description: 公积金登录信息
 * @Author: gbl
 * @CreateDate: 2018/12/25 11:06
 */
public class ProvidentFundLoginInfo {
    private Long id;
    private Integer provinceId;//省编号
    private String provinceName;//省名称
    private Integer cityId;//城市编号
    private String cityName;//城市名称
    private String loginName;//登录名
    private String loginNameDesc;//登录名描述
    private String loginPassWord;//登录密码
    private String loginPassWordDesc;//登录密码描述

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginNameDesc() {
        return loginNameDesc;
    }

    public void setLoginNameDesc(String loginNameDesc) {
        this.loginNameDesc = loginNameDesc;
    }

    public String getLoginPassWord() {
        return loginPassWord;
    }

    public void setLoginPassWord(String loginPassWord) {
        this.loginPassWord = loginPassWord;
    }

    public String getLoginPassWordDesc() {
        return loginPassWordDesc;
    }

    public void setLoginPassWordDesc(String loginPassWordDesc) {
        this.loginPassWordDesc = loginPassWordDesc;
    }
}
