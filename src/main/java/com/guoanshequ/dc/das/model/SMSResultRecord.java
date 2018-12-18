package com.guoanshequ.dc.das.model;

import java.util.Date;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.model
 * @Description:短信发送结果
 * @Author: gbl
 * @CreateDate: 2018/12/6 11:03
 */
public class SMSResultRecord {

    private Long id;
    private String appKey;
    private String channelId;
    private String mobilePhone;
    private String serialNo;
    private Date sendTime;
    private String sendResultFlag;
    private String sendResultDesc;
    private String groupCode;
    private Integer smsType;
    private String beginDate;
    private String endDate;
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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendResultFlag() {
        return sendResultFlag;
    }

    public void setSendResultFlag(String sendResultFlag) {
        this.sendResultFlag = sendResultFlag;
    }

    public String getSendResultDesc() {
        return sendResultDesc;
    }

    public void setSendResultDesc(String sendResultDesc) {
        this.sendResultDesc = sendResultDesc;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
