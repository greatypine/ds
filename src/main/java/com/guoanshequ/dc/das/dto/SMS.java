package com.guoanshequ.dc.das.dto;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.dto
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/12/6 11:13
 */
public class SMS {

    private String appKey;
    private int channelId;
    private String groupCode;
    private int smsType;
    private String mobilePhone;
    private String content;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public int getSmsType() {
        return smsType;
    }

    public void setSmsType(int smsType) {
        this.smsType = smsType;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
