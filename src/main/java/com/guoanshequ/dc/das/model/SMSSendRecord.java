package com.guoanshequ.dc.das.model;

import java.util.Date;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.model
 * @Description:短信发送请求记录
 * @Author: gbl
 * @CreateDate: 2018/11/26 16:18
 */

public class SMSSendRecord {
    private Long id;
    private String appKey;
    private String channelId;
    private String mobilePhone;
    private String exeResult;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getExeResult() {
        return exeResult;
    }

    public void setExeResult(String exeResult) {
        this.exeResult = exeResult;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
