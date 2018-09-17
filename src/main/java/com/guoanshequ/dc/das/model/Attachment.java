package com.guoanshequ.dc.das.model;

import com.guoanshequ.dc.das.model.base.DataEntity;

/**
 * Created by sunning on 2018/9/11.
 */
public class Attachment extends DataEntity{
    /**
     * 附件名称
     */
    private String file_name;

    /**
     * 附件路径
     */
    private String file_path;

    /**
     * 文件类型
     */
    private Integer file_type;
    /**
     * 文件类型
     */
    private String file_type_name;
    /**
     * 街道名称
     */
    private String townName;
    /**
     * 门店名称
     */
    private String store_name;
    /**
     * 门店id
     */
    private Long store_id;
    /**
     * 错误信息
     */
    private String message;

    private String uploadType;

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public Integer getFile_type() {
        return file_type;
    }

    public void setFile_type(Integer file_type) {
        this.file_type = file_type;
    }

    public String getFile_type_name() {
        return file_type_name;
    }

    public void setFile_type_name(String file_type_name) {
        this.file_type_name = file_type_name;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }
}
