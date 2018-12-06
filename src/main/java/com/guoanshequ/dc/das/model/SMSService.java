package com.guoanshequ.dc.das.model;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.model
 * @Description: 短信服务账号
 * @Author: gbl
 * @CreateDate: 2018/11/23 15:23
 */
public class SMSService {
    //短信通道ID
    private String channelId;
    //短信服务地址
    private String url;
    //短信服务登录用户名
    private String userName;
    //短信服务登录账号
    private String passWord;
    //短信通道名称
    private String channelName;
    //使用状态 1：是 0：否
    private int status;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
