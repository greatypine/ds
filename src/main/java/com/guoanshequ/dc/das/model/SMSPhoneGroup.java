package com.guoanshequ.dc.das.model;

import java.util.Date;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.model
 * @Description: 电话群组
 * @Author: gbl
 * @CreateDate: 2018/12/13 13:35
 */
public class SMSPhoneGroup {

    private Long id;
    private String code;
    private String name;
    private Integer status;//是否使用 0:否 1:是
    private String remark;
    private Date createtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
