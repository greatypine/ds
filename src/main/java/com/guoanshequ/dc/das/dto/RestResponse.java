package com.guoanshequ.dc.das.dto;

import com.guoanshequ.dc.das.domain.EnumRespStatus;

/**
 * Created by shuhuadai on 2017/2/21.
 */
public class RestResponse {
    private String  status;
    private String message;
    private Object datacount;
    private Object data;

    public RestResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public RestResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public RestResponse(EnumRespStatus respStatus) {
        this.status = respStatus.getCode();
        this.message = respStatus.getMessage();
    }

    public RestResponse(EnumRespStatus respStatus, Object datacount,Object data) {
        this.status = respStatus.getCode();
        this.message = respStatus.getMessage();
        this.datacount = datacount;
        this.data = data;
    }

    public RestResponse(EnumRespStatus respStatus, Object data) {
        this.status = respStatus.getCode();
        this.message = respStatus.getMessage();
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

	public Object getDatacount() {
		return datacount;
	}

	public void setDatacount(Object datacount) {
		this.datacount = datacount;
	}

}
