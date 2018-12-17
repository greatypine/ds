package com.guoanshequ.dc.das.model;

import java.util.Date;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.model
 * @Description: 短信电话
 * @Author: gbl
 * @CreateDate: 2018/12/7 13:53
 */
public class SMSPhone {

    private Long id;
    private String groupCode;
    private String groupName;
    private String mobilePhone;
    private Integer status;//是否使用 0:否 1:是
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
