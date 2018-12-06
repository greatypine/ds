package com.guoanshequ.dc.das.model;

import com.guoanshequ.dc.das.datasource.DataSource;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.model
 * @Description: auth中app_key对应的允许的服务
 * @Author: gbl
 * @CreateDate: 2018/11/14 11:30
 */

public class PermissionService {
    private Long id;//主键
    private String app_key;//权限许可证编号
    private String reqURI;//服务地址
    private Integer status;//启用状态 0：否 1：是

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getReqURI() {
        return reqURI;
    }

    public void setReqURI(String reqURI) {
        this.reqURI = reqURI;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
